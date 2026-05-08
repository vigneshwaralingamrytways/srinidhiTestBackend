package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rytways.model.DocumentTransaction;

@Repository
public interface DocumentTransactionRepo extends JpaRepository<DocumentTransaction, Long> , JpaSpecificationExecutor<DocumentTransaction> {

}
