package com.rytways.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rytways.model.SeriousNo;
import com.rytways.repository.SeriousNoRepo;

@Service
public class SeriousNoService {

	@Autowired
	SeriousNoRepo seriousRepo;
	
	public SeriousNo createSerious(LocalDate date,String seriousName){
		String isSaved = "";
		SeriousNo serious = new SeriousNo();
		serious.setSeriousDate(date);
		serious.setSeriousType(seriousName);
		serious.setSeriousNo(1);
		
		seriousRepo.save(serious);
		
		return serious;
	
}
	
	
	public SeriousNo updateSerious(SeriousNo serious){
		String isSaved = "";
		serious.setSeriousNo(serious.getSeriousNo()+1);
//		serious.setSeriousNo(1);
		
		seriousRepo.save(serious);
		
		return serious;
	
}
	
}
