package com.rytways.service;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rytways.dto.TokenDto;
//import com.rytways.model.ClientUsers;
import com.rytways.model.DepartmentMaster;
import com.rytways.model.UserDeptMap;
import com.rytways.model.Users;
//import com.rytways.repository.ClientUserRepository;
import com.rytways.repository.UserRepository;




@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private TokenDto tokenDto;
	
	@Autowired
	private UserRepository userRepo;

//	@Autowired
//	private ClientUserRepository clientUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Users> user = userRepo.findByUserName(username);
		if (user.isPresent()) {
			tokenDto.setRoleId(user.get().getRoleId());
			tokenDto.setUserId(user.get().getUserId());
			tokenDto.setTimeOut(1 * 60 * 1000);
			tokenDto.setPersonName(user.get().getPersonName());
			tokenDto.setMachineName(user.get().getMachineName());
			tokenDto.setUserType(user.get().getUserType());
			tokenDto.setDepartmentIds(user.get().getDepartments().stream().map(UserDeptMap::getDepartmentId)
					.map(String::valueOf).collect(Collectors.joining(",")));
			tokenDto.setDepartments(user.get().getDepartments().stream().map(UserDeptMap::getDepartment)
					.map(DepartmentMaster::getDepartmentName).collect(Collectors.joining(",")));

			return new User(user.get().getUserName(), user.get().getPassword(),
					new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(user.get().getRole().getRoleName()))));
		}
//		Optional<ClientUsers> clientUser = clientUserRepository.findByUserName(username);
//		if (clientUser.isPresent()) {
//			ClientUsers client = clientUser.get();
//			tokenDto.setPersonName(client.getPersonName());
//			tokenDto.setUserId(client.getCustomerId().intValue());
//			tokenDto.setTimeOut(1 * 60 * 1000);
//			tokenDto.setRoleId(client.getRoleId());
//			tokenDto.setUserType(client.getUserType());
//			String roleName = (client.getRole() != null) ? client.getRole().getRoleName() : "";
//			return new User(client.getUserName(), client.getPassword(),
//					new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(roleName))));
//		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}
}