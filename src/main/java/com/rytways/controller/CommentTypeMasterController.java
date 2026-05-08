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
import com.rytways.model.CommentTypeMaster;
import com.rytways.repository.CommentTypeMasterRepo;
import com.rytways.service.CommentTypeMasterService;

@RestController
@RequestMapping("/commentType")
public class CommentTypeMasterController {

	@Autowired
	private CommentTypeMasterService commentTypeMasterService;
	
	@Autowired
	private CommentTypeMasterRepo commentTypeMasterRepo;
	
	
	@PostMapping("/loadOptions")
	public ResponseEntity<List<LoadOptionsStringDto>> loadOptions(@RequestBody CommentTypeMaster data) {
	    
		List<LoadOptionsStringDto> list = commentTypeMasterService.loadType(data);
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
