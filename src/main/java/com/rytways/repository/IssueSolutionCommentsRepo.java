package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.IssueSolutionComments;

@Repository
public interface IssueSolutionCommentsRepo extends JpaRepository <IssueSolutionComments, Long > , JpaSpecificationExecutor<IssueSolutionComments> {

	List<IssueSolutionComments> findByIssueId(Long issueId);

	List<IssueSolutionComments> findByIssueIdAndCommentType(Long issueId, String commentType);

    List<IssueSolutionComments> findByIssueIdAndCommentTypeNotIn(Long issueId, List<String> commentTypeNotIn);

}
