package com.rytways.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.ApprovalProcess;

@Repository
public interface ApprovalProcessRepo extends JpaRepository<ApprovalProcess,Long>,JpaSpecificationExecutor<ApprovalProcess> {
	
	// @Query("SELECT e FROM YourEntity e WHERE TRIM(e.processName) = TRIM(:processName)")
	 //Optional<ApprovalProcess> findByProcessNameAfterTrim(@Param("processName") String processName);
	
	
	Optional<ApprovalProcess> findByProcessName(String processName);
}
