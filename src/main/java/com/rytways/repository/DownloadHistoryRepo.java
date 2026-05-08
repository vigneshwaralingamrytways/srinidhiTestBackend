package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.DownloadHistory;
@Repository
public interface DownloadHistoryRepo  extends JpaRepository<DownloadHistory,Long>{

	List<DownloadHistory> findByReportDocId(Long reportDocId);
	List<DownloadHistory> findByReportDocIdAndUserId(Long reportDocId, int userId);
	void deleteByReportDocId(Long reportDocId);
	 

}
