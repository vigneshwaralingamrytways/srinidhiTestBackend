package com.rytways.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.CustomerMaster;
import com.rytways.model.Users;
import com.rytways.repository.CustomerRepository;
import com.rytways.repository.UserRepository;
import com.rytways.security.SecurityUtils;

@Component
@Service
@Transactional
public class CustomerMasterService {
	
	@Autowired
	private CustomerRepository custMasterRepo;
	

	@Autowired
	private UserRepository userRepository;

	public CustomerMaster saveCustDetails(CustomerMaster customer) {
        CustomerMaster savedCustomer = custMasterRepo.save(customer);
        if (savedCustomer.getCustomerType().equals("Dealer") || savedCustomer.getCustomerType().equals("Direct Customer")) {
            savedCustomer.setDealerId(savedCustomer.getId());
            savedCustomer = custMasterRepo.save(savedCustomer); // Update customer with dealerId
        }
        return savedCustomer;
    }
 
	public Optional<CustomerMaster> findCustomerById(Long id){
		
		return custMasterRepo.findById(id);
	} 

	
	public List<LoadOptionsDto> loadCustomerOptions() {
		String customerType="Customer";
		List<CustomerMaster> customersList = custMasterRepo.findAll();
		System.out.println("Customer List Size==>>"+customersList.size());
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!customersList.isEmpty()) {
			   for (CustomerMaster customers : customersList) {
				   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop
				   System.out.println("Customer Type ==>>"+customers.getCustomerType());
		            loadDto.setLabel(customers.getCustomerName());
		            loadDto.setValue(customers.getId());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}
	public List<LoadOptionsDto> loadDirectCustomerOptions() {

		String username = SecurityUtils.getCurrentUser().getUsername();
		Optional<Users> createdByOptinal = userRepository.findByUserName(username);
		Users createdBy = null;

		List<LoadOptionsDto> dtoList = new ArrayList<>();
		if (createdByOptinal.isPresent()) {
			createdBy = createdByOptinal.get();

		} else {

			throw new UsernameNotFoundException("User Not Found==>>" + username);
		}

		if ("ClientUser".equals(createdBy.getUserType())) {

			System.out.println("ClientUser.equals(createdBy.getUserType()==>>" + createdBy.getUserType());

			String custName = createdBy.getPersonName();

			System.out.println("ClientUser==>>userName==>>" + custName);

			Optional<CustomerMaster> findByNameOptional = custMasterRepo.findByName(custName);
			LoadOptionsDto loadDto = new LoadOptionsDto();
			CustomerMaster dealer = null;
			if (findByNameOptional.isPresent()) {

				dealer = findByNameOptional.get();
				loadDto.setLabel(dealer.getCustomerName());
				loadDto.setValue(dealer.getId());
				dtoList.add(loadDto);
				System.out.println("dtoList==>>" + dtoList.size());
				return dtoList;
			} else {
				throw new UsernameNotFoundException("Customer Not Found==>>" + custName);
			}
		} else {

			String customerType = "Direct Customer";
			List<CustomerMaster> customersList = custMasterRepo.findByCustomerType(customerType);
			System.out.println("Customer List Size==>>" + customersList.size());
			// List<LoadOptionsDto> dtoList = new ArrayList<>();
			if (!customersList.isEmpty()) {
				for (CustomerMaster customers : customersList) {
					LoadOptionsDto loadDto = new LoadOptionsDto(); // Move
																	// inside
																	// the loop
					System.out.println("Customer Type ==>>" + customers.getCustomerType());
					loadDto.setLabel(customers.getCustomerName());
					loadDto.setValue(customers.getId());

					dtoList.add(loadDto); // Add each instance to the list
				}
			}
		}

		return dtoList;

	}// Method ends here

	
public List<LoadOptionsDto> loadConsigneeOptions() {
		
		String customerType="Dealer";
		List<CustomerMaster> customersList = custMasterRepo.findByCustomerType(customerType);
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!customersList.isEmpty()) {
			   for (CustomerMaster consignees : customersList) {
				   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

		            loadDto.setLabel(consignees.getCustomerName());
		            loadDto.setValue(consignees.getId());

		            dtoList.add(loadDto); // Add each instance to the list
		        }
		 }
		return dtoList;
	}
public List<LoadOptionsDto> loadDealerAndDirectCustOptions() {

	String username = SecurityUtils.getCurrentUser().getUsername();
	Optional<Users> createdByOptinal = userRepository.findByUserName(username);
	Users createdBy = null;
//	Optional<Users> createdByOptinal = userRepository.findByEmail(email);

	List<LoadOptionsDto> dtoList = new ArrayList<>();
	if (createdByOptinal.isPresent()) {
		createdBy = createdByOptinal.get();
		if ("ClientUser".equals(createdBy.getUserType())) {
			
			System.out.println("ClientUser.equals(createdBy.getUserType()==>>"+createdBy.getUserType());

			String userName = createdBy.getPersonName();
			
			System.out.println("ClientUser==>>userName==>>"+userName);


			Optional<CustomerMaster> findByNameOptional = custMasterRepo.findByName(userName);
			LoadOptionsDto loadDto = new LoadOptionsDto();
			CustomerMaster dealer = null;
			if (findByNameOptional.isPresent()) {
			
				dealer = findByNameOptional.get();
				loadDto.setLabel(dealer.getCustomerName());
				loadDto.setValue(dealer.getId());
				dtoList.add(loadDto);
				System.out.println("dtoList==>>"+dtoList.size());
				return dtoList;
			}
		} else {

			List<String> customerTypes = Arrays.asList("Dealer", "Direct Customer");
			List<CustomerMaster> customersList = custMasterRepo.findByDealerAndDirectCust(customerTypes);
			System.out.println("Customer List Size==>>" + customersList.size());
			// List<LoadOptionsDto> dtoList = new ArrayList<>();
			for (CustomerMaster customers : customersList) {
				LoadOptionsDto loadDto = new LoadOptionsDto();
				System.out.println("Customer Type ==>>" + customers.getCustomerType());
				loadDto.setLabel(customers.getCustomerName());
				loadDto.setValue(customers.getId());

				dtoList.add(loadDto);
			}
		}
	}
	return dtoList;

}

public List<LoadOptionsDto> listCustomerByConsigneeID(Long id, String customerType) {
	
	List<CustomerMaster> listCustomerByConsigneeID = custMasterRepo.listCustomerByConsigneeID(id, customerType);
	List<LoadOptionsDto> dtoList = new ArrayList<>();
	 if (!listCustomerByConsigneeID.isEmpty()) {
		   for (CustomerMaster consignees : listCustomerByConsigneeID) {
			   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

	            loadDto.setLabel(consignees.getCustomerName());
	            loadDto.setValue(consignees.getId());

	            dtoList.add(loadDto); // Add each instance to the list
	        }
	 }
	return dtoList;
	
	
}

public List<LoadOptionsDto> findByDealerId(Long dealerId) {
	List<CustomerMaster> listCustomerBydealerID = custMasterRepo.findByDealerId(dealerId);
	List<LoadOptionsDto> dtoList = new ArrayList<>();
	 if (!listCustomerBydealerID.isEmpty()) {
		   for (CustomerMaster customers : listCustomerBydealerID) {
			   LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

	            loadDto.setLabel(customers.getCustomerName());
	            loadDto.setValue(customers.getId());

	            dtoList.add(loadDto); // Add each instance to the list
	        }
	 }
	return dtoList;
}
}
