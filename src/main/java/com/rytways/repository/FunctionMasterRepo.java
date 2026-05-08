package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rytways.model.ActivityMaster;
import com.rytways.model.FunctionMaster;

@Repository
public interface FunctionMasterRepo
		extends JpaRepository<FunctionMaster, Long>, JpaSpecificationExecutor<FunctionMaster> {

	List<FunctionMaster> findByFunctionIdIn(List<Long> functionId);

	@Query(value = "SELECT DISTINCT f.* FROM function_master f "
			+ "JOIN activity_master a ON a.function_id = f.function_id "
			+ "JOIN activity_master_features af ON af.activity_id = a.activity_id "
			+ "WHERE af.role_id = :roleId AND a.activity_status >= 100", nativeQuery = true)
	List<FunctionMaster> findFunctionsByRoleId(@Param("roleId") Integer roleId);

	List<FunctionMaster> findByProcessId(Long long1);

	/*
	 * @Query(value = "SELECT p.process_id, p.process_name, p.process_path, " +
	 * "f.function_id, f.function_name, f.function_path " + "FROM process_master p "
	 * + "LEFT JOIN function_master f ON f.process_id = p.process_id " +
	 * "LEFT JOIN activity_master a ON a.function_id = f.function_id " +
	 * "LEFT JOIN activity_master_features af ON af.activity_id = a.activity_id " +
	 * "WHERE af.role_id = :roleId OR af.role_id IS NULL", nativeQuery = true)
	 * List<Object[]> findAllProcessesWithFunctions(@Param("roleId") Integer
	 * roleId);
	 * 
	 */

	@Query(value = "SELECT p.process_id, p.process_name, p.process_path, "
			+ "f.function_id, f.function_name, f.function_path,p.description " + "FROM process_master p "
			+ "LEFT JOIN function_master f ON f.process_id = p.process_id "
			+ "ORDER BY p.process_id, f.function_id", nativeQuery = true)
	List<Object[]> findAllProcessesWithFunctions();

	boolean existsByProcessId(long processId);
	
	@Query(value = "SELECT DISTINCT f.* FROM function_master f "
	        + "JOIN activity_master a ON a.function_id = f.function_id "
	        + "LEFT JOIN activity_master_features af ON af.activity_id = a.activity_id "
	        + "WHERE  ( (:liveStatus < 100) OR (a.activity_status >= :liveStatus) ) "
	        + "AND ( "
	        + "  (a.user_type = 'ROLE_WISE' AND af.role_id = :roleId) "
	        + "  OR "
	        + "  (a.user_type = 'USER_WISE' AND af.user_id = :userId) "
	        + ")", nativeQuery = true)
	List<FunctionMaster> findFunctionsByRoleOrUser(
	    @Param("roleId") Integer roleId,
	    @Param("userId") Integer userId,
	    @Param("liveStatus") Long liveStatus
	);

	List<FunctionMaster> findByProcessIdIn(List<Long> uniqueProcessIds);

}
