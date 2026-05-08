package com.rytways.model;


import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	//@CreatedDate
	//@LastModifiedDate
    private LocalDateTime updatedOn = LocalDateTime.now();
    
   // @CreatedBy
    //@LastModifiedBy
    private Integer updatedBy=1;
    
    
    private LocalDateTime createdOn;
    
    // @CreatedBy
     //@LastModifiedBy
     private Integer createdBy=1;
     
 	
}
