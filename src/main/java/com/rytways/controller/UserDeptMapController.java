package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.UserDeptMap;
import com.rytways.repository.UserDepartMapRepository;
import com.rytways.service.UserDeptMapService;

@RestController
@RequestMapping("/userDepMap")
public class UserDeptMapController {
	
	
	@Autowired
	UserDeptMapService userDeptMapService;
	
	@Autowired
	UserDepartMapRepository UserDeptMapRepo; 
	
	
	@PostMapping("/create")
	public ResponseEntity<List<UserDeptMap>> createUserDeptMap(
	        @RequestBody UserDeptMap request) {
	        
		 List<UserDeptMap> userDeptMaps = userDeptMapService.createUserDeptMap(request);

	        // Return the created list with a 200 OK response
	        return new ResponseEntity<>(userDeptMaps, HttpStatus.OK);
	    }
	@PostMapping("/userDepMap")
	public ResponseEntity<List<UserDeptMap>> listUserDeptMaps(@RequestBody UserDeptMap request) {
	    List<UserDeptMap> map = UserDeptMapRepo.findByUserId(request.getUserId());

	  	   

	    return new ResponseEntity<>(map, HttpStatus.OK);
	}


	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteFolder(@RequestBody UserDeptMap request) {
	    try {
	        Integer userDeptMapId = request.getUserDeptMapId();
	        userDeptMapService.deleteUserDep(userDeptMapId);
	        return new ResponseEntity<>("Folder deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}
	
	
}
