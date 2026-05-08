package com.rytways.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.QueryDocuments;
import com.rytways.repository.QueryDocumentsRepo;

@Component
@Service
@Transactional
public class QueryDocumentsService {
	
	@Autowired
	private QueryDocumentsRepo queryDocumentsRepo;

	public void deleteReportDocument(QueryDocuments reportDocument) throws Exception {
		    if (reportDocument != null) {
		        // Delete the file from the filesystem
		        if (reportDocument.getFilePath() != null && reportDocument.getGeneratedFileName() != null) {
		            Path directoryPath = Paths.get( reportDocument.getDeleteFilePath());
		            Path fileToDelete = directoryPath.resolve(reportDocument.getGeneratedFileName());

		            Files.deleteIfExists(fileToDelete); // Deletes the file if it exists
		        }

		        // Delete the record from the database
		        queryDocumentsRepo.deleteById(reportDocument.getReportDocId());

		        // Check if any documents are left for the transaction
		        /*   List<ReportDocument> remainingDocs = queryDocumentsRepo.findByTransactionId(reportDocument.getTransactionId());

		        // Update the docAvailable flag based on remaining documents
		      Optional<DocumentTransaction> optionalDocumentTrans = queryDocumentsRepo.findById(reportDocument.getTransactionId());
		        if (optionalDocumentTrans.isPresent()) {
		            DocumentTransaction documentTrans = optionalDocumentTrans.get();
		            if (remainingDocs.isEmpty()) {
		                documentTrans.setDocAvailable(0); // No documents left
		            } else {
		                documentTrans.setDocAvailable(1); // Documents still available
		            }
		            queryDocumentsRepo.save(documentTrans);
		        } */
		    } else {
		        throw new Exception("ReportDocument is null or with ID " + reportDocument.getReportDocId() + " not found.");
		    }
		}
}
