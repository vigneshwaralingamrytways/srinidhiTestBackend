package com.rytways.dto;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

	    
	    private int roleId;
	    
	    private int userId;
	    
	    private String userName;
	    
	    private double timeOut;
	    
    private String accessToken;
    
    private String token;
    
    private String machineName;
    
    private String userType;
    
    private String departmentIds;
    
    private String departments;
}