package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.FormConfigMasterForTaskType;

@Repository
public interface FormConfigRepoForTaskType extends JpaRepository<FormConfigMasterForTaskType, Integer>{

	List<FormConfigMasterForTaskType> findByTaskTypeId(int taskTypeId);
}
