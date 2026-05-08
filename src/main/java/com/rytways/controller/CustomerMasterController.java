package com.rytways.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.CustomerMaster;
import com.rytways.repository.CustomerRepository;
import com.rytways.service.CustomerMasterService;


@RestController
@RequestMapping("/customerMaster")
public class CustomerMasterController {

	@Autowired
	private CustomerRepository custMasterRepo;
	
	private final CustomerMasterService custMasterService;
	
	@Autowired
	public CustomerMasterController (CustomerMasterService custMasterService) {
		this.custMasterService = custMasterService;
	}
	

	
	@PostMapping("/create")
    public ResponseEntity<CustomerMaster> createCustomer(@RequestBody CustomerMaster customer){
	 
	 	String isSaved = "";
	 
	 	customer = custMasterService.saveCustDetails(customer);
       
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }
	
	
	
	@GetMapping("/customerMaster")
    public ResponseEntity <List<CustomerMaster>> listCustomers(){
	 
	 
	 
	 	List<CustomerMaster> customers=custMasterRepo.findAll();
       
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }

	 		 	
		@GetMapping("/loadCustomerOptions")
	    public ResponseEntity <List<LoadOptionsDto>> loadCustomerOptions(){
		 		 	
		 
		 	List<LoadOptionsDto> customers=custMasterService.loadCustomerOptions();
	       
	        return new ResponseEntity<>(customers,HttpStatus.OK);
	    } 
		@GetMapping("/loadDirectCustomerOptions")
	    public ResponseEntity <List<LoadOptionsDto>> loadDirectCustomerOptions(){
		 		 	
		 
		 	List<LoadOptionsDto> customers=custMasterService.loadDirectCustomerOptions();
	       
	        return new ResponseEntity<>(customers,HttpStatus.OK);
	    } 
		
		@GetMapping("/loadDealerOptions")
	    public ResponseEntity <List<LoadOptionsDto>> loadConsigneeOptions(){
		 		 	
		 
		 	List<LoadOptionsDto>consignees=custMasterService.loadConsigneeOptions();
	       
	        return new ResponseEntity<>(consignees,HttpStatus.OK);
	    } 
		

		
		@GetMapping("/listCustomerByConsignee/{id}")
	    public ResponseEntity <List<LoadOptionsDto>> listCustomerByConsigneeID(@PathVariable("id")Long id){
		 		 	
		    String customerType="'Customer";
		 	
		    List<LoadOptionsDto>consignees=custMasterService.listCustomerByConsigneeID(id,customerType);
	       
	        return new ResponseEntity<>(consignees,HttpStatus.OK);
	    } 
		

		@GetMapping("/{id}")
	    public ResponseEntity <CustomerMaster> findCustomerById(@PathVariable Long id){
		 		 	
			 Optional<CustomerMaster> findCustomerById = custMasterService.findCustomerById(id);

		        return findCustomerById
		                .map(customerMaster -> new ResponseEntity<>(customerMaster, HttpStatus.OK))
		                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    } 
		
		
		@GetMapping("/loadDealerAndDirectOptions")
	    public ResponseEntity <List<LoadOptionsDto>> loadDealerAndDirectCustOpt(){
		 		 	
		 
		 	List<LoadOptionsDto>consignees=custMasterService.loadDealerAndDirectCustOptions();
	       
	        return new ResponseEntity<>(consignees,HttpStatus.OK);
	    } 
		
		 @PostMapping("/listByDearlerAndDirectId")
		  public ResponseEntity<List<LoadOptionsDto>> listBydealer(@RequestBody CustomerMaster customerMaster) {
		      Long dealerId = customerMaster.getDealerId();
		      List<LoadOptionsDto> customerlsList = custMasterService.findByDealerId(dealerId);
		      return new ResponseEntity<>(customerlsList, HttpStatus.OK);
		  }


	/*	 @GetMapping("/listCustomerByConsignee/{id}") 
			public ResponseEntity<List<LoadOptionsDto>> listCustomerByConsigneeID(@PathVariable("id") Long id) {
			    List<String> customerTypes = Arrays.asList("Consignee", "Direct Customer");
			    List<LoadOptionsDto> consignees = custMasterService.listCustomerByConsigneeID(id, customerTypes);
			    
			    if (consignees.isEmpty()) {
			        System.out.println("No customers found for dealer ID: " + id);
			    }
			    return new ResponseEntity<>(consignees, HttpStatus.OK);
			} */


}
