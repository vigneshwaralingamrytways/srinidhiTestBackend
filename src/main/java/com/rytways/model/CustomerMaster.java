package com.rytways.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CustomerMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String customerName;
    private String customerAddress;
    private String customerType;
    private String customerCode;
    private Long dealerId;
   // private Long consigneeId; // To be Deleted
   // private Long customerId;
    private String country;
    private String region;
    private String pan;
    private String customerGst;
    private String mobileNo;
    private String emailId;
    private String customerCategory;
    private String customerSegment;
    private String paymentTerms;
    private String incoTerms;
    private String ledgerName;
    private boolean isActive;
    private String contactPerson;
    private String custState;
    

    
}
