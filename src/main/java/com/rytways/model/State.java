package com.rytways.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class State extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
   // @GenericGenerator(name = "native", strategy = "native")
	// @Column(name = "orderId", updatable = false, nullable = false)
	 private int stateId;
	
	private String stateName;
	
	private String stateShortName;
	
	private int stateNo;
	
	private int region;
}
