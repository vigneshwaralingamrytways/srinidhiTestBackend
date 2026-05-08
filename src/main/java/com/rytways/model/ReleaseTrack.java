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
public class ReleaseTrack extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long releaseTrackId;

	private String releaseName;

	
	@Lob
	private String relDecription;

	@Lob
	private String relObjective;

	@Lob
	private String releaseNote;
	
	
	@Transient
	private List<ReleaseTrackItems> traclList;
	
	@Transient
	private String projectName;

}
