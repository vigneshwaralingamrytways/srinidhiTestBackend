package com.rytways.model;



import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RefreshToken extends BaseEntity{

		@Id
		@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
		@GenericGenerator(name = "native",strategy = "native")
	    private int id;

	    private String token;

	    
		private Instant expiryDate;

		 @OneToOne
		    @JoinColumn(name = "userId",nullable = true)
		    private Users userInfo;
//	    
//	    @OneToOne
//	    @JoinColumn(name = "clientUserId",nullable = true)
////	    private ClientUsers clientInfo;
//	
}
