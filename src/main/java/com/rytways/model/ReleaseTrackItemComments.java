package com.rytways.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ReleaseTrackItemComments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long commentId;
	
	private String commentName;
	
	
	
	private String commentStatus;

	
	private Long trackItemId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trackItemId",nullable = false,insertable =  false, updatable = false)
 	private ReleaseTrackItems items;

}
