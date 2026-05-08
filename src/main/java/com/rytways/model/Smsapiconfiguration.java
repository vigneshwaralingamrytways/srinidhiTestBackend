package com.rytways.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Smsapiconfiguration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int smsApiDetId;

    private String apiKey;
    private String senderId;
    private String dcs;
    private String flashSms;
    private String route;
    private String channel; 
}
