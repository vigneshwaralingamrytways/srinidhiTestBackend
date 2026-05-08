package com.rytways.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.DocumentTypeMaster;
import com.rytways.model.DocumentUserMaster;
 
import com.rytways.repository.DocumentTypeRepo;
import com.rytways.repository.DocumentUserMasterRepo;
import com.rytways.service.DocTypeService;

@RestController
@RequestMapping("/documentTypeMaster")
public class DocTypeMasterController {
	
	@Autowired
	private DocumentTypeRepo documentTypeRepo;
	
	
	@Autowired
	private DocTypeService docTypeService;
	
	@Autowired
	private DocumentUserMasterRepo documentUserMasterRepo;

	
	@PostMapping("/create")
	public ResponseEntity <DocumentTypeMaster> createType (@RequestBody DocumentTypeMaster doc){
		
		doc=docTypeService.createType(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	
	
	@PostMapping("/documentTypeMaster")
	public ResponseEntity <List<DocumentTypeMaster>> listDoc(@RequestBody DocumentTypeMaster document){
		List<DocumentTypeMaster> doc=documentTypeRepo.findAll();
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	@GetMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadType(){
	 		 	
	 
	 	List<LoadOptionsDto> loadTypeList=docTypeService.loadType();
       
        return new ResponseEntity<>(loadTypeList,HttpStatus.OK);
    } 
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteDocType(@RequestBody DocumentTypeMaster request) {
	    try {
	        Long documentTypeId = request.getDocumentTypeId();
	        docTypeService.deleteDocType(documentTypeId);
	        return new ResponseEntity<>("Document Type deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}
	
	@PostMapping("/getAllDocUserDetails")
	private ResponseEntity<?> getAllDocUserDetails(@RequestBody DocumentUserMaster documentUserMaster){
		Optional<DocumentUserMaster> entry= documentUserMasterRepo.findByUserIdAndDocumentTypeId(documentUserMaster.getUserId(),documentUserMaster.getDocumentTypeId());
		if(entry.isPresent())
		  return new ResponseEntity<>(entry.get(),HttpStatus.OK);
		else
		  return new ResponseEntity<>("Data Not Found",HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/searchDocTypes")
	private ResponseEntity<List<DocumentTypeMaster>> searchDocTypes(@RequestBody DocumentTypeMaster documentType){
		List<DocumentTypeMaster> entry= docTypeService.searchDocTypes(documentType);
		return new ResponseEntity<List<DocumentTypeMaster>>(entry,HttpStatus.OK);
	}
	
}
