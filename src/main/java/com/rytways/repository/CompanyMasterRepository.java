package com.rytways.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rytways.dto.LoadOptionIntegerDto;
import com.rytways.model.CompanyMaster;

public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, Integer> {
	Page<CompanyMaster> findAll( Pageable pageable);
	
	@Query("select new com.rytways.dto.LoadOptionIntegerDto (mm.companyId as value, mm.companyName as label) from CompanyMaster mm"
			)
	//@Query("select cm.customer_id as value, cm.name as label from customer_master cm")
	List<LoadOptionIntegerDto> findLoadOptions();
}
