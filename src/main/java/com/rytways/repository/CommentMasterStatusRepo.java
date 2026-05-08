package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.dto.LoadOptionsStringDto;
import com.rytways.model.CommentMasterStatus;

@Repository
public interface CommentMasterStatusRepo extends JpaRepository<CommentMasterStatus,Long>,JpaSpecificationExecutor<CommentMasterStatus> {

	


}

