package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rytways.model.CustomerMaster;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerMaster, Long> {

	 Optional<CustomerMaster> findById(int id);

	// @Query(value = "select * from customer_master where customer_type= :customerType", nativeQuery = true)
//	 List<CustomerMaster> findCustomerList(@Param("customerType")String customerType);
	 
	// @Query(value = "select * from customer_master where customer_type= :customerType", nativeQuery = true)
	// List<CustomerMaster> findConsigneeList(@Param("customerType")String customerType);

	 @Query("SELECT cm FROM CustomerMaster cm WHERE cm.customerType IN :customerTypes")
	 List<CustomerMaster> findByDealerAndDirectCust(@Param("customerTypes") List<String> customerTypes);
 
	 //Customer
	@Query(value = "select * from customer_master where customer_name= :customerName", nativeQuery = true)
	Optional<CustomerMaster> findByName(@Param("customerName")String customerName);
	
	@Query(value = "select * from customer_master where customer_type= :customerType and consignee_id= :consigneeId", nativeQuery = true)
	List<CustomerMaster> listCustomerByConsigneeID(@Param("consigneeId")Long consigneeId,@Param("customerType")String customerType);

	List<CustomerMaster> findByDealerId(Long dealerId);

	List<CustomerMaster> findByCustomerType(String customerType);

    
}