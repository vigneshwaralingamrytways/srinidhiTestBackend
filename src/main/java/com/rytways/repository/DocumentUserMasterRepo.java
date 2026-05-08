package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.DocumentUserMaster;


@Repository
public interface DocumentUserMasterRepo extends JpaRepository<DocumentUserMaster, Long>{

	List<DocumentUserMaster> findByDocumentTypeIdOrderByUpdatedOn(Long documentTypeId);

	List<DocumentUserMaster> findByUserIdOrderByUpdatedOn(Integer userId);

	List<DocumentUserMaster> findByDocumentTypeIdAndUserIdOrderByUpdatedOn(Long documentTypeId, Integer userId);

	Optional<DocumentUserMaster> findByUserIdAndDocumentTypeId(Integer userId, Long documentTypeId);

}
