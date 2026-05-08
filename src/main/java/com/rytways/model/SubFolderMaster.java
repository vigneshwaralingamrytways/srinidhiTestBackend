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

@Entity
@Getter
@Setter
public class SubFolderMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long subFolderId;

	private Long documentTypeId;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = DocumentTypeMaster.class)
	@JoinColumn(name = "documentTypeId", nullable = false, insertable = false, updatable = false)
	private DocumentTypeMaster documentTypeMaster;

	private Long folderId;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = FolderMaster.class)
	@JoinColumn(name = "folderId", nullable = true, insertable = false, updatable = false)
	private FolderMaster folderMaster;

	private String subFolderCategoryName;

	private Integer parenId;
	// private Integer folderLevel;

	// @Lob
	private String subFolderDescribtion;

}
