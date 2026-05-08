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

@Entity
@Getter
@Setter
public class DocumentUserMaster extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long docUserId;
	
	
	 private Long documentTypeId;

	 @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "documentTypeId", nullable = false, insertable = false, updatable = false)
	    private DocumentTypeMaster documentTypeMaster;

	    private Integer userId;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
	    private Users users;
	 
//	    
//	    private Long documentTypeId;
//		 
//		 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = DocumentTypeMaster.class)
//		 @JoinColumn(name = "documentTypeId",nullable = false,insertable =  false, updatable = false)
//		 private DocumentTypeMaster documentTypeMaster;
//		 
//		 
//		    private Integer userId;
//
//		    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL, targetEntity = Users.class)
//		    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
//		    private Users users;
	 
	 private String userName;  
	 
	 private String accesRight;  
	

}
