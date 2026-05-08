package com.rytways.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rytways.model.SmsCounter;

@Repository
public interface SmsCounterRepo extends  JpaRepository<SmsCounter, Integer> {

	@Query("SELECT MAX(c.counterId) FROM SmsCounter c")
	Optional<Integer> findMaxCounterId();

}
