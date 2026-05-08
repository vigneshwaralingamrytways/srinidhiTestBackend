package com.rytways.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.IssueSolution;
import com.rytways.model.IssueSolutionComments;
import com.rytways.repository.IssueSolutionCommentsRepo;
import com.rytways.repository.IssueSolutionRepo;
import com.rytways.security.SecurityUtils;

@Component
@Service
@Transactional
public class IssueSolutionService {

	@Autowired
	private IssueSolutionRepo issueSolutionRepo;
	
	@Autowired
	private IssueSolutionCommentsRepo issueSolutionCommmentsRepo;
	

	public IssueSolution createIssueSolution(IssueSolution doc) {
		// TODO Auto-generated method stub
		return issueSolutionRepo.save(doc);
	}

	public IssueSolution loadDetails(IssueSolution doc) {
		// TODO Auto-generated method stub
		return issueSolutionRepo.findByIssueTypeAndFunctionActivityId(doc.getIssueType(),doc.getFunctionActivityId());
	}

	public IssueSolutionComments createIssueSolutionComments(IssueSolutionComments doc) {
		// TODO Auto-generated method stub
		Optional<IssueSolutionComments> issueSol = issueSolutionCommmentsRepo.findById(doc.getCommentId());
		if(!issueSol.isPresent()) {
			String userName = SecurityUtils.getCurrentUser().getUsername();
			doc.setUpdateBy(userName);
		}
		return issueSolutionCommmentsRepo.save(doc);
	}


	public List<IssueSolutionComments> getAllIssueSolutionComments(IssueSolutionComments com) {
		 List<IssueSolutionComments> list = issueSolutionCommmentsRepo.findByIssueId(com.getIssueId());
		return list;
	}

//	public IssueSolution loadDetailsWithComments(IssueSolution doc) {
//    IssueSolution issue = issueSolutionRepo.findByIssueTypeAndFunctionActivityId(
//        doc.getIssueType(), doc.getFunctionActivityId()
//    );
//
//    if (issue != null) {
//        List<IssueSolutionComments> comments = issueSolutionCommmentsRepo.findByIssueId(issue.getIssueId());
//        issue.setCommentList(comments);
//    }
//
//    return issue;
//}
	
	public IssueSolution loadDetailsWithComments(IssueSolution doc) {
	    IssueSolution issue = issueSolutionRepo.findByIssueTypeAndFunctionActivityId(
	        doc.getIssueType(), doc.getFunctionActivityId()
	    );

	    if (issue != null) {
	        List<IssueSolutionComments> comments;

	        boolean hasCommentType = doc.getCommentType() != null && !doc.getCommentType().trim().isEmpty();
	        boolean hasCommentTypeNotIn = doc.getCommentTypeNotIn() != null && !doc.getCommentTypeNotIn().isEmpty();

	        if (hasCommentType) {
	            // Case 1: Specific commentType is provided
	            comments = issueSolutionCommmentsRepo.findByIssueIdAndCommentType(
	                issue.getIssueId(),
	                doc.getCommentType()
	            );

	        } else if (hasCommentTypeNotIn) {
	            // Case 2: Exclude certain comment types
	            comments = issueSolutionCommmentsRepo.findByIssueIdAndCommentTypeNotIn(
	                issue.getIssueId(),
	                doc.getCommentTypeNotIn()
	            );

	        } else {
	            // Case 3: No filter (get all comments)
	            comments = issueSolutionCommmentsRepo.findByIssueId(issue.getIssueId());
	        }

	        issue.setCommentList(comments);
	    }

	    return issue;
	}


	public IssueSolution loadDetailsWithActComments(IssueSolution doc) {

	    // Null or empty checks for all 4 fields
	    if (doc.getIssueType() != null && !doc.getIssueType().trim().isEmpty() &&
	        doc.getFunctionActivityId() != null &&
	        doc.getReleaseTrackId() != null ) {  // Replace with your actual 4th field

	        IssueSolution issue = issueSolutionRepo.findByIssueTypeAndFunctionActivityIdAndReleaseTrackId(
	            doc.getIssueType(), doc.getFunctionActivityId(), doc.getReleaseTrackId()
	        );

	        if (issue != null) {
	            List<IssueSolutionComments> comments = issueSolutionCommmentsRepo.findByIssueId(issue.getIssueId());
	            issue.setCommentList(comments);
	        }

	        return issue;
	    }

	    // Return null or empty object if not all 4 params are present
	    return null;
	}


	
}
