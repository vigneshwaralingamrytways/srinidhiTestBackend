package com.rytways.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rytways.model.Comments;
import com.rytways.repository.CommentsRepo;

@Service
public class CommentsService {

	@Autowired
	CommentsRepo commentsRepo;

	public Comments create(Comments comment) {
		return commentsRepo.save(comment);
	}

	public Comments getByCommentId(int commentId) {
		return commentsRepo.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
	}

	public List<Comments> getByDocId(int docsID) {
		return commentsRepo.findByDocsId(docsID);
	}
}
