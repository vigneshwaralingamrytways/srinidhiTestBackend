package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.LoadOptionsStringDto;
import com.rytways.model.CommentMasterStatus;
import com.rytways.repository.CommentMasterStatusRepo;
import com.rytways.service.CommentMasterStatusService;

@RestController
@RequestMapping("/commentStatus")
public class CommentMasterStatusController {

	
	@Autowired
	private CommentMasterStatusService commentMasterStatusService;
	
	@Autowired
	private CommentMasterStatusRepo commentMasterStatusRepo;
	
	
	@PostMapping("/loadOptions")
	public ResponseEntity<List<LoadOptionsStringDto>> loadOptions(@RequestBody CommentMasterStatus data) {
	    
		List<LoadOptionsStringDto> list = commentMasterStatusService.loadType(data);
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
