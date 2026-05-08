package com.rytways.service;

import java.io.FileNotFoundException;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.rytways.model.ReleaseTrack;
import com.rytways.model.ReleaseTrackItems;
import com.rytways.model.VisitorsPass;

import net.sf.jasperreports.engine.JRException;

public interface ReportService {
	
	
	StreamingResponseBody exportReleaseReport(ReleaseTrackItems release) throws FileNotFoundException, JRException;

	StreamingResponseBody exportReleaseReportForAllActivities(ReleaseTrack release) throws FileNotFoundException, JRException;
	StreamingResponseBody generateReportForVisitorGatePass(VisitorsPass visitorPass) throws JRException;
	
	
	
}