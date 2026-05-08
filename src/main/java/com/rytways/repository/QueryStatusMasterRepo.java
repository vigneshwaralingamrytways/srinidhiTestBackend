package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.QueryStatusMaster;

@Repository
public interface QueryStatusMasterRepo extends JpaRepository<QueryStatusMaster, Long>, JpaSpecificationExecutor<QueryStatusMaster> {

	List<QueryStatusMaster> findByStatusType(String statusType);

}