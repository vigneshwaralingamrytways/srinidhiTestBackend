package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.UnitMaster;

public interface UnitRepository extends JpaRepository<UnitMaster, Long>{
	
	 @Query(value = "select * from unit_master where unit_name = :unitName", nativeQuery = true)
	 Optional<UnitMaster> findByUnitName(@Param("unitName")String unitName);
	 
	 @Query("select new com.rytways.dto.LoadOptionsDto (cm.id as value, cm.unitName as label) from UnitMaster cm")
		//@Query("select cm.customer_id as value, cm.name as label from customer_master cm")
	List<LoadOptionsDto> findLoadOptions();

}
