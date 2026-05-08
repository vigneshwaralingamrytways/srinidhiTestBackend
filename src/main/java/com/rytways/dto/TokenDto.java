package com.rytways.dto;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TokenDto {
   // private String userId;
    private String token;
   // private String refreshToken; 
    
    private int roleId;
    
    private int userId;
    
    private double timeOut;
    
    private String personName;
    
    private String machineName;

    private String userType;
    
    private String departmentIds;
    
    private String departments;
}
