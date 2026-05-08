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

@Getter
@Setter
@Entity
public class DataPointModel extends BaseEntity {
		
		@Id
	    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	    @GenericGenerator(name = "native",strategy = "native")
		private Long dataPointId;
		
		
		 private Long dataId;
		 
		 
		 private Long processId;
		 
		 @ManyToOne(fetch = FetchType.EAGER)
			@JoinColumn(name = "processId",nullable = true,insertable =  false, updatable = false)
		 	private ProcessMaster process;
		 
		 
		 private Long functionId;
			
			@ManyToOne(fetch = FetchType.EAGER)
			@JoinColumn(name = "functionId",nullable = true,insertable =  false, updatable = false)
		 	private FunctionMaster function;
			
			
			private Long activityId;
			
			@ManyToOne(fetch = FetchType.EAGER)
			@JoinColumn(name = "activityId",nullable = true,insertable =  false, updatable = false)
		 	private ActivityMaster activity;
			
			private Long refDataPointId;
			
			@ManyToOne(fetch = FetchType.EAGER)
		    @JoinColumn(name = "refDataPointId", nullable = true, insertable =  false, updatable = false)
		    private DataPointModel refDataPoint;
		 
			
			private Long refReqDataPointId;
			
			@ManyToOne(fetch = FetchType.EAGER)
		    @JoinColumn(name = "refReqDataPointId", nullable = true, insertable =  false, updatable = false)
		    private DataPointModel refReqDataPoint;
			
		 private String type;
		 
		 
		 private String infoName;
		 
		private String remarks;
		
		private String infoType;
		
		private String connected = "NO";
		
		

}
