package com.rytways.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.ActivityMaster;
import com.rytways.model.ProcessMaster;
import com.rytways.repository.FunctionMasterRepo;
import com.rytways.repository.ProcessMasterRepo;

@Component
@Service
@Transactional
public class ProcessMasterService {

	
	@Autowired
	private ProcessMasterRepo processRepo;
	
	@Autowired
	private FunctionMasterRepo functionRepo;
	

	public ProcessMaster createProcess(ProcessMaster doc) {
		
		return processRepo.save(doc);
	}

	public List<ProcessMaster> getProcessMaster() {
		// TODO Auto-generated method stub
		return processRepo.findAll();
	}
	
	public void deleteProcess(long processId) throws Exception {
		
		boolean isUsed = functionRepo.existsByProcessId(processId);
		if (isUsed) {
		    throw new Exception("Cannot delete process: it is in use by FunctionMaster.");
		}
        // Check if the record exists
	 ProcessMaster process = processRepo.findById(processId)
                .orElseThrow(() -> new Exception("Process with ID " + processId + " not found."));
        
        
        // Delete the DocumentUserMaster record
	 processRepo.deleteById(processId);
    } 
	
	
	public List<LoadOptionsDto> loadType() {
		 
		List<ProcessMaster> typeList = processRepo.findAll();
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!typeList.isEmpty()) {
			   for (ProcessMaster typeMaster : typeList) {
				   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

		            loadDto.setLabel(typeMaster.getProcessName());
		            loadDto.setValue(typeMaster.getProcessId());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}

	public ProcessMaster findProcessById(long id) {
    return processRepo.findById(id).orElse(null);
}

	
}
