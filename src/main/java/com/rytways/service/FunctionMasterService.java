package com.rytways.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.ActivityMaster;
import com.rytways.model.FunctionMaster;
import com.rytways.model.ProcessMaster;
import com.rytways.repository.ActivityMasterRepo;
import com.rytways.repository.FunctionMasterRepo;

@Component
@Service
@Transactional
public class FunctionMasterService {

	
	@Autowired
	private FunctionMasterRepo functionRepo;
	
	@Autowired
	private ActivityMasterRepo activityMasterRepo;

	public FunctionMaster createFunction(FunctionMaster doc) {
		// TODO Auto-generated method stub
		return functionRepo.save(doc);
	}

	public List<FunctionMaster> getFunctionMaster(Long long1) {
		// TODO Auto-generated method stub
		return functionRepo.findByProcessId(long1);
	}

	public FunctionMaster editFunction(FunctionMaster doc) {
		
		doc.setProcess(null);
		
		doc=functionRepo.save(doc);
		return doc;
	}

	public void deleteFunction(long functionId) throws Exception {
		

		boolean isUsed = activityMasterRepo.existsByFunctionId(functionId);
		if (isUsed) {
		    throw new Exception("Cannot delete Function: it is in use by Acivity Master.");
		}
        // Check if the record exists
		FunctionMaster process = functionRepo.findById(functionId)
                .orElseThrow(() -> new Exception("Function with ID " + functionId + " not found."));
        
        
        // Delete the DocumentUserMaster record
	 functionRepo.deleteById(functionId);
		
	}
	
	public List<LoadOptionsDto> loadType(FunctionMaster request) {
		 
		List<FunctionMaster> typeList = functionRepo.findByProcessId(request.getProcessId());
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!typeList.isEmpty()) {
			   for (FunctionMaster typeMaster : typeList) {
				   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

		            loadDto.setLabel(typeMaster.getFunctionName());
		            loadDto.setValue(typeMaster.getFunctionId());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}
	public List<LoadOptionsDto> loadTypes(FunctionMaster request) {
		 
		List<FunctionMaster> typeList = functionRepo.findAll();
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!typeList.isEmpty()) {
			   for (FunctionMaster typeMaster : typeList) {
				   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

		            loadDto.setLabel(typeMaster.getFunctionName());
		            loadDto.setValue(typeMaster.getFunctionId());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}

	public FunctionMaster findFunctionMasterById(long functionId) {
		
		return functionRepo.findById(functionId).orElse(null);
	}
	
}
