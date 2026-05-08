package com.rytways.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.rytways.model.ReleaseTrack;
import com.rytways.model.ReleaseTrackItems;
import com.rytways.model.VisitorsPass;
import com.rytways.service.ReportService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/jasperReports")
public class ReportController {

	
	@Autowired
	 private ReportService reportService;
	
	
	@PostMapping("/reportVisitorGatePass")
	public ResponseEntity<StreamingResponseBody> generateReportForVisitorGatePass(@RequestBody VisitorsPass visitorPass) throws JRException, FileNotFoundException {
	    StreamingResponseBody responseBody = reportService.generateReportForVisitorGatePass(visitorPass);
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Document_Report_" + System.currentTimeMillis() + ".pdf")
	            .contentType(MediaType.APPLICATION_PDF) // Better to specify PDF
	            .body(responseBody);
	}
	
	@PostMapping("/releaseReport")
	public ResponseEntity<StreamingResponseBody> generateReportTest(@RequestBody ReleaseTrackItems release) throws JRException, FileNotFoundException {
	    StreamingResponseBody responseBody = reportService.exportReleaseReport(release);
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=Document_Report_" + System.currentTimeMillis() + ".pdf")
	            .contentType(MediaType.APPLICATION_PDF) // Better to specify PDF
	            .body(responseBody);
	}
	
	@PostMapping("/releaseReportForEveryActivity")
	public ResponseEntity<StreamingResponseBody> generateReportForAllActivities(@RequestBody ReleaseTrack release) throws JRException, FileNotFoundException {
	    StreamingResponseBody responseBody = reportService.exportReleaseReportForAllActivities(release);
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=Multi_Activity_Report_" + System.currentTimeMillis() + ".pdf")
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(responseBody);
	}

}