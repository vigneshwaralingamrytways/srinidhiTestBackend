package com.rytways.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rytways.model.SeriousNo;


public interface SeriousNoRepo extends JpaRepository<SeriousNo, Integer> {
	
	@Query("Select sn from SeriousNo sn where sn.seriousDate = ?1 and sn.seriousType=?2")
	Optional<SeriousNo> findSeriousNo(LocalDate financialDate,String seriousType);
	
}
