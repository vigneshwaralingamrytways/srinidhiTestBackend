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
import com.rytways.dto.LoadOptionsDto;
import com.rytways.dto.LoadOptionsStringDto;
import com.rytways.service.CategoriesService;
import com.rytways.service.UnitService;

@RestController
@RequestMapping("/loadCategories")
public class CategoriesController {

	@Autowired
	CategoriesService catService;
	

	@PostMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadUnitsOptions(@RequestBody IdDto idDto){
	 		 	
	 
	 	List<LoadOptionsDto> loadedOptions=catService.loadOptions(idDto.getCatName());
       
        return new ResponseEntity<>(loadedOptions,HttpStatus.OK);
    } 
}
