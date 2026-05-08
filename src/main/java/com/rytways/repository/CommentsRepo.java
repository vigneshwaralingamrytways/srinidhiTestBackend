package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.Comments;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Integer> {

	List<Comments> findByDocsId(int docsId);
}
