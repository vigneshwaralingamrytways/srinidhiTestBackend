package com.rytways.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.rytways.Categories.ActiveStatus;
import com.rytways.Categories.ActiveStatusConvertor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Categories extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private Long categoryId;
		
	private String categoryName;
	
	private int orderNo;
	
	private String CategoryType;
	
	private int subCategoryId1;
	
	private int subCategoryId2;
	
	private int subCategoryId3;
	
	@Convert(converter = ActiveStatusConvertor.class)
	private ActiveStatus toShow=ActiveStatus.Select;
}
