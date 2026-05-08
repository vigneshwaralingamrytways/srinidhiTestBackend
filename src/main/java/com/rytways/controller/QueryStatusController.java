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
import com.rytways.model.QueryStatusMaster;
import com.rytways.repository.QueryStatusMasterRepo;
import com.rytways.service.QueryStatusService;

@RestController
@RequestMapping("/queryStatus")
public class QueryStatusController {

	@Autowired
	private QueryStatusService queryStatusService;
	
	@Autowired
	private QueryStatusMasterRepo queryStatusMasterRepo;
	
	
	@PostMapping("/loadOptions")
	public ResponseEntity<List<LoadOptionsStringDto>> loadOptions(@RequestBody QueryStatusMaster data) {
	    
		List<LoadOptionsStringDto> list = queryStatusService.loadStatus(data);
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
