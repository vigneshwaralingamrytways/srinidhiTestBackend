package com.rytways.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.DocumentTransaction;
import com.rytways.model.ReportDocument;
import com.rytways.repository.DocumentTransactionRepo;
import com.rytways.repository.DocumentTypeRepo;
import com.rytways.repository.FolderRepository;
import com.rytways.repository.ReportDocumentRepo;
import com.rytways.repository.SubFolderMasterRepo;
import com.rytways.specifications.DocTransacSpec;


@Component
@Service
@Transactional
public class DocumentTransactionService {
	
	
	@Autowired
	private DocumentTransactionRepo documentTransactionRepo;
	
	@Autowired
	private DocumentTypeRepo documentTypeRepo;
	
	@Autowired
	private FolderRepository folderRepository;
	
	@Autowired
	private SubFolderMasterRepo subFolderMasterRepo;
	
	@Autowired
	private ReportDocumentRepo reportDocumentRepo;
	

	
	

	@Value("${docReportUploadPath}")
	  private String uploadDirectory;
//	@Transactional
//	public DocumentTransaction createTransac(DocumentTransaction doc) {
//  
//
//	    
//
//	    // Save the DocumentTransaction entity
//	    doc = documentTransactionRepo.save(doc);
//	    
//	    if (doc.getDocumentTypeId() != null) {
//	    	
//	    	Optional<DocumentTypeMaster> docTypeOpt = documentTypeRepo.findById(doc.getDocumentTypeId());
//	    	
//	    	docTypeOpt.ifPresent(doc::setDocumentTypeMaster);
//	    	
//	    }
// if (doc.getFolderId() != null) {
//	    	
//	 Optional<FolderMaster> folderOpt = folderRepository.findById(doc.getFolderId());
//	    	
//	 folderOpt.ifPresent(doc::setFolderMaster);
//	    	
//	    }
// if (doc.getSubFolderId() != null) {
// 	
//	 Optional<SubFolderMaster> subFolderOpt = subFolderMasterRepo.findById(doc.getSubFolderId());
//	 
//	 subFolderOpt.ifPresent(doc::setSubFolderMaster);
// 	
// }
//	    
//	
//	    
//	    return doc;
//	}
	public byte[] viewFile(Long reportDocId) throws Exception {

	    ReportDocument doc = reportDocumentRepo.findById(reportDocId)
	            .orElseThrow(() -> new Exception("File not found"));

	    // Build full file path
	    Path filePath = Paths.get(uploadDirectory, doc.getDeleteFilePath(), doc.getGeneratedFileName());

	    // Read file as bytes
	    return Files.readAllBytes(filePath);
	}
	
	@Transactional
	public DocumentTransaction createTransac(DocumentTransaction doc) {

	    // Save the DocumentTransaction entity
	    doc = documentTransactionRepo.save(doc);

	    // Set DocumentTypeMaster if DocumentTypeId is provided
	    if (doc.getDocumentTypeId() != null) {
	        documentTypeRepo.findById(doc.getDocumentTypeId())
	                        .ifPresent(doc::setDocumentTypeMaster);
	    }

	    // Set FolderMaster if FolderId is provided
	    if (doc.getFolderId() != null) {
	        folderRepository.findById(doc.getFolderId())
	                        .ifPresent(doc::setFolderMaster);
	    }

	    // Set SubFolderMaster if SubFolderId is provided
	    if (doc.getSubFolderId() != null) {
	        subFolderMasterRepo.findById(doc.getSubFolderId())
	                           .ifPresent(doc::setSubFolderMaster);
	    }

	    return doc;
	}


	public DocumentTransaction editTransac(DocumentTransaction doc) {
		
		doc.setDocumentTypeMaster(null);	
		doc.setFolderMaster(null);
		doc.setSubFolderMaster(null);
		
		
		// Save the DocumentTransaction entity
	    doc = documentTransactionRepo.save(doc);
	    
	    
		// TODO Auto-generated method stub
		return doc;
	}
	
	
	public List<DocumentTransaction> getTransaction(DocumentTransaction transac) {
		
		
		Specification spec1 = DocTransacSpec.createdDateFromDate(transac.getFromDate()); 
	    Specification spec2 = DocTransacSpec.createdDateTillDate(transac.getToDate()); 
		Specification spec3 = DocTransacSpec.docTypeIdEqual(transac.getDocumentTypeId()); 
	    Specification spec4 = DocTransacSpec.folderIdEqual(transac.getFolderId()); 
	    Specification spec5 = DocTransacSpec.subFolderIdEqual(transac.getSubFolderId());
	    
	    Specification<DocumentTransaction> spec = Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5);

		return documentTransactionRepo.findAll(spec);
	}


//	public void deleteReportDocument(ReportDocument reportDocument) throws Exception {
//	    if (reportDocument != null) {
//	        // Delete the file matching generatedFileName within the filePath directory
//	        if (reportDocument.getFilePath() != null && reportDocument.getGeneratedFileName() != null) {
//	            Path directoryPath = Paths.get(reportDocument.getDeleteFilePath());
//	            Path fileToDelete = directoryPath.resolve(reportDocument.getGeneratedFileName());
//
//	            Files.deleteIfExists(fileToDelete); // Deletes the file if it exists
//	        }
//
//	        // Delete the record from the database
//	        reportDocumentRepo.deleteById(reportDocument.getReportDocId());
//	    } else {
//	        throw new Exception("ReportDocument is null or with ID " + reportDocument.getReportDocId() + " not found.");
//	    }
//	}


	public void deleteReportDocument(ReportDocument reportDocument) throws Exception {
	    if (reportDocument != null) {
	        // Delete the file from the filesystem
	        if (reportDocument.getFilePath() != null && reportDocument.getGeneratedFileName() != null) {
	            Path directoryPath = Paths.get(uploadDirectory, reportDocument.getDeleteFilePath());
	            Path fileToDelete = directoryPath.resolve(reportDocument.getGeneratedFileName());

	            Files.deleteIfExists(fileToDelete); // Deletes the file if it exists
	        }

	        // Delete the record from the database
	        reportDocumentRepo.deleteById(reportDocument.getReportDocId());

	        // Check if any documents are left for the transaction
	        List<ReportDocument> remainingDocs = reportDocumentRepo.findByTransactionId(reportDocument.getTransactionId());

	        // Update the docAvailable flag based on remaining documents
	        Optional<DocumentTransaction> optionalDocumentTrans = documentTransactionRepo.findById(reportDocument.getTransactionId());
	        if (optionalDocumentTrans.isPresent()) {
	            DocumentTransaction documentTrans = optionalDocumentTrans.get();
	            if (remainingDocs.isEmpty()) {
	                documentTrans.setDocAvailable(0); // No documents left
	            } else {
	                documentTrans.setDocAvailable(1); // Documents still available
	            }
	            documentTransactionRepo.save(documentTrans);
	        }
	    } else {
	        throw new Exception("ReportDocument is null or with ID " + reportDocument.getReportDocId() + " not found.");
	    }
	}


	



}
