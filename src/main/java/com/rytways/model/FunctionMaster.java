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
public class FunctionMaster extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long functionId;
	
	private String functionName;
	
	private String functionPath;
	
	private String description;
	
	private String functionStatus;

	
	private Long processId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "processId",nullable = false,insertable =  false, updatable = false)
 	private ProcessMaster process;
	
	@Transient
	private Integer userId;
	
	@Transient
	private Integer roleId;
	
}
