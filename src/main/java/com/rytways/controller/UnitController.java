package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.dto.LoadOptionsStringDto;
import com.rytways.service.UnitService;

@RestController
@RequestMapping("/unitmaster")
public class UnitController {

	@Autowired
	UnitService unitService;
	
	
	@GetMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionsStringDto>> loadUnitsOptions(){
	 		 	
	 
	 	List<LoadOptionsStringDto> unitList=unitService.loadUnitOptions();
       
        return new ResponseEntity<>(unitList,HttpStatus.OK);
    } 

	@PostMapping("/loadOptionsDto")
    public ResponseEntity <List<LoadOptionsDto>> loadOptions(){
	 		 	
	 
	 	List<LoadOptionsDto> unitList=unitService.loadOptions();
       
        return new ResponseEntity<>(unitList,HttpStatus.OK);
    } 
}
