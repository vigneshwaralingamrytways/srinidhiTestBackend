package com.rytways.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.rytways.Categories.ActiveStatus;
import com.rytways.Categories.ActiveStatusConvertor;
import com.rytways.Categories.CustomerCategory;
import com.rytways.Categories.CustomerCategoryConvertor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CompanyMaster extends BaseEntity{

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int companyId;
	
//	@NotBlank(message="Customer Name must not be blank")
    //@Size(min=3,max=100, message="Customer Name must be between 3 to 100 characters")
    private String companyName;
    
    private String prefixName;
    
    private String contactPerson;
	
	//@NotBlank(message="Address must not be blank")
    //@Size(min=10, max=500, message="Address must be between 10 to 500 characters")
    private String companyAddress;
    
    private String registeredAddress;
    
    private String imageLocation;
    
    //@NotBlank(message="Pan must not be blank")
    //@Size(min=10, max=45, message="Pan must be between 10  characters")
    private String pan;
	
	//@NotBlank(message="GST must not be blank")
    //@Size(min=12, max=45, message="GST must be between 13  characters")
    private String gst;
    
    private String iecNo;
    
    private String cin;
	
	//@NotBlank(message="Mobile number must not be blank")
    //@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String contactNo;
	
	//@NotBlank(message="Email must not be blank")
    //@Email(message = "Please provide a valid email address" )
 	private String contactEmail;
	
	//@NotBlank(message="Please Select Category")
	@Column(name="category")
 	//@Range(min = 0, max = 10, message="Please select a valid Category")
	@Convert(converter = CustomerCategoryConvertor.class)
	private CustomerCategory category;
 	
		
	//@NotBlank(message="GST must not be blank")
    //@Size(min=4, max=50, message="Ledger must be between 4 to 50  characters")
    private String ledgerCode;
    
    private String paymentTerms;
	
	//@NotBlank(message="GST must not be blank")
    //@Size(min=4, max=100, message="LedgerName must be between 4 to 100  characters")
    private String ledgerName;
	
	//@NotBlank(message="Please Select Status")
 	//@Range(min = 0, max = 10, message="Please select a valid Status")
	@Convert(converter = ActiveStatusConvertor.class)
	private ActiveStatus status;
	
	private String documentUpload;	
	
	private int stateId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = State.class)
	@JoinColumn(name = "stateId",nullable = true,insertable =  false, updatable = false)
 	private State state; 
	 
	private String poDocNo;
	
	private String joDocNo;
	
	private String spoDocNo;
		
	}
