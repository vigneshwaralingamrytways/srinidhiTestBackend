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
import com.rytways.model.FunctionMaster;
import com.rytways.model.ProcessMaster;
import com.rytways.repository.FunctionMasterRepo;
import com.rytways.service.FunctionMasterService;

@RestController
@RequestMapping("/functionMaster")
public class FunctionMasterController {

	
	@Autowired
	private FunctionMasterRepo functionRepo;
	
	
	@Autowired
	private FunctionMasterService functionService;

	
	@PostMapping("/create")
	public ResponseEntity <FunctionMaster> createFunction (@RequestBody FunctionMaster doc){
		
		doc=functionService.createFunction(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	@PostMapping("/edit")
	public ResponseEntity <FunctionMaster> editFunction (@RequestBody FunctionMaster doc){
		
		doc=functionService.editFunction(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	@PostMapping("/functionMaster")
	public ResponseEntity<List<FunctionMaster>> loadModules(@RequestBody FunctionMaster request) {
	    List<FunctionMaster> menus = functionService.getFunctionMaster(request.getProcessId());
	    return new ResponseEntity<>(menus, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteFunction(@RequestBody FunctionMaster request) {
	    try {
	       
	    	functionService.deleteFunction(request.getFunctionId());
	        return new ResponseEntity<>("Function deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}
	
	@PostMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadType(@RequestBody FunctionMaster request){
	 		 	
	 
	 	List<LoadOptionsDto> loadTypeList=functionService.loadType(request);
       
        return new ResponseEntity<>(loadTypeList,HttpStatus.OK);
    } 
	
	@PostMapping("/loadFunctionOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadTypes(@RequestBody FunctionMaster request){
	 		 	
	 
	 	List<LoadOptionsDto> loadTypeList=functionService.loadTypes(request);
       
        return new ResponseEntity<>(loadTypeList,HttpStatus.OK);
    } 
	
	@PostMapping("/findFunctionById")
	public ResponseEntity<?> findById(@RequestBody FunctionMaster request) {
	    try {
	        
	    	FunctionMaster process = functionService.findFunctionMasterById(request.getFunctionId());
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
