package com.rytways.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rytways.model.QueryDocuments;
import com.rytways.repository.QueryDocumentsRepo;
import com.rytways.service.QueryDocumentsService;

@RestController
@RequestMapping("/queryDoc")
public class QueryDocumentsController {
	
	@Autowired
	private QueryDocumentsRepo queryDocumentsRepo;
	
	
	@Autowired
	private QueryDocumentsService queryDocumentsService;
	
	@Value("${docReportUploadPath}")
	  private String uploadDirectory;
	
	@PostMapping("/getListByType")
    public List < QueryDocuments > uploadMultipleFiles(@RequestBody QueryDocuments idDto) {
    	
    	Optional<List<QueryDocuments>> list = queryDocumentsRepo.findByReportTypeAndQueryIdOrderByUpdatedOn(idDto.getReportType(),idDto.getQueryId());
    	
        return list.get();
    }
	
	
	
	
	@PostMapping("/uploadFile")
	 public Map uploadFile(@RequestParam("files") MultipartFile[] files, @RequestParam("reportType") String reportType,
	         @RequestParam("remarks") String remarks, @RequestParam("queryId") Long queryId,
	         @RequestParam("filePath") String filePath) {
	     
	     List<QueryDocuments> savedReports = new ArrayList<>();
	     Map retValues = new HashMap();
	     try {
	         for (MultipartFile file : files) {
	        	 QueryDocuments report = new QueryDocuments();
	             report.setReportType(reportType);
	             report.setRemarks(remarks);
	             report.setQueryId(queryId);
	             report.setFileName(file.getOriginalFilename());

	             String originalFilename = file.getOriginalFilename();
	             String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

	             report.setDocType(extension);

	             String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	             String generatedFileName = timeStamp + "." + extension;
	             report.setGeneratedFileName(generatedFileName);
	             
	             String docFolder = "QUERY";

	             String directoryPath = Paths.get(uploadDirectory, docFolder, filePath).toString();
	             String downloadPath = Paths.get(uploadDirectory, docFolder, filePath).toString();
	             String deltefilePath = Paths.get(uploadDirectory, docFolder, filePath).toString();
	             report.setFilePath(downloadPath);
	             report.setDeleteFilePath(deltefilePath);

	             report = queryDocumentsRepo.save(report);
	             savedReports.add(report);

	             File directory = new File(directoryPath);
	             if (!directory.exists()) {
	                 directory.mkdirs();
	             }

	             Path filePaths = Paths.get(directoryPath, generatedFileName);
	             File destinationFile = filePaths.toFile();
	             file.transferTo(destinationFile);
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

	
	@PostMapping("/delete")
    public ResponseEntity<String> deleteReportDocument(@RequestBody QueryDocuments request) {
        try {
          //  Long reportDocId = request.getReportDocId();
        	queryDocumentsService.deleteReportDocument(request);
            return new ResponseEntity<>("ReportDocument deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
	
	
	@PostMapping("/downloadFile")
	  public ResponseEntity<Resource> downloadFile(@RequestBody QueryDocuments reportDocument) {
	      try {
	          Long reportDocId = reportDocument.getReportDocId();

	          // Fetch the report document from the database using reportDocId
	          Optional<QueryDocuments> reportDocOptional = queryDocumentsRepo.findById(reportDocId);

	          if (!reportDocOptional.isPresent()) {
	              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	          }

	          QueryDocuments reportDoc = reportDocOptional.get();

	          // Construct file path from document entity
	          Path filePath = Paths.get(reportDoc.getDeleteFilePath(), reportDoc.getGeneratedFileName());
	          Resource resource = new UrlResource(filePath.toUri());
	          System.out.println("Report Path: " + filePath.toString());

	          if (!resource.exists() || !resource.isReadable()) {
	              throw new RuntimeException("File not found or not readable: " + reportDoc.getGeneratedFileName());
	          }

	          // Return the file for download
	          return ResponseEntity.ok()
	                  .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reportDoc.getFileName() + "\"")
	                  .body(resource);

	      } catch (Exception e) {
	          e.printStackTrace();
	          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	  }
	
}
