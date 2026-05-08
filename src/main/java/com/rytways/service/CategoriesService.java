package com.rytways.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rytways.Categories.ActiveStatus;
import com.rytways.dto.LoadOptionsDto;
import com.rytways.repository.CategoriesRepo;

@Service
public class CategoriesService {

	@Autowired
	CategoriesRepo catRepo;
	
	
	public List<LoadOptionsDto> loadOptions(String catType) {
		List<LoadOptionsDto>  unitList = catRepo.findLoadOptions(catType,ActiveStatus.Yes);
		
		return unitList;
	}
	
	
	
}
