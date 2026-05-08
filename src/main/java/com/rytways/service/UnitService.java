package com.rytways.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.dto.LoadOptionsStringDto;
 
import com.rytways.model.UnitMaster;
import com.rytways.repository.UnitRepository;

@Service
public class UnitService {

	@Autowired
	UnitRepository unitRepository;
	
	public List<LoadOptionsDto> loadOptions() {
		
		List<LoadOptionsDto> lodedOptions = unitRepository.findLoadOptions();
		return lodedOptions;
	}
	public List<LoadOptionsStringDto> loadUnitOptions() {
		List<UnitMaster> unitList = unitRepository.findAll();
		List<LoadOptionsStringDto> dtoList = new ArrayList<>();
		 if (!unitList.isEmpty()) {
			   for (UnitMaster unitMaster : unitList) {
				   LoadOptionsStringDto loadDto = new LoadOptionsStringDto(); // Move inside the loop

		            loadDto.setLabel(unitMaster.getUnitName());
		            loadDto.setValue(unitMaster.getUnitName());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}
	
	public Optional<UnitMaster> getUnitByName(String unitName) {
        return unitRepository.findByUnitName(unitName);
    }
	
	public UnitMaster saveUnitDetails(UnitMaster unit) {
		
		unit=unitRepository.save(unit);
        return unit;
	}
	
}
