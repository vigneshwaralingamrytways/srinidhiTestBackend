package com.rytways.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IssueSolutionComments extends BaseEntity{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long commentId;
	
	private String commentType;
	
	private String issueComments;
	
	private String commentStatus;
	
	@Lob
	private String commentNotes;
	
	
	private Long issueId;
	
	private String updateBy;

	private LocalDate updateOn = LocalDate.now();

	private String responseBy;

	private LocalDate responseOn;
	
	
	
}