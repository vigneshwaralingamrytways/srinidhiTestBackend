package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.GateMaster;
 
import com.rytways.model.UnitMaster;

public interface GateMasterRepo extends JpaRepository<GateMaster, Long>, JpaSpecificationExecutor<GateMaster>{

}
