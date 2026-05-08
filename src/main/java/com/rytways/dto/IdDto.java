package com.rytways.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rytways.Categories.PoMasterStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdDto {
	
	
	 private List<Integer>  ids=new ArrayList<>();
	 
	 private int id;
	 
	 private Long longId;
	 
	 private String orderNo;
	 
	 private String catName;
	 
	 private int productId;
	 
	 private int pageNo;
	 
	 private int rowsPerPage;
	 
	 private Date reqDate;
	 
	 private int departmentId;
	 
	 private ArrayList<Integer> departmentIds;
	 
	 private int month=0;
	 
	 private int year=0;
	 
	 private LocalDate startDate;
	 
	 private LocalDate endDate;
	 
	 private int costingMenuId;
	 
	 private int inputSheetId;
	 
	 private String docType;
	 
	 private List<PoMasterStatus> poStatus;
	 
	 private String approvalType;
}
