package com.rytways.service;



import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rytways.model.RefreshToken;
import com.rytways.model.Users;
import com.rytways.repository.RefreshTokenRepository;
import com.rytways.repository.UserRepository;
import com.rytways.security.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutHandler  {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil; 
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RefreshTokenRepository refreshRepository;
	
/*	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		 System.out.println("Inside onLogoutSuccess");
		 
		 final String authHeader = request.getHeader("Authorization");
		    final String jwt;
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		      return;
		    }
		    
		    jwt = authHeader.substring(7);
		    System.out.println("jwt"+jwt);
		    
		  
		    String username = jwtTokenUtil.getUsernameFromToken(jwt);
		    
		  System.out.println("user:::authentication->"+authentication.isAuthenticated());
		    
		  if(authentication.isAuthenticated()){
		  String userLogged = authentication.getName();
		    
		    System.out.println("user->"+userLogged+"Logged out sucessfully");
		  }
		  
		    UrlPathHelper helper=new UrlPathHelper();
		    String contextPath = helper.getContextPath(request);
		    response.sendRedirect(contextPath);
		    
		  //  super.onLogoutSuccess(request, response, authentication);
  		  
		   // SecurityContextHolder.clearContext();
		    
		    }
*/
	
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		
		System.out.println("Inside logout");
		 
			final String authHeader = request.getHeader("Authorization");
		    final String jwt;
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		      return;
		    }
		    
		    jwt = authHeader.substring(7);
		    System.out.println("jwt:::->"+jwt);
		    String username = jwtTokenUtil.getUsernameFromToken(jwt);
		
			 
				// Handle logout request
			    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
			            && request.getServletPath().equals("/logout")) {
			       
			    	// Perform logout logic (invalidate or delete refreshToken)
			    	
			    	 Optional<Users> users = userRepository.findByUserName(username);
			    	 System.out.println("user->"+users.isPresent());
			    	if(users.isPresent()){
			    		
			    		int userId = users.get().getUserId();
				        Optional<RefreshToken> tokens = refreshRepository.findByuserId(userId);
				    
					       if(tokens.isPresent()){
					    	   refreshRepository.delete(tokens.get());
					       }
				       
				     System.out.println("user->"+username+"  Logged out sucessfully::::RefreshToken Deleted");
			        // Clear any existing SecurityContextHolder context
			       
			        try {
						new ObjectMapper().writeValue(response.getOutputStream(), new String("user-> "+username+" Logged out sucessfully"));
					} catch (IOException e) {
						
						e.printStackTrace();
					}
			        SecurityContextHolder.clearContext();
			        return;
			
			    }
			    }
			    
			  /*  try {
			    	
			    	UrlPathHelper helper=new UrlPathHelper();
				    String contextPath = helper.getContextPath(request);
				    System.out.println("contextPath->"+contextPath+"/login/signin");
					response.sendRedirect(contextPath+"/login/signin");
										 
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		    */
		    
	}
	}
		  
	