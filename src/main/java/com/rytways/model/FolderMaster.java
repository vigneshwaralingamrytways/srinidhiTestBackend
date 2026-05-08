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
public class FolderMaster extends BaseEntity{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long folderId;
	
	
	private Long documentTypeId;
	 
	 @ManyToOne(fetch = FetchType.EAGER, targetEntity = DocumentTypeMaster.class)
	 @JoinColumn(name = "documentTypeId",nullable = false,insertable =  false, updatable = false)
	 private DocumentTypeMaster documentTypeMaster;
	 
	private String folderCategoryName;
	
	//@Lob
	private String folderDescribtion;

	
	
	
	
}
