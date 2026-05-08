package com.rytways.service;





import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionIntegerDto;
import com.rytways.model.DepartmentMaster;
import com.rytways.repository.DepartRepository;
import com.rytways.specifications.DepartmentSpec;

@Component
@Service
@Transactional
public class DepartmentService {

	
	@Autowired
	private DepartRepository departRepo;
	
	public DepartmentMaster saveDepartment(DepartmentMaster department) {
		String isSaved = "";
		department=departRepo.save(department);
        return department;
	}
	
	public List<LoadOptionIntegerDto> loadDepartmentOptions() {

	    List<DepartmentMaster> departmentsList = departRepo.findAll();
	    List<LoadOptionIntegerDto> dtoList = new ArrayList<>();

	    if (!departmentsList.isEmpty()) {
	        for (DepartmentMaster deptMas : departmentsList) {
	            LoadOptionIntegerDto loadDto = new LoadOptionIntegerDto(); // Move inside the loop

	            loadDto.setLabel(deptMas.getDepartmentName());
	            loadDto.setValue(deptMas.getDepartmentId());

	            dtoList.add(loadDto); // Add each instance to the list
	        }
	    }

	    return dtoList; // Return the list after the loop
	}

	public List<DepartmentMaster> getDepartmentDetails(DepartmentMaster department) {
		


		Specification spec1 = DepartmentSpec.departmentNameLike(department.getDepartmentName()); 
	    Specification spec2 = DepartmentSpec.departmentHeadLike(department.getDepartmentHead()); 
	    Specification spec3 = DepartmentSpec.assDepartmentHeadLike(department.getAssDepartmentHead()); 
		
	    
	    Specification<DepartmentMaster> spec = Specification.where(spec1).and(spec2).and(spec3);
	    // Use Sort to specify sorting order
	    Sort sort = Sort.by(Sort.Direction.ASC, "departmentId");

		return departRepo.findAll(spec, sort);
		
		
		
	}


}
