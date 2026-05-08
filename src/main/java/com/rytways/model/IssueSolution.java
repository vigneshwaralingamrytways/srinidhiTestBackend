package com.rytways.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IssueSolution extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long issueId;

	private String issueType;

	private Long functionActivityId;
	
	private Long releaseTrackId;

	@Lob
	private String currentProblem;
	
	@Lob
	private String currentDetails; // Blue Print
	
	@Lob
	private String controls;
	
	@Lob
	private String manageObjective;  // Blue Print

	@Lob
	private String solution;
	
	@Lob
	private String incoming; // Blue Print
	
	@Lob
	private String dataPoints;
	
	@Lob
	private String outGoing; // Blue Print

	private String issueStatus;
	
	@Lob
	private String issueNotes;
	
	@Transient
	private List<IssueSolutionComments> commentList;

	@Transient
    private String commentType;
	
	 @Transient
    private List<String> commentTypeNotIn;

}