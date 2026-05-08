package com.rytways.service;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.dto.LoadOptionsStringDto;
//import com.rytways.model.OtpVerification;
import com.rytways.model.Roles;
import com.rytways.model.Users;
import com.rytways.repository.DepartRepository;
//import com.rytways.repository.OtpVerificationRepository;
import com.rytways.repository.RoleRepository;
import com.rytways.repository.UserRepository;
import com.rytways.specifications.UserMasterSpec;

@Component
@Service
@Transactional
public class UserService {
	
	
	 @Value("${spring.mail.username}")
	 private String sender;

	@Autowired
    private JavaMailSender mailSender;
	

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private DepartRepository departRepo;
	
//	  @Autowired
//	    private OtpVerificationRepository otpRepo;

	public Users saveCustomer(Users cust) {
		String isSaved = "";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		System.out.println("cust.getRoleId();==>"+cust.getRoleId()); 

		String pswd = passwordEncoder.encode(cust.getPassword());
		cust.setPassword(pswd);
		
		cust = userRepo.save(cust);

		 Optional<Roles> role= roleRepo.findById(cust.getRoleId());
		 
		cust.setRole(role.get());

		// Optional<DepartmentMaster> depart= departRepo.findById(cust.getDepartmentId());

	//	 cust.setDepartments(depart.get());
		 
		return cust;
	}

	
	 // 1. Send OTP
//	  public void sendOtpToUser(String email) {
//		    // Step 1: Check for existing valid OTP
//		    Optional<OtpVerification> existingOtp = otpRepo.findTopByEmailOrderByIdDesc(email);
//
//		    if (existingOtp.isPresent()) {
//		        OtpVerification latest = existingOtp.get();
//		        // If not verified and not expired, reuse it
//		        if (!latest.isVerified() && latest.getExpiryTime().isAfter(LocalDateTime.now())) {
//		            throw new RuntimeException("An OTP has already been sent and is still valid until: " +
//		                    latest.getExpiryTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
//		        }
//		    }

		    // Step 2: Generate new OTP
//		    String otp = String.format("%06d", new Random().nextInt(999999));
//		    LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);
//
//		    OtpVerification otpRecord = new OtpVerification();
//		    otpRecord.setEmail(email);
//		    otpRecord.setOtp(otp);
//		    otpRecord.setExpiryTime(expiry);
//		    otpRecord.setVerified(false);
//
//		    otpRepo.save(otpRecord);
//
//		    // Step 3: Format and send email with expiry time
//		    String formattedExpiry = expiry.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
//
//		    String body = "<p>Hello <strong>" + email + "</strong>,</p>"
//		            + "<p>Your OTP is: <b>" + otp + "</b></p>"
//		            + "<p><b>This OTP will expire at: " + formattedExpiry + "</b></p>"
//		            + "<br><p>If you did not request this, you can ignore this email.</p>"
//		            + "<p>Regards,<br/>Rytways Team</p>";
//
//		    sendEmail(email, "OTP Verification", body);
//		}
//
//    // 2. Verify OTP
//    public boolean verifyOtp(String email, String userInputOtp) {
//        OtpVerification otp = otpRepo.findTopByEmailOrderByIdDesc(email)
//                .orElseThrow(() -> new RuntimeException("OTP not found"));
//
//        if (otp.isVerified()) {
//            throw new RuntimeException("This email has already been verified.");
//        }
//
//        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("OTP expired");
//        }
//
//        if (!otp.getOtp().equals(userInputOtp)) {
//            return false;
//        }
//
//        otp.setVerified(true);
//        otpRepo.save(otp);
//        return true;
//    }


    // 3. Save Customer only after OTP is verified
//    @Transactional(rollbackFor = Exception.class)
//    public Users saveCustomerAfterOtpVerified(Users cust) throws UnsupportedEncodingException, MessagingException {
//        if (!verifyOtp(cust.getEmail(), cust.getOtp())) {
//            throw new RuntimeException("Invalid OTP");
//        }
//
//        String tempPass = cust.getPassword();
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String pswd = passwordEncoder.encode(tempPass);
//        cust.setPassword(pswd);
//
//        cust = userRepo.save(cust);
//
//        Optional<Roles> role = roleRepo.findById(cust.getRoleId());
////        if (role.isEmpty()) {
////            throw new RuntimeException("Invalid role ID: " + cust.getRoleId());
////        }
//        cust.setRole(role.get());
//
//        sendEmailClientPassword(cust.getEmail(), tempPass);
//        return cust;
//    }

    // 4. Send Password Email
    public void sendEmailClientPassword(String recipientEmail, String updatedPassword)
            throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(sender, "Rytways");
        helper.setTo(recipientEmail);

        String subject = "Here is your password";

        String content = "<p>Hello <strong>" + recipientEmail + "</strong>,</p>"
        	    + "<p>Thank you for verifying your email address.</p>"
        	    + "<p>Your account has been successfully verified. Below are your login credentials:</p>"
        	    + "<ul>"
        	    + "<li><strong>Username:</strong> " + recipientEmail + "</li>"
        	    + "<li><strong>Password:</strong> " + updatedPassword + "</li>"
        	    + "</ul>"
        	    + "<p>Please keep this information secure. For security reasons, we recommend you change your password after your first login.</p>"
        	    + "<br>"
        	    + "<p>Regards,<br><strong>Rytways Team</strong></p>";


        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    // 5. Reusable mail function
    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(sender, "Rytways");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
    
    
public List<LoadOptionsDto> loadUser() {
		
		List<Users> userList = userRepo.findAll();
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!userList.isEmpty()) {
			   for (Users userMster : userList) {
				   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

		            loadDto.setLabel(userMster.getUserName());
		            loadDto.setValue(userMster.getUserId());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}

public List<Users> getUserDetails(Users user) {
	
	Specification spec1 = UserMasterSpec.userNameLike(user.getUserName()); 
    Specification spec2 = UserMasterSpec.roleIdEqual(user.getRoleId()); 
	
    
    Specification<Users> spec = Specification.where(spec1).and(spec2);
    // Use Sort to specify sorting order
    Sort sort = Sort.by(Sort.Direction.ASC, "userId");

	return userRepo.findAll(spec);
	
}


public List<LoadOptionsStringDto> getUserUnitOptions(Integer userId) {
	
	 Optional<Users> userOpt = userRepo.findById(userId);

	    if (!userOpt.isPresent()) {
	        throw new IllegalArgumentException("Invalid user ID");
	    }

	    Users user = userOpt.get();
	    String machineNames = user.getMachineName(); 

	    if (machineNames == null || machineNames.trim().isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<LoadOptionsStringDto> unitOptions = Arrays.stream(machineNames.split(","))
	            .map(String::trim)
	            .filter(unit -> !unit.isEmpty())
	            .map(unit -> LoadOptionsStringDto.builder()
	                    .value(unit)
	                    .label(unit)
	                    .build())
	            .collect(Collectors.toList());

	    return unitOptions;
}
}