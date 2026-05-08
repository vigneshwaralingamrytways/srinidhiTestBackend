package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.CommentTypeMaster;

@Repository
public interface CommentTypeMasterRepo extends JpaRepository<CommentTypeMaster,Long>,JpaSpecificationExecutor<CommentTypeMaster> {


}
