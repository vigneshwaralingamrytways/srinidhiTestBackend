package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.ActivityMaster;
import com.rytways.model.ActivityMasterFeatures;

@Repository
public interface ActivityMasterFeaturesRepo extends JpaRepository<ActivityMasterFeatures, Long>, JpaSpecificationExecutor<ActivityMasterFeatures> {

	List<ActivityMasterFeatures> findByRoleIdOrderByActivityId(Integer roleId);

	boolean existsByActivityIdAndRoleId(long activityId, Integer roleId);

	boolean existsByActivityIdAndUserId(long activityId, Integer userId);

	List<ActivityMasterFeatures> findByActivityId(long activityId);

	boolean existsByActivityId(long activityId);

}
