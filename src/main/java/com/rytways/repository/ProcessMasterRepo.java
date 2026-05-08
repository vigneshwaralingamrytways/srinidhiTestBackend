package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.ProcessMaster;

@Repository
public interface ProcessMasterRepo extends JpaRepository<ProcessMaster, Long>, JpaSpecificationExecutor<ProcessMaster> {

}

