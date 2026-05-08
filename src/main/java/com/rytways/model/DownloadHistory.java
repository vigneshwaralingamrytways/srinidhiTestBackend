package com.rytways.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class DownloadHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long downloadHistoryId;
	@Column(name = "report_doc_id")
	private Long reportDocId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_doc_id", nullable = false, insertable = false, updatable = false)
	private ReportDocument reportDocument;
	@Column(name = "user_id")
	private int userId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private Users user;

	private LocalDateTime downloadHistoryTime;
}
