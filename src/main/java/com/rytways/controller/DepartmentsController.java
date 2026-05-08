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

import com.rytways.dto.IdDto;
import com.rytways.dto.LoadOptionIntegerDto;
import com.rytways.model.DepartmentMaster;
import com.rytways.model.Users;
import com.rytways.repository.DepartRepository;
import com.rytways.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentsController {

	
	@Autowired
	private DepartRepository departRepo;
	
	private final DepartmentService departService;
	
	@Autowired
	public DepartmentsController (DepartmentService departService) {
		this.departService = departService;
	}
	
	@PostMapping("/create")
    public ResponseEntity<DepartmentMaster> createdepartment(@RequestBody DepartmentMaster department){
	 
	 	String isSaved = "";
	 
	 	department = departService.saveDepartment(department);
       
        return new ResponseEntity<>(department,HttpStatus.OK);
    }
	
	@GetMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionIntegerDto>> loadDepartmentOptions(){
	 
	 	
	 
	 	List<LoadOptionIntegerDto> departments=departService.loadDepartmentOptions();
       
        return new ResponseEntity<>(departments,HttpStatus.OK);
    }
	
	@GetMapping("/department")
    public ResponseEntity <List<DepartmentMaster>> listDepartments(){
	 
	 
	 
	 	List<DepartmentMaster> departments=departRepo.findAll();
       
        return new ResponseEntity<>(departments,HttpStatus.OK);
    } 
	
	@PostMapping("/loadSpecificDepartments")
    public ResponseEntity <List<LoadOptionIntegerDto>> loadDepartmentSpecificOptions(@RequestBody IdDto idDto){
	  	
	 List<LoadOptionIntegerDto> departments=departRepo.findLoadOptions(idDto.getDepartmentIds());
	       
        return new ResponseEntity<>(departments,HttpStatus.OK);
    }
	
	@PostMapping("/searchDepartment")
    public ResponseEntity<List<DepartmentMaster>> searcDepartments(@RequestBody DepartmentMaster department){
	 		
	 		String isSaved = "";
			 	
		 	List<DepartmentMaster> options = departService.getDepartmentDetails(department);
	       
	        return new ResponseEntity<>(options,HttpStatus.OK);

}
	
	
}
