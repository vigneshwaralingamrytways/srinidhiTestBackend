package com.rytways.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.ModuleFeatures;
import com.rytways.repository.ModuleFeaturesRepository;






@RestController
@RequestMapping("/loadMenu")
public class LoadMenuController {

@Autowired
private ModuleFeaturesRepository moduleFeatsRepo;

	 
	 @PostMapping("/loadModules")
	    public ResponseEntity <List<ModuleFeatures>> loadModules(@RequestBody ModuleFeatures modulesFeats){
		 	
		    List<ModuleFeatures> modulesList = null;
		 	//Page<MaterialMaster> custPage  = custService.findMaterials(pageNum);
		 	 
		 	// List<MaterialMaster> Materials = custPage.getContent();
		 
		 	Optional <List<ModuleFeatures>> modules=moduleFeatsRepo.findByRoleIdOrderByModuleId(modulesFeats.getRoleId());
		 	
		 	if(modules.isPresent()){
		 		modulesList = modules.get();
		 	}
		 	
		 	return new ResponseEntity<>(modulesList,HttpStatus.OK);       
	        
	    }	
}
