package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.DocumentTypeMaster;

@Repository
public interface DocumentTypeRepo extends JpaRepository<DocumentTypeMaster, Long>,JpaSpecificationExecutor<DocumentTypeMaster>{

}
