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
public class ReleaseTrackItems extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long trackItemId;
	
	private Long releaseTrackId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "releaseTrackId",nullable = true,insertable =  false, updatable = false)
 	private ReleaseTrack track;
	
	private String statusName;
	
	private Integer displaySeqNo;
	
	private Long activityId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activityId",nullable = true,insertable =  false, updatable = false)
 	private ActivityMaster activity;
	
	@Transient
	private String issueType;
	
	@Transient
	private String projectName;

}
