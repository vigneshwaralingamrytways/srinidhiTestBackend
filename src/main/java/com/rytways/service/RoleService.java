package com.rytways.service;





import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.Roles;
import com.rytways.model.Users;
import com.rytways.repository.RoleRepository;
import com.rytways.specifications.RoleSpec;

@Component
@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public Roles saveRole (Roles roles) {
		String isSaved = "";
		roles=roleRepository.save(roles);
        return roles;
	}
	
	
	public List<LoadOptionsDto> loadRoleOptions() {

	    List<Roles> rolesList = roleRepository.findAll();
	    
	    List<LoadOptionsDto> dtoList = new ArrayList<>();

	    if (!rolesList.isEmpty()) {
	        for (Roles role : rolesList) {
	            LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

	            loadDto.setLabel(role.getRoleName());
	            loadDto.setValue(role.getRoleId());

	            dtoList.add(loadDto); // Add each instance to the list
	        }
	    }

	    return dtoList; // Return the list after the loop
	}


	public List<Roles> getRoleDetails(Roles role) {
		

		Specification spec1 = RoleSpec.roleNameLike(role.getRoleName()); 
	   
		
	    
	    Specification<Roles> spec = Specification.where(spec1);
	    // Use Sort to specify sorting order
	    Sort sort = Sort.by(Sort.Direction.ASC, "roleId");

		return roleRepository.findAll(spec, sort);
		
	}

	
}
