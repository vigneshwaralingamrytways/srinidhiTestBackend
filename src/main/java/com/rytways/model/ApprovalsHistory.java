package com.rytways.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.rytways.Categories.AppHistAppCat;
//import com.rytways.Categories.AppHistAppCatConvertor;
//import com.rytways.Categories.OrderStatus;
//import com.rytways.Categories.OrderMasterStatusConvertor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table
public class ApprovalsHistory extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	private int approvalHistoryId;
	   
    private String approver="NA";
    
    private String approverRemarks="NA";
    
    private int authorityId;
     
    private Long id;
    
    private String appType;
    
    private int showApproval=1;
    
    private int isApproved=0;
    
    private int isCancelled=0;
    
    private int approvedBy;
    
    private int departmentId;
    
    @Transient
    private Users approvalPerson;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = ApprovalAuthorities.class)
    @JoinColumn(name = "authorityId",nullable = true,insertable =  false, updatable = false)
 	private ApprovalAuthorities authoriy;

}
	