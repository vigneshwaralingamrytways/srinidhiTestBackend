package com.rytways.model;

import java.time.LocalDateTime;

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
public class DocumentAccessHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long documentAccessHitoryId;

	private int userId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = true, insertable = false, updatable = false)
	private Users users;
	
	private Long reportDocId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reportDocId", nullable = true, insertable = false, updatable = false)
	private ReportDocument reportDocument;
	
	private Long transactionId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transactionId", nullable = true, insertable = false, updatable = false)
	private DocumentTransaction documentTransaction;
	
	private LocalDateTime accessTime;
}
