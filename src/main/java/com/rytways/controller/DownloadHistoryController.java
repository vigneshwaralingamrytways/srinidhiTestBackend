package com.rytways.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.DownloadHistory;
import com.rytways.service.DownloadHistoryService;

@RestController
@RequestMapping("/downloadHistory")
public class DownloadHistoryController {
	@Autowired
	private DownloadHistoryService downloadHistoryService;

	@PostMapping("/create")
	public Object createHist(@RequestBody Map<String, String> map) {
		Long reportDocId = Long.parseLong(map.get("reportDocId"));
		int userId = Integer.parseInt(map.get("userId"));
		String timeStr = String.valueOf(map.get("time"));
		LocalDateTime dateTime = LocalDateTime.parse(timeStr);
		return downloadHistoryService.saveDownloadHostory(reportDocId, userId, dateTime);

	}

	@PostMapping("/getDownloadHistory")
	public List<DownloadHistory> getAllHist(@RequestBody Map<String, String> map) {
		Long reportDocId = Long.parseLong(map.get("reportDocId"));
		int userId = Integer.parseInt(map.get("userId"));
		return downloadHistoryService.getAll(reportDocId, userId);
	}

}
