package com.rytways.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rytways.dto.AppHistDto;
import com.rytways.model.ApprovalsHistory;

@Repository
public interface ApprovalsHistoryRepo extends JpaRepository<ApprovalsHistory,Integer>,JpaSpecificationExecutor<ApprovalsHistory> {
	
		List<ApprovalsHistory> findByAppTypeAndIdOrderByApprovalHistoryId(String appType,Long id);
	
	
	 	@Modifying
	    @Query("UPDATE ApprovalsHistory ah SET ah.showApproval = 0 WHERE ah.appType= :appType and "
	    		+ "ah.id = :id and ah.isApproved=0")
	    void updateShowApproval(@Param("appType") String appType, @Param("id") Long id);
	 	
	 	List<ApprovalsHistory> findByIsApprovedAndAppTypeAndId(int isApproved,String appType,Long id);
	 	
	 	@Modifying
	    @Query("UPDATE ApprovalsHistory ah SET ah.isApproved = 0 WHERE ah.appType= :appType and "
	    		+ "ah.id = :id and ah.isApproved=1 and ah.authorityId>0")
	    void updateApprovalStatus(@Param("appType") String appType, @Param("id") Long id);
	 	
	 	List<ApprovalsHistory> findByAppTypeInAndIdInOrderByApprovalHistoryId(List<String> appType,List<Long> ids);


//	 	@Query("SELECT new com.rytways.dto.AppHistDto(a, u) "
//	 			+ " FROM ApprovalsHistory a LEFT JOIN Users u on  a.approvedBy = u.UserId where a.appType in ?1 and a.id in ?2")
//		List<AppHistDto> findByApprovalHistory(List<String> processName,List<Long> ids);
//	 	
	 	
	 	@Query("SELECT new com.rytways.dto.AppHistDto(a, u) "
	 		     + "FROM ApprovalsHistory a JOIN Users u ON a.approvedBy = u.UserId "
	 		     + "WHERE a.appType IN :processName AND a.id IN :ids")
	 		List<AppHistDto> findByApprovalHistory(@Param("processName") List<String> processName,
	 		                                       @Param("ids") List<Long> ids);


//		@Modifying
//@Transactional
//@Query("DELETE FROM ApprovalsHistory a WHERE a.id = :id")
//void deleteByIdValue(@Param("id") Long id);
//
	 	@Modifying
	 	@Transactional
	 	@Query("DELETE FROM ApprovalsHistory a WHERE a.id = :id AND a.appType = :appType")
	 	void deleteByIdAndAppType(@Param("id") Long id, @Param("appType") String appType);


		List<ApprovalsHistory> findByAppTypeAndId(String string, Long requestDiscountId);

	 	
}
