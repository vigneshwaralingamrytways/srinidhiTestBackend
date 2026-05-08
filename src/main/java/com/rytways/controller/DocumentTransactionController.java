package com.rytways.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.rytways.model.DocumentAccessHistory;
import com.rytways.model.DocumentTransaction;
import com.rytways.model.ReportDocument;
import com.rytways.model.Users;
import com.rytways.repository.DocumentAccessHistoryRepo;
import com.rytways.repository.DocumentTransactionRepo;
import com.rytways.repository.DownloadHistoryRepo;
import com.rytways.repository.ReportDocumentRepo;
import com.rytways.repository.UserRepository;
import com.rytways.security.SecurityUtils;
import com.rytways.service.DocumentAccessHistoryService;
import com.rytways.service.DocumentTransactionService;
import com.rytways.service.WriteExcelReports;

@RestController
@RequestMapping("/documentTransaction")
public class DocumentTransactionController {

	@Autowired
	private DownloadHistoryRepo downloadHistoryRepo;

	@Autowired
	private DocumentTransactionRepo documentTransactionRepo;

	@Autowired
	private DocumentAccessHistoryRepo documentAccessHistoryRepo;

	@Autowired
	private ReportDocumentRepo reportDocumentRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private DocumentAccessHistoryService documentAccessHistoryService;

	@Autowired
	private DocumentTransactionService documentTransactionService;

	@Autowired
	private WriteExcelReports excelReports;

	@Value("${docReportUploadPath}")
	private String uploadDirectory;

//	 @Value("${docReportUploadPathd}")
//	  private String uploadDirectoryd;
//	

	@PostMapping("/updateReportDoc")
	public ResponseEntity<ReportDocument> updateReportDoc(@RequestBody ReportDocument doc) {

		doc = reportDocumentRepo.save(doc);

		return new ResponseEntity<>(doc, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<DocumentTransaction> createTransac(@RequestBody DocumentTransaction doc) {

		doc = documentTransactionService.createTransac(doc);

		return new ResponseEntity<>(doc, HttpStatus.OK);
	}

	@PostMapping("/edit")
	public ResponseEntity<DocumentTransaction> editTransac(@RequestBody DocumentTransaction doc) {

		doc = documentTransactionService.editTransac(doc);

		return new ResponseEntity<>(doc, HttpStatus.OK);
	}

	@PostMapping("/documentTransaction")
	public ResponseEntity<List<DocumentTransaction>> listPallet(@RequestBody DocumentTransaction document) {
		List<DocumentTransaction> doc = documentTransactionRepo.findAll(Sort.by(Sort.Direction.DESC, "transactionId"));
		;
		return new ResponseEntity<>(doc, HttpStatus.OK);
	}

	@PostMapping("/searchTransaction")
	public List<DocumentTransaction> getTransaction(@RequestBody DocumentTransaction transac) {

		String isSaved = "";

		List<DocumentTransaction> options = documentTransactionService.getTransaction(transac);

		return options;

	}

	@PostMapping("/viewFile")
	public ResponseEntity<Resource> viewFile(@RequestBody ReportDocument request) {
		try {
			Long reportDocId = request.getReportDocId();

			Optional<ReportDocument> reportDocOptional = reportDocumentRepo.findById(reportDocId);

			if (!reportDocOptional.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			ReportDocument reportDoc = reportDocOptional.get();

//			Path filePath = Paths.get(uploadDirectory, reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());

			Path filePath;
			if (reportDoc.getDeleteFilePath().startsWith(uploadDirectory)) {
				filePath = Paths.get(reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
			} else {
				filePath = Paths.get(uploadDirectory, reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
			}
			System.out.println(" Final File Path: " + filePath.toAbsolutePath());
			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists() || !resource.isReadable()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			// Detect content type (VERY IMPORTANT)
			String contentType = Files.probeContentType(filePath);
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					// ?? THIS LINE IS THE KEY DIFFERENCE
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + reportDoc.getFileName() + "\"")
					.body(resource);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/uploadFile")
	public Map uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("transactionId") Long transactionId,
			@RequestParam("remarks") String remarks, @RequestParam("documentType") String documentType,
			@RequestParam("folderCategoryName") String folderCategoryName,
			@RequestParam("subFolderCategoryName") String subFolderCategoryName) {
		ReportDocument report = new ReportDocument();
		try {

			// Save the file to the database
			report.setTransactionId(transactionId);
			report.setRemarks(remarks);
			report.setFileName(file.getOriginalFilename());
			// Set other file attributes if needed
			System.out.println(report.getFileName());
			// String extention = report.getFileName().split("\\.")[1];
			// Get the original filename and extension
			String originalFilename = file.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

			report.setType(extension);

			// Generate a unique filename using a timestamp
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String generatedFileName = timeStamp + "." + extension;
			report.setGeneratedFileName(generatedFileName);

			String directoryPath = Paths.get(uploadDirectory, documentType, folderCategoryName, subFolderCategoryName)
					.toString();

			String downloadPath = Paths.get(documentType, folderCategoryName, subFolderCategoryName).toString();

			report.setFilePath(downloadPath);
			report.setDeleteFilePath(directoryPath);

			report = reportDocumentRepo.save(report);

			report = reportDocumentRepo.findById(report.getReportDocId()).get();

			// Save the file to a directory if required
			// file.transferTo(new File(uploadDirectory+ report.getGeneratedFileName()));

			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			Path filePath = Paths.get(directoryPath, generatedFileName);

			// Save the file to the specified path
			File destinationFile = filePath.toFile();
			file.transferTo(destinationFile);

//	   	         Optional<DocumentTransaction> optionalDocumentTrans = documentTransactionRepo.findById(transactionId);
//		         if (optionalDocumentTrans.isPresent()) {
//		             DocumentTransaction documentTrans = optionalDocumentTrans.get();
//		             documentTrans.setDocAvailable(1);
//		             documentTransactionRepo.save(documentTrans);
//		         }

			Map map = new HashMap();
			map.put("reports", report);
			map.put("message", "File Uploaded Successfully");
			map.put("status", 1);
			Map retValues = new HashMap();
			;
			retValues.put("retValues", map);
			return retValues;

		} catch (Exception e) {
			e.printStackTrace();
			Map map = new HashMap();
			map.put("reports", report);
			map.put("message", "File Uploaded UnSuccessfull");
			map.put("status", 0);
			Map retValues = new HashMap();
			;
			retValues.put("retValues", map);
			return retValues;
		}
	}

	@PostMapping("/uploadFiles")
	public Map uploadFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam("transactionId") Long transactionId, @RequestParam("remarks") String remarks,
			@RequestParam("documentType") String documentType,
			@RequestParam("folderCategoryName") String folderCategoryName,
			@RequestParam("subFolderCategoryName") String subFolderCategoryName) {

		List<ReportDocument> savedReports = new ArrayList<>();
		Map retValues = new HashMap();
		try {
			for (MultipartFile file : files) {
				ReportDocument report = new ReportDocument();
				report.setTransactionId(transactionId);
				report.setRemarks(remarks);
				report.setFileName(file.getOriginalFilename());

				String originalFilename = file.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

				report.setType(extension);

				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				String generatedFileName = timeStamp + "." + extension;
				report.setGeneratedFileName(generatedFileName);

				String docFolder = "RepositoryDocument";

				String directoryPath = Paths
						.get(uploadDirectory, docFolder, documentType, folderCategoryName, subFolderCategoryName)
						.toString();
				String downloadPath = Paths.get(documentType, folderCategoryName, subFolderCategoryName).toString();
				String deltefilePath = Paths.get(docFolder, documentType, folderCategoryName, subFolderCategoryName)
						.toString();
				report.setFilePath(downloadPath);
				report.setDeleteFilePath(deltefilePath);

				report = reportDocumentRepo.save(report);
				savedReports.add(report);

				File directory = new File(directoryPath);
				if (!directory.exists()) {
					directory.mkdirs();
				}

				Path filePath = Paths.get(directoryPath, generatedFileName);
				File destinationFile = filePath.toFile();
				file.transferTo(destinationFile);
			}

			Optional<DocumentTransaction> optionalDocumentTrans = documentTransactionRepo.findById(transactionId);
			if (optionalDocumentTrans.isPresent()) {
				DocumentTransaction documentTrans = optionalDocumentTrans.get();
				documentTrans.setDocAvailable(1);
				documentTransactionRepo.save(documentTrans);
			}

			Map map = new HashMap();
			map.put("reports", savedReports);
			map.put("message", "Files Uploaded Successfully");
			map.put("status", 1);
			retValues.put("retValues", map);
			return retValues;

		} catch (Exception e) {
			e.printStackTrace();
			Map map = new HashMap();
			map.put("reports", savedReports);
			map.put("message", "File Upload Unsuccessful");
			map.put("status", 0);
			retValues.put("retValues", map);
			return retValues;
		}
	}

	@PostMapping("/getListByTransacId")
	public List<ReportDocument> uploadMultipleFiles(@RequestBody ReportDocument idDto) {

		Optional<List<ReportDocument>> list = reportDocumentRepo
				.findByTransactionIdOrderByUpdatedOn(idDto.getTransactionId());

		return list.get();
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteReportDocument(@RequestBody ReportDocument request) {
		try {
			// Long reportDocId = request.getReportDocId();
			documentTransactionService.deleteReportDocument(request);
			return new ResponseEntity<>("ReportDocument deleted successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

//	 @GetMapping("/downloadFile")
//	 public ResponseEntity<Resource> downloadFile(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) {
//	     try {
//	         Path file = Paths.get(uploadDirectory).resolve(filePath).resolve(fileName);
//	         Resource resource = new UrlResource(file.toUri());
//
//	         if (resource.exists() && resource.isReadable()) {
//	             return ResponseEntity.ok()
//	                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//	                     .body(resource);
//	         } else {
//	             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	         }
//	     } catch (MalformedURLException e) {
//	         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	     }
//	 }

	@GetMapping("/serverinfoab")
	public Map<String, String> getServerInfoa(HttpServletRequest request) {
		// Get the full base URL dynamically (including context path if needed)
		String baseUrl = request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() != 80 && request.getServerPort() != 443 ? ":" + request.getServerPort() : "")
				+ request.getContextPath();

		// Return it in a JSON response
		Map<String, String> response = new HashMap<>();
		response.put("baseUrl", baseUrl);
		return response;
	}

	@GetMapping("/serverinfo")
	public Map<String, String> getServerInfo(HttpServletRequest request) {
		String baseUrl = request.getScheme() + "://" + request.getServerName();

//	      // Check if running on localhost (or development environment)
//	      if (!request.getServerName().equalsIgnoreCase("localhost")) {
//	          // Only append context path if not running on localhost
//	          baseUrl += request.getContextPath();
//	      }

		// Return it in a JSON response
		Map<String, String> response = new HashMap<>();
		response.put("baseUrl", baseUrl);
		return response;
	}

//	  @PostMapping("/downloadFile")
//	  public ResponseEntity<Resource> downloadFile(@RequestBody ReportDocument reportDocument) {
//	      try {
//	          Long reportDocId = reportDocument.getReportDocId();
//
//	          // Fetch the report document from the database using reportDocId
//	          Optional<ReportDocument> reportDocOptional = reportDocumentRepo.findById(reportDocId);
//
//	          if (!reportDocOptional.isPresent()) {
//	              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	          }
//
//	          ReportDocument reportDoc = reportDocOptional.get();
//
//	          // Construct file path from document entity
//	          Path filePath = Paths.get(uploadDirectory, reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
//	          Resource resource = new UrlResource(filePath.toUri());
//	          System.out.println("Report Path: " + filePath.toString());
//
//	          if (!resource.exists() || !resource.isReadable()) {
//	              throw new RuntimeException("File not found or not readable: " + reportDoc.getGeneratedFileName());
//	          }
//	          
//	          String username = SecurityUtils.getCurrentUser().getUsername();
//	          Users createdBy = userRepo.findByUserName(username).orElse(null);
//	          
//	          Optional<DocumentAccessHistory> entry = 
//	        		    documentAccessHistoryRepo.findByUserIdAndReportDocId(createdBy.getUserId(), reportDocId);
//	          if(entry.isEmpty()) {
//		          DocumentAccessHistory history = new DocumentAccessHistory();
//		          history.setAccessTime(LocalDateTime.now());
//		          history.setReportDocId(reportDocId);
//		          history.setUserId(createdBy.getUserId());
//		          history.setTransactionId(reportDoc.getTransactionId());
//		          documentAccessHistoryRepo.save(history);
//	          }
//	          
//	          // Return the file for download
//	          return ResponseEntity.ok()
//	                  .contentType(MediaType.APPLICATION_OCTET_STREAM)
//	                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reportDoc.getFileName() + "\"")
//	                  .body(resource);
//
//	      } catch (Exception e) {
//	          e.printStackTrace();
//	          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	      }
//	  }
	// COmmented By Lokesh
//	@PostMapping("/downloadFile")
//	public ResponseEntity<?> downloadFile(@RequestBody ReportDocument reportDocument) {
//		try {
//			Long reportDocId = reportDocument.getReportDocId();
//
//			Optional<ReportDocument> reportDocOptional = reportDocumentRepo.findById(reportDocId);
//			if (!reportDocOptional.isPresent()) {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND)
//						.body("Report document with ID " + reportDocId + " not found.");
//			}
//
//			ReportDocument reportDoc = reportDocOptional.get();
//			Path filePath = Paths.get(uploadDirectory, reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
//			Resource resource = new UrlResource(filePath.toUri());
//
//			if (!resource.exists() || !resource.isReadable()) {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND)
//						.body("File not found or not readable: " + reportDoc.getGeneratedFileName());
//			}
//
//			String username = SecurityUtils.getCurrentUser().getUsername();
//			Users createdBy = userRepo.findByUserName(username).orElse(null);
//			if (createdBy == null) {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized.");
//			}
//
//			Optional<DocumentAccessHistory> entry = documentAccessHistoryRepo
//					.findByUserIdAndReportDocId(createdBy.getUserId(), reportDocId);
//			if (!entry.isPresent()) {
//				DocumentAccessHistory history = new DocumentAccessHistory();
//				history.setAccessTime(LocalDateTime.now());
//				history.setReportDocId(reportDocId);
//				history.setUserId(createdBy.getUserId());
//				history.setTransactionId(reportDoc.getTransactionId());
//				documentAccessHistoryRepo.save(history);
//			}
//
//			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reportDoc.getFileName() + "\"")
//					.body(resource);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error occurred while downloading the file: " + e.getMessage());
//		}
//	}

	@PostMapping("/downloadFile")
	public ResponseEntity<?> downloadFile(@RequestBody ReportDocument reportDocument) {

		try {
			Long reportDocId = reportDocument.getReportDocId();

			Optional<ReportDocument> reportOptional = reportDocumentRepo.findById(reportDocId);

			if (!reportOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			ReportDocument reportDoc = reportOptional.get();
//            File file = resolveFile(document);
//
//            if (!file.exists()) {
//                return ResponseEntity.notFound().build();
//            }
//
			String fileType = reportDoc.getType() != null ? reportDoc.getType().toLowerCase() : "";
//			Path filePath = Paths.get(uploadDirectory, reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
			Path filePath;
			if (reportDoc.getDeleteFilePath().startsWith(uploadDirectory)) {
				filePath = Paths.get(reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
			} else {
				filePath = Paths.get(uploadDirectory, reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
			}
			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists() || !resource.isReadable()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("File not found or not readable: " + reportDoc.getGeneratedFileName());
			}

			String username = SecurityUtils.getCurrentUser().getUsername();
			Users createdBy = userRepo.findByUserName(username).orElse(null);
			if (createdBy == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized.");
			}

			Optional<DocumentAccessHistory> entry = documentAccessHistoryRepo
					.findByUserIdAndReportDocId(createdBy.getUserId(), reportDocId);
			if (!entry.isPresent()) {
				DocumentAccessHistory history = new DocumentAccessHistory();
				history.setAccessTime(LocalDateTime.now());
				history.setReportDocId(reportDocId);
				history.setUserId(createdBy.getUserId());
				Long transId = reportDoc.getTransactionId();
				if (transId != null && documentTransactionRepo.existsById(transId)) {
					history.setTransactionId(transId);
				} else {
					System.out.println(
							"Warning: Transaction ID " + transId + " does not exist. Saving history without it.");
				}

				documentAccessHistoryRepo.save(history);
				;
			}

			// IMPORTANT CHANGE
			if (reportDoc.isPreview() && fileType.matches("doc|docx|ppt|pptx|odt|ods")) {
//                file = conversionService.convertToPdfAsync(file);
				fileType = "pdf";
			}

//            Resource resource = new UrlResource(file.toURI());

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + reportDoc.getFileName() + "\"")
					.body(resource);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	private String getContentType(String fileType) {
		switch (fileType) {
		case "pdf":
			return "application/pdf";
		case "png":
			return "image/png";
		case "jpg":
		case "jpeg":
			return "image/jpeg";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		default:
			return "application/octet-stream";
		}
	}

//	private File resolveFile(ReportDocument document) {
//
//		String financialYear = document.getUploadFinancialYear();
//		File file;
//
//		if (financialYear == null || financialYear.trim().isEmpty()) {
//
//			file = new File(Paths.get(uploadPath, document.getGeneratedFileName()).toString());
//
//		} else {
//
//			file = new File(
//					Paths.get(vafuploadPath, poquoteupPath, financialYear, document.getGeneratedFileName()).toString());
//		}
//
//		return file;
//	}

	@PostMapping("/deleteTransaction")
	public ResponseEntity<String> deleteDocumentTransaction(@RequestBody DocumentTransaction doc) {
		try {
			Long transactionId = doc.getTransactionId();

			List<DocumentAccessHistory> histories = documentAccessHistoryRepo.findByTransactionId(transactionId);
			for (DocumentAccessHistory history : histories) {
				documentAccessHistoryService.deleteHistory(history);
			}

			// Find all report documents related to the transaction ID
			List<ReportDocument> reportDocuments = reportDocumentRepo.findByTransactionId(transactionId);

			// Delete each report document using the service method
			for (ReportDocument reportDocument : reportDocuments) {
				documentTransactionService.deleteReportDocument(reportDocument);
			}

			// After deleting all report documents, delete the transaction itself
			documentTransactionRepo.deleteById(transactionId);

			return new ResponseEntity<>("Document Transaction and associated Report Documents deleted successfully.",
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error occurred while deleting: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Step 1

	/*
	 * @PostMapping("/excelReport") public Map excelReport(@RequestBody
	 * DocumentTransaction doc) throws IOException{ Map map = new HashMap(); String
	 * isSaved = "";
	 * 
	 * Map returnMap = documentTransactionService.writeExcel(doc);
	 * 
	 * return returnMap;
	 * 
	 * }
	 */

	// Dorect Download with saving on folder V2

	/*
	 * @PostMapping("/excelReport") public ResponseEntity<Resource>
	 * excelReport(@RequestBody DocumentTransaction doc) throws IOException {
	 * Map<String, Object> returnMap = documentTransactionService.writeExcel(doc);
	 * 
	 * String fileLocation = (String) returnMap.get("fileLocation"); if
	 * (fileLocation == null || fileLocation.isEmpty()) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); }
	 * 
	 * // Load the file File file = new File(fileLocation); if (!file.exists()) {
	 * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); }
	 * 
	 * // Prepare resource for download Resource resource = new
	 * UrlResource(file.toURI());
	 * 
	 * // Set the content-disposition header to indicate a file download HttpHeaders
	 * headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=" + file.getName());
	 * 
	 * return ResponseEntity.ok() .headers(headers) .contentLength(file.length())
	 * .contentType(MediaType.parseMediaType(
	 * "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	 * .body(resource); }
	 */
	// Excel file Without saving on Server
	@PostMapping("/excelReport")
	public ResponseEntity<StreamingResponseBody> excelReport(@RequestBody DocumentTransaction doc) throws IOException {
		List<DocumentTransaction> docList = documentTransactionService.getTransaction(doc); // Assuming you have a
																							// method to fetch the list
		StreamingResponseBody responseBody = excelReports.writeDocTransacExcelReport(docList);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Document_Report.xlsx");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(responseBody);
	}

	@PostMapping("/selectedExcelReport")
	public ResponseEntity<StreamingResponseBody> selectedExcelReport(@RequestBody List<DocumentTransaction> docList)
			throws IOException {
		// Generate the Excel report and get the StreamingResponseBody
		StreamingResponseBody responseBody = excelReports.writeDocTransacExcelReport(docList);

		// Prepare the response with appropriate headers
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=Document_Report_" + System.currentTimeMillis() + ".xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM) // Set appropriate content type
				.body(responseBody);
	}

	@Transactional
	@PostMapping("/deleteFile")
	public ResponseEntity<Map<String, Object>> deleteFile(@RequestBody ReportDocument request) {
		Map<String, Object> response = new HashMap<>();
		try {
			Long reportDocId = request.getReportDocId();

			Optional<ReportDocument> reportOptional = reportDocumentRepo.findById(reportDocId);
			if (!reportOptional.isPresent()) {
				response.put("message", "File record not found in database");
				response.put("status", 0);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			ReportDocument reportDoc = reportOptional.get();

			Path filePath;
			if (reportDoc.getDeleteFilePath().startsWith(uploadDirectory)) {
				filePath = Paths.get(reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
			} else {
				filePath = Paths.get(uploadDirectory, reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
			}

			File file = filePath.toFile();
			if (file.exists()) {
				file.delete();
			}
			downloadHistoryRepo.deleteByReportDocId(reportDocId);
			documentAccessHistoryRepo.deleteByReportDocId(reportDocId);
			reportDocumentRepo.deleteById(reportDocId);

			response.put("message", "File and record deleted successfully");
			response.put("status", 1);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Error occurred: " + e.getMessage());
			response.put("status", 0);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
