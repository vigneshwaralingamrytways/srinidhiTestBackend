package com.rytways.model;

import java.time.LocalDate;

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
public class FormConfigMasterForTaskType extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int formConfigId;

	private int taskTypeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "taskTypeId", nullable = false, insertable = false, updatable = false)
	private TaskType taskType;

	private String itemOne;
	private String itemTwo;
	private String itemThree;
	private String itemFour;
	private String itemFive;
	private String itemSix;
	private String itemSeven;
	private String itemEight;
	private String itemNine;
	private String itemTen;
	private String itemEleven;
	private String itemTwelve;
	private String itemThirteen;
	private String itemFourteen;
	private String itemFifteen;
	private String itemSixteen;
	private String itemSeventeen;

	private String formDescribtion;

}
