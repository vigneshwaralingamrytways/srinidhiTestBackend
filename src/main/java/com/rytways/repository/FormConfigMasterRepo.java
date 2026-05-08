package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.FormConfigMaster;

@Repository
public interface FormConfigMasterRepo extends JpaRepository<FormConfigMaster, Long>{

	List<FormConfigMaster> findByDocumentTypeIdOrderByUpdatedOn(Long documentTypeId);

	Optional<FormConfigMaster> findByDocumentTypeId(Long documentTypeId);

}
