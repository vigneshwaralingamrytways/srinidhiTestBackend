package com.rytways.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ReportDocument extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long reportDocId;

	private Long transactionId;

	// private Long documentTypeId;

	// private Long folderId;

	// private Long subFolderId;

	private String type;

	private String generatedFileName;

	private String fileName;

	private String remarks;

	private String filePath;

	private String deleteFilePath;

 
	// private String remarks;

	@Transient
	private MultipartFile file;
	@Transient
	private boolean isPreview;

}
