package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.Smsapiconfiguration;

@Repository
public interface SmsapiconfigurationRepo extends  JpaRepository<Smsapiconfiguration, Integer>{ 

}
