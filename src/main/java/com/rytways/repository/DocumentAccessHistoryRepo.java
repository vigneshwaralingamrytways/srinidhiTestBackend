package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.DocumentAccessHistory;

@Repository
public interface DocumentAccessHistoryRepo extends JpaRepository<DocumentAccessHistory, Long> {

	List<DocumentAccessHistory> findByReportDocId(Long reportDocId);

	Optional<DocumentAccessHistory> findByUserIdAndReportDocId(int userId, Long reportDocId);

	List<DocumentAccessHistory> findByTransactionId(Long transactionId);

	void deleteByReportDocId(Long reportDocId);

}
