package com.rytways.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DocumentTransaction extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long transactionId;

	private Long documentTypeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "documentTypeId", nullable = false, insertable = false, updatable = false)
	private DocumentTypeMaster documentTypeMaster;

	private Long folderId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "folderId", nullable = true, insertable = false, updatable = false)
	private FolderMaster folderMaster;

	private Long subFolderId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subFolderId", nullable = true, insertable = false, updatable = false)
	private SubFolderMaster subFolderMaster;

	private Long docUserId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "docUserId", nullable = true, insertable = false, updatable = false)
	private DocumentUserMaster documentUserMaster;

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
	private String itemRemarks;

	private Integer docAvailable = 0;

	private String keyWord;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate createdDate;

	@Transient
	private LocalDate fromDate;

	@Transient
	private LocalDate toDate;

}
