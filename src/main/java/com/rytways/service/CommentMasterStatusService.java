package com.rytways.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsStringDto;
import com.rytways.model.CommentMasterStatus;
import com.rytways.repository.CommentMasterStatusRepo;


@Component
@Service
@Transactional
public class CommentMasterStatusService {
	
	@Autowired
	private CommentMasterStatusRepo commentMasterStatusRepo;
	
	
public List<LoadOptionsStringDto> loadType(CommentMasterStatus data) {
		
	    // Fetch distinct vehicle names by clientName
	    List<CommentMasterStatus> list = commentMasterStatusRepo.findAll();
	    List<LoadOptionsStringDto> dtoList = new ArrayList<>();

	    // Create DTOs from EmissionData
	    for (CommentMasterStatus data1 : list) {
	        LoadOptionsStringDto loadDto = new LoadOptionsStringDto();
	        loadDto.setLabel(data1.getStatusName()); // Assuming you want to set vehicleName as the label
	        loadDto.setValue(data1.getStatusName()); // Assuming you want to set vehicleName as the value
	        dtoList.add(loadDto);
	    }

	    return dtoList;
	}

}
