package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.ActivityMaster;
import com.rytways.model.ActivityMasterFeatures;
import com.rytways.model.FunctionMaster;

@Repository
public interface ActivityMasterRepo extends JpaRepository<ActivityMaster, Long>, JpaSpecificationExecutor<ActivityMaster> {

	List<ActivityMaster> findByActivityIdIn(List<Long> menuIds);

	List<ActivityMaster> findByFunctionId(long functionId);

	boolean existsByFunctionId(long functionId);

	List<ActivityMaster> findByFunctionIdOrderByDisplaySeqNoAsc(long functionId);

}