package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.GateMaster;
import com.rytways.repository.GateMasterRepo;

@RestController
@RequestMapping("/gateMaster")
public class GateMasterController {
	
	@Autowired
	private GateMasterRepo gateMasterRepo;
	
	@PostMapping("/getAllGateMasters")
	private ResponseEntity<List<GateMaster>> getAllGateMasters(){
		return new ResponseEntity<List<GateMaster>>(gateMasterRepo.findAll(),HttpStatus.OK);
	}

}
