package com.rytways.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rytways.dto.LoadOptionIntegerDto;
import com.rytways.model.DepartmentMaster;






@Repository
public interface DepartRepository extends JpaRepository <DepartmentMaster, Integer > , JpaSpecificationExecutor<DepartmentMaster> {

	@Query("select new com.rytways.dto.LoadOptionIntegerDto (cm.departmentId as value, cm.departmentName as label) from DepartmentMaster cm where departmentId in :department")
	List<LoadOptionIntegerDto> findLoadOptions(@Param("department")ArrayList<Integer> department);


	
	/*@Query("select new com.ssbpl.dto.LoadOptionIntegerDto (mm.departId as value, mm.departmentName as label) from DepartMaster mm"
			)
	
	List<LoadOptionIntegerDto> findLoadOptions();*/

}