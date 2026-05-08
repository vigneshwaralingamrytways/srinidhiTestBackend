package com.rytways.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.ModuleFeatures;







@Repository
public interface ModuleFeaturesRepository extends JpaRepository<ModuleFeatures,Integer> {
	
	Optional<List<ModuleFeatures>> findByRoleIdOrderByModuleId(int roleId);
}