package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.ActivityMasterFeatures;
import com.rytways.model.FunctionMaster;
import com.rytways.model.ProcessMaster;
import com.rytways.repository.ProcessMasterRepo;
import com.rytways.service.ProcessMasterService;

@RestController
@RequestMapping("/processMaster")
public class ProcessMasterController {

	@Autowired
	private ProcessMasterService processMasterService;
	
	
	@Autowired
	private ProcessMasterRepo processRepo;

	
	@PostMapping("/create")
	public ResponseEntity <ProcessMaster> createProcess (@RequestBody ProcessMaster doc){
		
		doc=processMasterService.createProcess(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	

	@PostMapping("/processMaster")
	public ResponseEntity<List<ProcessMaster>> loadModules(@RequestBody ProcessMaster request) {
	    List<ProcessMaster> menus = processMasterService.getProcessMaster();
	    return new ResponseEntity<>(menus, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteProcess(@RequestBody ProcessMaster request) {
	    try {
	       
	        processMasterService.deleteProcess(request.getProcessId());
	        return new ResponseEntity<>("Process deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}
	
	@PostMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadType(@RequestBody ProcessMaster request){
	 		 	
	 
	 	List<LoadOptionsDto> loadTypeList=processMasterService.loadType();
       
        return new ResponseEntity<>(loadTypeList,HttpStatus.OK);
    } 
	
	@PostMapping("/findProcessById")
	public ResponseEntity<?> findById(@RequestBody ProcessMaster request) {
	    try {
	        
	        ProcessMaster process = processMasterService.findProcessById(request.getProcessId());
	        if (process != null) {
	            return new ResponseEntity<>(process, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Process not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
