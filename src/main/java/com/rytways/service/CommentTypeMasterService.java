package com.rytways.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsStringDto;
import com.rytways.model.CommentTypeMaster;
import com.rytways.repository.CommentTypeMasterRepo;

@Component
@Service
@Transactional
public class CommentTypeMasterService {
	
	@Autowired
	private CommentTypeMasterRepo commentTypeMasterRepo;

	public List<LoadOptionsStringDto> loadType(CommentTypeMaster data) {
		
	    // Fetch distinct vehicle names by clientName
	    List<CommentTypeMaster> list = commentTypeMasterRepo.findAll();
	    List<LoadOptionsStringDto> dtoList = new ArrayList<>();

	    // Create DTOs from EmissionData
	    for (CommentTypeMaster data1 : list) {
	        LoadOptionsStringDto loadDto = new LoadOptionsStringDto();
	        loadDto.setLabel(data1.getTypeName()); // Assuming you want to set vehicleName as the label
	        loadDto.setValue(data1.getTypeName()); // Assuming you want to set vehicleName as the value
	        dtoList.add(loadDto);
	    }

	    return dtoList;
	}

}
