package com.rytways.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rytways.model.DownloadHistory;
import com.rytways.repository.DownloadHistoryRepo;

@Service
public class DownloadHistoryService {
	@Autowired
	private DownloadHistoryRepo downloadHistoryRepo;

	public List<DownloadHistory> getAll(Long reportDocId, int userId) {
		List<DownloadHistory> downHist = downloadHistoryRepo.findByReportDocIdAndUserId(reportDocId, userId);
		return downHist;
	}

	public DownloadHistory saveDownloadHostory(Long reportDocId, int userId, LocalDateTime donwloadTime) {

		DownloadHistory downloadHistory = new DownloadHistory();
		downloadHistory.setDownloadHistoryTime(donwloadTime);
		downloadHistory.setReportDocId(reportDocId);
		downloadHistory.setUserId(userId);
		return downloadHistoryRepo.save(downloadHistory);
	}

}
