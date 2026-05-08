package com.rytways.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.DocumentUserMaster;
import com.rytways.repository.DocumentUserMasterRepo;

@Component
@Service
@Transactional
public class DocumentUserService {
	
	
	@Autowired
	private DocumentUserMasterRepo documentUserMasterRepo;
	
	

	public DocumentUserMaster createUser(DocumentUserMaster user) {
		
		user=documentUserMasterRepo.save(user);
		return user;
	}



	public void deleteDocument(Long docUserId) throws Exception {
	    if (docUserId != null) {
	        // Check if the record exists
	        DocumentUserMaster documentUserMaster = documentUserMasterRepo.findById(docUserId)
	                .orElseThrow(() -> new Exception("Document with ID " + docUserId + " not found."));
	        
	        // Ensure no dependent records exist (if necessary)
	        // Example: formConfigMasterRepo.deleteByDocumentTypeId(documentUserMaster.getDocumentTypeId());

	        // Delete the DocumentUserMaster record
	        documentUserMasterRepo.deleteById(docUserId);
	    } else {
	        throw new Exception("DocumentUserId cannot be null.");
	    }
	}

		

}
