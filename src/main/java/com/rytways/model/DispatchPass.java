package com.rytways.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DispatchPass extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private Long dispatchid;
	
	private String unit;
	
	private int departmentId=1;
	
	private String gateEntryNo;
	
//	private int unitId;
//	
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UnitMaster.class)
//    @JoinColumn(name = "unitId",nullable = false,insertable =  false, updatable = false)
// 	private UnitMaster unit;
	
	private int gateId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = GateMaster.class)
    @JoinColumn(name = "gateId",referencedColumnName = "gateId",nullable = false,insertable =  false, updatable = false)
 	private GateMaster gate; 
	
	private LocalDate entryDate;
		
	private String vechileNo;
	
	private String mobileNo;
	
	private String transportName;
	
	private String driverName;
	
	private String customerName;
	
	private String destination;
	
	private String invoiceNo;
	
	private String dcNo;
	
	private String wgtBridgeNo;
	
	private Float invoiceWgt;
	
	private Float fullWgt;
	
	private Float emptyWgt;
	
	private Float actualWgt;
	
	private String remarks;
	
	private String status;
	
	private LocalDate dispatchDate;
	
	private LocalDateTime inTime;
	
	private LocalDateTime outTime;
	
	@Transient
	private LocalDate fromDate;
	
	@Transient
	private LocalDate toDate;
			
		
}
