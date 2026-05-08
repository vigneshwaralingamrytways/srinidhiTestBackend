package com.rytways.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VisitorsPass extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private Long visitorId;
	
	private String passNo;
	
	private String createBy;
	
	private String priorAppointment;
	
	private LocalDate entryDate;
	
	private String status;
	
	private String department;
	
	private String visitorName;
	
	private String visitorCompany;
	
	private String address;
	
	private String phoneNo;
	
	private String emailId;
	
	private int noOfPersons;
	
	private String nameList;
	
	private String purpose;
	
	private int visitorTypeId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Categories.class)
    @JoinColumn(name = "visitorTypeId",referencedColumnName = "categoryId",nullable = false,insertable =  false, updatable = false)
 	private Categories visitorType;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Kolkata")
	private LocalDateTime visitingDate;
	
	private String food="No";
	
//	private int hours;
//	
//	private int minutes;
	
	private LocalDateTime inTime;
	
	private LocalDateTime outTime;
	
	private Integer numberTaken;
	
	private String unit;
	
//	private int unitId;
//
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UnitMaster.class)
//	@JoinColumn(name = "unitId", nullable = false, insertable = false, updatable = false)
//	private UnitMaster unit;
	
	private int gateId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = GateMaster.class)
	@JoinColumn(name = "gateId", referencedColumnName = "gateId", nullable = false, insertable = false, updatable = false)
	private GateMaster gate;
	
	private String modeOfVehicle;
	
	private String vehicleNo;
	
	private String toWhomMeet;
	
	private String toWhomMeetDepartment;
	
	private String enteredBy;
	
	private String remarks;	
	private String location;
	
	@Transient
	private LocalDate fromDate;
	
	@Transient
	private LocalDate toDate;
	
		
}
