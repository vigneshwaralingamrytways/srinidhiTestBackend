package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.DocumentUserMaster;
import com.rytways.model.FolderMaster;
import com.rytways.model.ReportDocument;
import com.rytways.repository.DocumentUserMasterRepo;
import com.rytways.service.DocumentUserService;

@RestController
@RequestMapping("/docUserMaster")
public class DocUserController {
	
	
	@Autowired
	private DocumentUserService documentUserService;
	
	@Autowired
	private DocumentUserMasterRepo documentUserMasterRepo;
	
	
	@PostMapping("/create")
	public ResponseEntity <DocumentUserMaster> createUser (@RequestBody DocumentUserMaster user){
		
		user=documentUserService.createUser(user);
		
		return new ResponseEntity <>(user,HttpStatus.OK);
	}
	
	
	@PostMapping("/getListByDocumentTypeId")
	public ResponseEntity<List<DocumentUserMaster>> getListById(@RequestBody DocumentUserMaster user) {
	    List<DocumentUserMaster> list = documentUserMasterRepo.findByDocumentTypeIdOrderByUpdatedOn(user.getDocumentTypeId());
	    
	    if (!list.isEmpty()) {
	        return new ResponseEntity<>(list, HttpStatus.OK);
	    } else {
	    	 return new ResponseEntity<>(list, HttpStatus.OK);
   }
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteReportDocument(@RequestBody DocumentUserMaster request) {
	    try {
	        Long docUserId = request.getDocUserId();
	        documentUserService.deleteDocument(docUserId);
	        return new ResponseEntity<>("Document deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}

	
	@PostMapping("/getListByUserId")
	public ResponseEntity<List<DocumentUserMaster>> getListByUserId(@RequestBody DocumentUserMaster doc) {
	    List<DocumentUserMaster> folderList = documentUserMasterRepo.findByUserIdOrderByUpdatedOn(doc.getUserId());
	    
	    if (!folderList.isEmpty()) {
	        return new ResponseEntity<>(folderList, HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<>(folderList, HttpStatus.OK);
	    }
	}

	
//	@PostMapping("/getListByDocIdAndUserId")
//	public ResponseEntity<List<DocumentUserMaster>> getListByUserIdAndDocID(@RequestBody DocumentUserMaster doc) {
//	    List<DocumentUserMaster> folderList = documentUserMasterRepo.findByDocumentTypeIdAndUserIdOrderByUpdatedOn(doc.getDocumentTypeId(), doc.getUserId());
//	    return new ResponseEntity<>(folderList, HttpStatus.OK);
//	}

	
}
