package com.rytways.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.DocumentAccessHistory;
import com.rytways.repository.DocumentAccessHistoryRepo;

@Service
public class DocumentAccessHistoryService {

	@Autowired
	private DocumentAccessHistoryRepo documentAccessHistoryRepo;

	public List<DocumentAccessHistory> getAllDocumentAccess(Long reportDocId) {
		return documentAccessHistoryRepo.findByReportDocId(reportDocId);
	}

	@Transactional
	public void deleteHistory(DocumentAccessHistory history) {
		Optional<DocumentAccessHistory> optionalHistory = documentAccessHistoryRepo.findById(history.getDocumentAccessHitoryId());
	    if (optionalHistory.isPresent()) {
	    	optionalHistory.get().setReportDocument(null);
	    	optionalHistory.get().setReportDocId(null);
	    	optionalHistory.get().setDocumentTransaction(null);
	    	optionalHistory.get().setUsers(null);
	    	documentAccessHistoryRepo.save(optionalHistory.get());
	        documentAccessHistoryRepo.deleteById(optionalHistory.get().getDocumentAccessHitoryId());
	    }
	}
}
