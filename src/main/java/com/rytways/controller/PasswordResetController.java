package com.rytways.controller;


import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.exception.UserNotFoundException;
import com.rytways.helper.RandomPasswordGenerator;
//import com.rytways.model.ClientUsers;
import com.rytways.model.Users;
import com.rytways.service.PasswordResetService;

import net.bytebuddy.utility.RandomString;


@RestController
@RequestMapping("/reset-password")
public class PasswordResetController {
	
	 @Value("${spring.mail.username}")
	 private String sender;

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private PasswordResetService passwordResetService;
	
	@Autowired
	public PasswordResetController(JavaMailSender mailSender, PasswordResetService passwordResetService) {
	
		this.mailSender = mailSender;
		this.passwordResetService = passwordResetService;
	}

	@PostMapping("/forgot_password")
	public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> requestBody,HttpServletRequest request){
		
		 String userName = requestBody.get("userName");
	    
	   // String token = RandomString.make(30);
	    int passwordLength = 10; // Adjust the length as needed
	    String generateRandomPassword = RandomPasswordGenerator.generateRandomPassword(passwordLength);
	      
	   // String  generateRandomPassword="Admin@123";
	        
	        System.out.println("Generated Password: " + generateRandomPassword);
	   
	    try {
	    	
	    	/*passwordResetService.updateResetPasswordToken(token, email);
	        String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
	        System.out.println("Click the LInk:"+resetPasswordLink);
	       // sendEmail(email, resetPasswordLink);
	        * 
*/	 
	    	String userEmail = passwordResetService.fetchUserEmailByUserName(userName);
	    	String updatedPassword = passwordResetService.updatePassword(userEmail,generateRandomPassword);
			sendEmailWithPassword(userEmail,generateRandomPassword);
	    } catch (UserNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (UnsupportedEncodingException | MessagingException ex) {
	        ex.printStackTrace();
	    }
	         
	    return ResponseEntity.ok("Password reset email sent successfully.");
	}
	

	public void sendEmailWithPassword(String recipientEmail,String updatedPassword)
	        throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(sender, "Rytways");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Here's the new updated password";
	     
	    String content = "<p>Hello,</p>"
	            + "<p>You have requested to use the new password for SRIPATHI Application.</p>"
	            + "<p>"+updatedPassword+"</p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}
	
	public void sendEmail(String recipientEmail, String link)
	        throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(sender, "Rytways");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Here's the link to reset your password";
	     
	    String content = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + link + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}
  

	
	@PostMapping("/change_password")
	public ResponseEntity<String> requestChangeReset(@RequestBody Users user){
	    
	    String token = RandomString.make(30);
	    boolean passwordChanged =false;
	     
	    try {
	    	passwordChanged = passwordResetService.changePassword(user);
		//	sendEmailWithPassword(user.getEmail(),updatedPassword);
	    } catch (UserNotFoundException ex) {
	        ex.printStackTrace();
	    } 
	    if(passwordChanged){
	    	 return ResponseEntity.ok("Password Reset Successfully");
	    }else{
	    	return ResponseEntity.badRequest().body("Unable to Reset Password");
	    }
		
	    
	}
	
	
	//--------------------------ClientUser---------------------------
	
////		@PostMapping("/change_passwordForClient")
////		public ResponseEntity<String> requestChangeResetForClient(@RequestBody ClientUsers user){
////		    
////		    String token = RandomString.make(30);
////		    boolean passwordChanged =false;
////		     
////		    try {
////		    	passwordChanged = passwordResetService.changePassword(user);
////			//	sendEmailWithPassword(user.getEmail(),updatedPassword);
////		    } catch (UserNotFoundException ex) {
////		        ex.printStackTrace();
////		    } 
////		    if(passwordChanged){
////		    	 return ResponseEntity.ok("Password Reset Successfully");
////		    }else{
////		    	return ResponseEntity.badRequest().body("Unable to Reset Password");
////		    }
////			
//		    
//		}
		
//		@PostMapping("/forgot_passwordForClient")
//		public ResponseEntity<String> requestPasswordResetForClient(@RequestBody Map<String, String> requestBody,HttpServletRequest request){
//			
//			 String userName = requestBody.get("userName");
//		    int passwordLength = 10; // Adjust the length as needed
//		    String generateRandomPassword = RandomPasswordGenerator.generateRandomPassword(passwordLength);
//		    try {
//		    	String userEmail = passwordResetService.fetchUserEmailByUserNameForClient(userName);
//		    	passwordResetService.updatePasswordForClient(userName,generateRandomPassword);
//				sendEmailWithPassword(userEmail,generateRandomPassword);
//		    } catch (UserNotFoundException ex) {
//		        ex.printStackTrace();
//		    } catch (UnsupportedEncodingException | MessagingException ex) {
//		        ex.printStackTrace();
//		    }
//		    return ResponseEntity.ok("Password reset email sent successfully.");
//		}

}

