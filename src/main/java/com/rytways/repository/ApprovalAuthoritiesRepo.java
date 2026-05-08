package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rytways.model.ApprovalAuthorities;

@Repository
public interface ApprovalAuthoritiesRepo extends JpaRepository<ApprovalAuthorities,Integer>,JpaSpecificationExecutor<ApprovalAuthorities> {

	@Query(value = "SELECT * FROM approval_authorities " +
            "WHERE approval_process_id = (" +
            "   SELECT approval_process_id FROM approval_process " +
            "   WHERE process_name = :processName" +
            ") AND approval_name = :approvalName", nativeQuery = true)
ApprovalAuthorities findByProcessNameAndApprovalName(@Param("processName") String processName,
                                                  @Param("approvalName") String approvalName);


	
	
}
