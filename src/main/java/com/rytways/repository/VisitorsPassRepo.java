package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.VisitorsPass;
import com.rytways.model.UnitMaster;

public interface VisitorsPassRepo extends JpaRepository<VisitorsPass, Long>,JpaSpecificationExecutor<VisitorsPass>{

	Optional<VisitorsPass> findByPassNo(String passNo);
	
	

}
