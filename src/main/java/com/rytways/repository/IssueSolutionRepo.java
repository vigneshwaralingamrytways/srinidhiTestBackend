package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.IssueSolution;

@Repository
public interface IssueSolutionRepo extends JpaRepository <IssueSolution, Long > , JpaSpecificationExecutor<IssueSolution> {

	IssueSolution findByIssueTypeAndFunctionActivityId(String issueType, Long functionActivityId);

	IssueSolution findByIssueTypeAndFunctionActivityIdAndReleaseTrackId(String issueType, Long functionActivityId,
			Long releaseTrackId);

	


}
