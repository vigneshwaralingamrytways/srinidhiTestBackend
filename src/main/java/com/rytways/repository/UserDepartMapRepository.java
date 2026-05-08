package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.UserDeptMap;


@Repository
public interface UserDepartMapRepository extends JpaRepository<UserDeptMap,Integer> , JpaSpecificationExecutor<UserDeptMap> {

	List<UserDeptMap> findByUserId(Integer userId);

}
