package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.Comments;
import com.rytways.service.CommentsService;

@RestController
@RequestMapping("/comments")

public class CommentsController {
	@Autowired
	CommentsService commentService;

	@GetMapping("/getComments/{docsId}")
	public List<Comments> getComments(@PathVariable int docsId) {
		return commentService.getByDocId(docsId);
	}

	@PostMapping("/saveComments")
	public Comments saveComments(@RequestBody Comments cmnt) {
		return commentService.create(cmnt);
	}

}
