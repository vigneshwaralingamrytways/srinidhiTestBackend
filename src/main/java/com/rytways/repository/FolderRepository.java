package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.FolderMaster;

@Repository
public interface FolderRepository extends JpaRepository<FolderMaster, Long>{



	List<FolderMaster> findByDocumentTypeIdOrderByUpdatedOn(Long documentTypeId);

	// Optional<List<FolderMaster>> findByDocumentTypeIdOrderByUpdatedOn(Long documentTypeId);
	
	 // New method to find by a list of documentTypeIds
  //  List<FolderMaster> findByDocumentTypeIdIn(List<Long> documentTypeIds);


}
