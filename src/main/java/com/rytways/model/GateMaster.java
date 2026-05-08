package com.rytways.model;

import javax.persistence.CascadeType;
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

@Getter
@Setter
@Entity
public class GateMaster extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long gateId;  
    
   // private String palletStatus; // 0 inactive ,   1 InAcactive

	private int companyId;
	
  //  @Column(nullable = false, unique = true)
    private String gateName;
    
    private String description;
	
}
