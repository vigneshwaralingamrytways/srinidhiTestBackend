package com.rytways.controller;


import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.LoadOptionIntegerDto;
import com.rytways.dto.LoadOptionsDto;
import com.rytways.dto.LoadOptionsStringDto;
import com.rytways.model.Roles;
import com.rytways.model.Users;
import com.rytways.repository.RoleRepository;
import com.rytways.repository.UserRepository;
import com.rytways.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	private UserRepository userRepo;
	
	
	@Autowired
	private RoleRepository rolesRepo;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	public ResponseEntity<Users> createUser(@RequestBody Users user) {

		String isSaved = "";

		user = userService.saveCustomer(user);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
//	 @PostMapping("/send-otp")
//	    public ResponseEntity<String> sendOtp(@RequestBody Users user) {
//	        userService.sendOtpToUser(user.getEmail());
//	        return ResponseEntity.ok("OTP sent to " + user.getEmail());
//	    }
//	 


	
	    // 2. Create user after OTP is verified
//	    @PostMapping("/createApiuser")
//	    public ResponseEntity<Users> createUserAfterOtp(
//	            @RequestBody Users user
//	            
//	    ) throws UnsupportedEncodingException, MessagingException {
//	        Users savedUser = userService.saveCustomerAfterOtpVerified(user);
//	        return new ResponseEntity<>(savedUser, HttpStatus.OK);
//	    }

	@GetMapping("/users")
	public ResponseEntity<List<Users>> listcusotmers() {

		List<Users> customers = userRepo.findAll();

		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@GetMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadUsers(){
	 		 	
	 
	 	List<LoadOptionsDto> loadFolderList=userService.loadUser();
       
        return new ResponseEntity<>(loadFolderList,HttpStatus.OK);
    } 
	
	@PostMapping("/loadUnitUsersOptions")
	public ResponseEntity<List<LoadOptionsStringDto>> loadUnitOptions(@RequestBody Users user) {
	    try {
	        List<LoadOptionsStringDto> unitOptions = userService.getUserUnitOptions(user.getUserId());
	        return ResponseEntity.ok(unitOptions);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(Collections.singletonList(
	                        LoadOptionsStringDto.builder()
	                                .value("")
	                                .label(e.getMessage())
	                                .build()
	                ));
	    }
	}

	@PostMapping("/userLoadOptions")
    public ResponseEntity <List<LoadOptionIntegerDto>> loadUsersList(@RequestBody Users user){
	 		 	
	 
	 //	List<LoadOptionsDto> loadFolderList=userService.loadUser();
		List<LoadOptionIntegerDto> users=userRepo.findUserByRole(user.getRoleId());
       
        return new ResponseEntity<>(users,HttpStatus.OK);
    } 
	
	 @PostMapping("/loadOptionsByRoles")
    public Map loadMaterialsOptionsByCategory(@RequestBody Users user){
		 
	     HashMap returnValues = new HashMap();
	 	//Page<MaterialMaster> custPage  = matService.findMaterials(pageNum);
	 	 
	 	// List<MaterialMaster> Materials = custPage.getContent();
	     
	   
	 	List<LoadOptionIntegerDto> users=userRepo.findUserByRole(user.getRoleId());
	 	
	 	Optional<Roles> role = rolesRepo.findById(user.getRoleId());
	 	
	 	  returnValues.put("loadOptions",users);
	 	  
	 	 returnValues.put("role",role.get());
			 
      
       return returnValues;
   }
	 
	 @PostMapping("/searchUsers")
	    public ResponseEntity<List<Users>> searchUsers(@RequestBody Users user){
		 		
		 		String isSaved = "";
				 	
			 	List<Users> options = userService.getUserDetails(user);
		       
		        return new ResponseEntity<>(options,HttpStatus.OK);

	}

}