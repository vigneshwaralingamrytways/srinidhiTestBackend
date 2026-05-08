package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.IssueSolution;
import com.rytways.model.IssueSolutionComments;
import com.rytways.repository.IssueSolutionRepo;
import com.rytways.service.IssueSolutionService;

@RestController
@RequestMapping("/issueSolution")
public class IssueSolutionController {

	
	@Autowired
	private IssueSolutionRepo issueSolutionRepo;
	
	
	@Autowired
	private IssueSolutionService issueSolutionService;

	
	@PostMapping("/create")
	public ResponseEntity <IssueSolution> createFunction (@RequestBody IssueSolution doc){
		
		doc=issueSolutionService.createIssueSolution(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	@PostMapping("/createComments")
	public ResponseEntity <IssueSolutionComments> createComments (@RequestBody IssueSolutionComments doc){
		
		doc=issueSolutionService.createIssueSolutionComments(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	
	@PostMapping("/loadIssueSolutionWithComments")
	public ResponseEntity<IssueSolution> loadIssueSolutionWithComments(@RequestBody IssueSolution doc) {
	    IssueSolution issue = issueSolutionService.loadDetailsWithComments(doc);
	    return new ResponseEntity<>(issue, HttpStatus.OK);
	}
	
	@PostMapping("/loadIssueSolutionActComments")
	public ResponseEntity<IssueSolution> loadIssueSolutionActComments(@RequestBody IssueSolution doc) {
	    IssueSolution issue = issueSolutionService.loadDetailsWithActComments(doc);
	    return new ResponseEntity<>(issue, HttpStatus.OK);
	}

	
	@PostMapping("/findIssueSolution")
	public ResponseEntity <IssueSolution> loadIssueSolution (@RequestBody IssueSolution doc){
		
		doc=issueSolutionService.loadDetails(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	
	
	
	// Comments
	
	
	@PostMapping("/getAllComments")
	public ResponseEntity<List<IssueSolutionComments>> getAllIssueSolutionComments(@RequestBody IssueSolutionComments com){
		return new ResponseEntity<List<IssueSolutionComments>>(issueSolutionService.getAllIssueSolutionComments(com),HttpStatus.OK);
	}
}
