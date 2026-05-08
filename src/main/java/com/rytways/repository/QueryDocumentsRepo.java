package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.QueryDocuments;

@Repository
public interface QueryDocumentsRepo extends JpaRepository<QueryDocuments,Long>,JpaSpecificationExecutor<QueryDocuments> {

	Optional<List<QueryDocuments>> findByReportTypeAndQueryIdOrderByUpdatedOn(String reportType, Long queryId);

	

}
