package com.rytways.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SmsCounter extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	private int counterId;

	private Long userId;

	private String counter;

	private LocalDateTime counterDateTime;

	private String url;

	private String type;
}
