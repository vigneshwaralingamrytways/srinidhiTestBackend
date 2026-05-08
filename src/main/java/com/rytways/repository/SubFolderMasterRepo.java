package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.SubFolderMaster;

@Repository
public interface SubFolderMasterRepo extends JpaRepository<SubFolderMaster, Long>{

	List<SubFolderMaster> findByFolderId(Long folderId);

	List<SubFolderMaster> findByDocumentTypeIdAndFolderIdOrderByUpdatedOn(Long documentTypeId, Long folderId);

}
