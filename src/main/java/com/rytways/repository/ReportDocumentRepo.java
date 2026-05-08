package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.rytways.model.ReportDocument;

@Repository
public interface ReportDocumentRepo extends JpaRepository <ReportDocument, Long>{

	Optional<List<ReportDocument>> findByTransactionIdOrderByUpdatedOn(long transactionId);

	List<ReportDocument> findByTransactionId(Long transactionId);

}
