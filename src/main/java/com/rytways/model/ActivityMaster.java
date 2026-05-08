package com.rytways.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ActivityMaster extends BaseEntity{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long activityId;
	
	private String activityName;
	
	private String activityPath;
	
	private String devLinkType;
	
	private String devPath;
	
	private String description;
	
//	private String activityStatus;
	private Integer activityStatus;
	
	private Integer displaySeqNo;
	
	@Lob
	private String activityNotes;
	
	private String userType;  // ROLE_WISE, USER_WISE, GENERAL
	
	private Long functionId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "functionId",nullable = true,insertable =  false, updatable = false)
 	private FunctionMaster function;
	
	@Transient
	private Integer userId;
	
	@Transient
	private Integer roleId;
	
	@Transient
	private int liveStatus;
}
