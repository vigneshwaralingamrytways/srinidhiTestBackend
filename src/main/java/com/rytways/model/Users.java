package com.rytways.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
public class Users extends BaseEntity{
	
	 	@Id
	    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	    @GenericGenerator(name = "native",strategy = "native")
	    private int UserId;
	 	
	 	//@NotBlank(message="Name must not be blank")
	   // @Size(min=3, message="Name must be at least 3 characters long")
	    private String userName;
	 	
	 	
	 //	@NotBlank(message="Name must not be blank")
	//    @Pattern(regexp="""^(?=.*[a-z])(?=.*\d)(?=.*[A-Z]).{8,50}$""", message="Name must be at least 3 characters long")
	 	private String Password;
	 	
	 	
	 	//@NotBlank(message="Name must not be blank")
	   // @Size(min=3, message="Name must be at least 3 characters long")
	 	private String PersonName;
	 	
	 	//@NotBlank(message="Department must not be blank")
	 	//@Range(min = 0, max = 10, message="Please select a valid department")
	 	//private int departmentId;

	   

        private int departmentId;
		
		
		@OneToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "departmentId", nullable = false ,insertable=false,updatable=false )
		private DepartmentMaster department;
		
	 	//@NotBlank(message="Email must not be blank")
	    @Email(message = "Please provide a valid email address" )
	 	private String Email;
	 	
		//@NotBlank(message="Please Select Reporting Manager")
	 	//@Range(min = 0, max = 10, message="Please select a valid Reporting Manager")
		private int ReportingManager;
	 	
		
		//@NotBlank(message="Please Select Role")
	 //	@Range(min = 0, max = 10, message="Please select a valid Role")
		private int roleId;
		
				
		@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinColumn(name = "roleId",nullable = false,insertable=false,updatable=false)
	 	private Roles role;
	 	
	 	//@NotBlank(message="Please Select Region")
	 	//@Range(min = 0, max = 10, message="Please select a valid Reporting Manager")
	 	private int Region;
	 	
	 	//@NotBlank(message="Mobile number must not be blank")
	 //   @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	    private String PhoneNo;
	 	
	 //   @ElementCollection
	    private String machineName; // Mapped in UserMaster.
	    
	    @EqualsAndHashCode.Exclude
		@ToString.Exclude
	 	//@JsonManagedReference
		//@JsonIgnoreProperties("order")
		@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinColumn(name = "userId",nullable = false,insertable=false,updatable=false)
		private List<UserDeptMap>  departments=new ArrayList<>();	
	    
		/*
		 * @OneToMany(mappedBy = "users", cascade =
		 * CascadeType.ALL,fetch=FetchType.EAGER)
		 * 
		 * @JsonManagedReference private List<OrderMaster> orders;
		 */
	    	private String userType;
	    
		
		  @Column(name = "reset_password_token") 
		  private String resetPasswordToken;
		 
	
	 	@Transient
		private String newPassword;
	 	
}