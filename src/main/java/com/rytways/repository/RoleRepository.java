package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rytways.model.Roles;


public interface RoleRepository extends JpaRepository<Roles, Integer>, JpaSpecificationExecutor<Roles> {
	
	/*@Query("select new com.rytways.dto.LoadOptionsDto (mm.roleId as value, mm.roleName as label) from Roles mm"
			)
	//@Query("select cm.customer_id as value, cm.name as label from customer_master cm")
	List<LoadOptionsDto> findLoadOptions();*/
	
}
