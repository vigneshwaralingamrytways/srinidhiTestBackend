package com.rytways.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.rytways.model.DocumentTransaction;
import com.rytways.model.FormConfigMaster;
import com.rytways.repository.FormConfigMasterRepo;




@Component
@Service
@Transactional
public class WriteExcelReports {

	
//	@Value("${pathofInvoiceExcelReport}")
//	private String pathofInvoiceExcelReport;
//	
//	@Value("${pathofPurchaseExcelReport}")
//	private String pathofPurchaseExcelReport;
//	
//	@Value("${docReportUploadPath}")
//	private String docReportUploadPath;
//	
//	@Value("${digitReportExcelUploadPath}")
//	private String digitReportExcelUploadPath;
	
	
	
	@Autowired
	FormConfigMasterRepo formConfigRepository;
	
	
	
	
	// Final Version Without saving excel on server 
	
	public StreamingResponseBody writeDocTransacExcelReport(List<DocumentTransaction> docList) throws IOException {
	    // Check if the docList is not empty
	    if (docList == null || docList.isEmpty()) {
	        throw new IllegalArgumentException("Document list is empty");
	    }
	    
//	    System.out.println("Length of size========>"+docList.size());

	    // Retrieve the documentTypeId from the first document in the list
	    Long documentTypeId = docList.get(0).getDocumentTypeId();

	    // Retrieve form configuration for the document type
	    Optional<FormConfigMaster> formConfigOpt = formConfigRepository.findByDocumentTypeId(documentTypeId);
	    if (!formConfigOpt.isPresent()) {
	        throw new IllegalArgumentException("Invalid document type id");
	    }

	    FormConfigMaster formConfig = formConfigOpt.get();

	    // Create a workbook and a sheet
	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Document Report");
	    int rowNum = 0;

	    // Dynamically build headers
	    List<String> headers = new ArrayList<>();
	    List<String> formItems = Arrays.asList(
	            formConfig.getItemOne(), formConfig.getItemTwo(), formConfig.getItemThree(),
	            formConfig.getItemFour(), formConfig.getItemFive(), formConfig.getItemSix(),
	            formConfig.getItemSeven(), formConfig.getItemEight(), formConfig.getItemNine(),
	            formConfig.getItemTen(), formConfig.getItemEleven(), formConfig.getItemTwelve(),
	            formConfig.getItemThirteen(), formConfig.getItemFourteen(), formConfig.getItemFifteen()
	    );

	    // Add static headers
	    headers.add("S.No");
	    headers.add("Document Type");
	    headers.add("Folder Name");
	    headers.add("Sub Folder Name");

	    // Add dynamic headers based on formConfig values (ignore empty ones)
	    for (String item : formItems) {
	        if (item != null && !item.trim().isEmpty()) {
	            headers.add(item);
	        } else {
	            break;
	        }
	    }

	    // Create header row
	    Row headerRow = sheet.createRow(rowNum++);
	    for (int i = 0; i < headers.size(); i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headers.get(i));
	        cell.setCellStyle(getHeaderCellStyle(workbook));
	    }

	    // Write data rows
	    int sNo = 1;
	    for (DocumentTransaction doc : docList) {
	        Row row = sheet.createRow(rowNum++);
	        int cellNum = 0;

	        row.createCell(cellNum++).setCellValue(sNo++); // S.No
	        row.createCell(cellNum++).setCellValue(doc.getDocumentTypeMaster().getDocumentType()); // Document Type
	        row.createCell(cellNum++).setCellValue(doc.getFolderMaster() != null ? doc.getFolderMaster().getFolderCategoryName() : ""); // Folder Name
	        row.createCell(cellNum++).setCellValue(doc.getSubFolderMaster() != null ? doc.getSubFolderMaster().getSubFolderCategoryName() : ""); // Sub Folder Name

	        // Add dynamic cell values from DocumentTransaction
	        List<String> docItems = Arrays.asList(
	                doc.getItemOne(), doc.getItemTwo(), doc.getItemThree(),
	                doc.getItemFour(), doc.getItemFive(), doc.getItemSix(),
	                doc.getItemSeven(), doc.getItemEight(), doc.getItemNine(),
	                doc.getItemTen(), doc.getItemEleven(), doc.getItemTwelve(),
	                doc.getItemThirteen(), doc.getItemFourteen(), doc.getItemFifteen()
	        );

	        for (int i = 0; i < headers.size() - 4; i++) { // Start from the 5th column (after static headers)
	            row.createCell(cellNum++).setCellValue(docItems.get(i));
	        }
	    }

	    // Auto-size columns
	    for (int i = 0; i < headers.size(); i++) {
	        sheet.autoSizeColumn(i);
	    }

	    // Stream the workbook to the response output stream
	    return outputStream -> {
	        try {
	            workbook.write(outputStream);
	            // No flush() method; just close the workbook after writing to the stream
	        } catch (IOException e) {
	            e.printStackTrace(); // Log the exception
	            throw e; // Rethrow to let the controller handle it
	        } finally {
	            workbook.close(); // Ensure the workbook is closed
	        }
	    };
	}

	private CellStyle getHeaderCellStyle(Workbook workbook) {
	    CellStyle headerStyle = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setBold(true);
	    headerStyle.setFont(font);
	    return headerStyle;
	}

}


