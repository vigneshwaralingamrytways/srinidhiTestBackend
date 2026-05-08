package com.rytways.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ActivityMasterFeatures extends BaseEntity{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long activityFeatureId;
	
	
	private long activityId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activityId",nullable = false,insertable =  false, updatable = false)
 	private ActivityMaster activity;
	
	private Integer userId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId",nullable = true,insertable =  false, updatable = false)
 	private Users user;
	
	private Integer roleId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId",nullable = true,insertable =  false, updatable = false)
 	private Roles role;
	
	@Transient
	private Long liveStatus;
	
}
