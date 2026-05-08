package com.rytways.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.exception.UserNotFoundException;
//import com.rytways.model.ClientUsers;
import com.rytways.model.Users;
//import com.rytways.repository.ClientUserRepository;
import com.rytways.repository.UserRepository;





@Service
@Transactional
public class PasswordResetService {
	
	@Autowired
    private UserRepository userRepository;
	
//	@Autowired
//	private ClientUserRepository clientUserRepository;
	
	 public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
	        Optional<Users> users = userRepository.findByUserName(email);
	        
	        System.out.println("User Availability check->"+users.isPresent());
	        
	        if(users.isPresent()){
		        	/*Users users2 = users.get();
		        	if (users2 != null) {
		        	users2.setResetPasswordToken(token);
		        	userRepository.save(users2);*/
		        	
		        	
		        	userRepository.updateResetPasswordToken(token, email);
		        	
		       // }
	        }else {
	        	
	            throw new UserNotFoundException("Could not find any user with the email " + email);
	        }
	    }
	     
	 
	 public Optional<Users> getByResetPasswordToken(String token) {
	        return userRepository.findByResetPasswordToken(token);
	    }
	 
	 
	public boolean changePassword(Users user) {
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		String password = user.getPassword();
		String encodedPassword = passEncoder.encode(password);
		System.out.println(encodedPassword);
		String newPassword = user.getNewPassword();
		String encodedNewPassword = passEncoder.encode(newPassword);
		String resetPasswordToken=null;
		boolean status = false;

		Optional<Users> userOptional= userRepository.findById(user.getUserId());
		
		if(userOptional.isPresent()){
			if(passEncoder.matches(user.getPassword(), userOptional.get().getPassword())){
			userOptional.get().setPassword(encodedNewPassword);
			userRepository.save(userOptional.get());
			status= true;
			}
		}
		
		return status;
	}
	
	
	
	public String updatePassword(String email,String password) throws UserNotFoundException {
        Optional<Users> users = userRepository.findByEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pswd=password;
        System.out.println("pswd::->"+pswd);
		String encodedPassword = passwordEncoder.encode(pswd);
	
        
        System.out.println("User Availability check->"+users.isPresent());
        
        if(users.isPresent()){
	        	/*Users users2 = users.get();
	        	if (users2 != null) {
	        	users2.setResetPasswordToken(token);
	        	userRepository.save(users2);*/
	        userRepository.updateResetPassword(email, encodedPassword);
	        
	        return pswd;
	        	
	       // }
        }else {
        	
            throw new UserNotFoundException("Could not find any user with the email " + email);
            
        }
        
        }
	
	public String fetchUserEmailByUserName(String userName){
		
		 Optional<Users> users = userRepository.findByUserName(userName);
		 String userEmail=null;
	        
	        System.out.println("User Availability check->"+users.isPresent());
	        
	        if(users.isPresent()){
		        	Users usersReference = users.get();
		        	if (usersReference != null) {
		        	
		        		 userEmail = usersReference.getEmail();
		        	
		        		 System.out.println("User Availability check->"+users.isPresent());
		       // }
	        }else {
	        	
	            throw new UserNotFoundException("Could not find any user:=> " + userName);
	        }
	    }
	     
		
		return userEmail;
	}


	//---------------------------Client-----------------

//		public boolean changePassword(ClientUsers user) {
//			BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
//			String password = user.getPassword();
//			String encodedPassword = passEncoder.encode(password);
//			System.out.println(encodedPassword);
//			String newPassword = user.getNewPassword();
//			String encodedNewPassword = passEncoder.encode(newPassword);
//			String resetPasswordToken=null;
//			boolean status = false;
//
//			Long customerId = Long.valueOf(user.getUserId());
//			List<ClientUsers> userOptional= clientUserRepository.findByCustomerId(customerId);
//			if(userOptional.size()>0){
//				if(passEncoder.matches(user.getPassword(), userOptional.get(0).getPassword())){
//					ClientUsers client = userOptional.get(0);
//					client.setPassword(encodedNewPassword);
//				clientUserRepository.save(client);
//				status= true;
//				}
//			}
//			
//			return status;
//		}
		
//		public String fetchUserEmailByUserNameForClient(String userName) {
//			Optional<ClientUsers> users = clientUserRepository.findByUserName(userName);
//			 String userEmail=null;
//		        if(users.isPresent()){
//		        	ClientUsers usersReference = users.get();
//			        	if (usersReference != null) {
//			        		 userEmail = usersReference.getEmail();
//		        }else {
//		            throw new UserNotFoundException("Could not find any user:=> " + userName);
//		        }
//		    }
//			return userEmail;
//		}


//		public String updatePasswordForClient(String userName, String generateRandomPassword) {
//			Optional<ClientUsers> users = clientUserRepository.findByUserName(userName);
//		        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		        String pswd=generateRandomPassword;
//				String encodedPassword = passwordEncoder.encode(pswd);
//		        if(users.isPresent()){
//		        	users.get().setPassword(encodedPassword);
//		        	clientUserRepository.save(users.get());
//			        return pswd;
//		        }else {
//		            throw new UserNotFoundException("Could not find the user " + userName);
//		            
//		        }
//		}
     
}