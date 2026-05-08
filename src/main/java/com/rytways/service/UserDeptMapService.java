package com.rytways.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.DepartmentMaster;
import com.rytways.model.UserDeptMap;
import com.rytways.repository.DepartRepository;
import com.rytways.repository.UserDepartMapRepository;

@Component
@Service
@Transactional
public class UserDeptMapService {

	
	@Autowired
	UserDepartMapRepository userDeptMapRepository;
	 
	@Autowired
	private DepartRepository departRepo;
	
	public List<UserDeptMap> createUserDeptMap(UserDeptMap request) {
        List<UserDeptMap> userDeptMaps = new ArrayList<>();

        // Loop through the department IDs and create individual mappings
        for (Integer depId : request.getDepartmentIds()) {
            UserDeptMap depMap = new UserDeptMap();
            depMap.setUserId(request.getUserId());
            depMap.setDepartmentId(depId);

            // Save the mapping to the database
            userDeptMapRepository.save(depMap);
            // Retrieve the department from the repository
            Optional<DepartmentMaster> departOptional = departRepo.findById(depId);
            if (departOptional.isPresent()) {
                depMap.setDepartment(departOptional.get());
            } else {
                throw new RuntimeException("Department not found with ID: " + depId);
            }

            // Add the saved entry to the list to return
            userDeptMaps.add(depMap);
        }

        return userDeptMaps;
    }



	public void deleteUserDep(Integer userDeptMapId) throws Exception{	
		   if (userDeptMapId != null) {
		        // Check if the record exists
			   UserDeptMap userDeptMap = userDeptMapRepository.findById(userDeptMapId)
		                .orElseThrow(() -> new Exception("userDeptMapId with ID " + userDeptMapId + " not found."));
		        
		        // Ensure no dependent records exist (if necessary)
		       

		        // Delete the DocumentUserMaster record
		    	userDeptMapRepository.deleteById(userDeptMapId);
		    } else {
		        throw new Exception("userDeptMapId cannot be null.");
		    }
		   
	}
	
	
	

	
}
