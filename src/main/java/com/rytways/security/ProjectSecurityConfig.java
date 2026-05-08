package com.rytways.security;



import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rytways.service.CustomLogoutSuccessHandler;





@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private CustomLogoutSuccessHandler logoutHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder( new BCryptPasswordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		// We don't need CSRF for this example
		httpSecurity.csrf().disable().authorizeRequests().
		// dont authenticate this particular request
				antMatchers("/login/**").permitAll().
				/*	
					
					antMatchers(HttpMethod.GET,  "/regionMaster/regionMaster").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST') "
						+ "or hasRole('ROLE_SALESMANAGER') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_SALES') or hasRole('ROLE_FINANCE') "
						+ "or hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_HO') or hasRole('ROLE_MANAGEMENT')").
					
					*/
				
				
				// UserMaster API Access
				
				antMatchers(HttpMethod.POST,  "/users/create").access("hasRole('ROLE_ADMIN')").
				
				antMatchers(HttpMethod.GET,  "/users/users").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ " hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') ").
				
				// RoleMaster API Access
				
				antMatchers(HttpMethod.GET,  "/roles/loadOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_FINANCEMANAGER')").
				
				
				antMatchers(HttpMethod.POST,  "/roles/create").access("hasRole('ROLE_ADMIN')").
				
				antMatchers(HttpMethod.GET,  "/roles/roles").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_FINANCEMANAGER')").
				
				// DepartmentMaster API Access
								
//				antMatchers(HttpMethod.POST,  "/department/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCEMANAGER')").
//				
//				antMatchers(HttpMethod.GET,  "/department/loadOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
//						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
//						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
//						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') "
//						+ "or hasRole('ROLE_PURCHASE') or hasRole('ROLE_PURCHASEMANAGER') or hasRole('ROLE_STOREAPPROVER') or hasRole('ROLE_PURCHASE')  or hasRole('ROLE_MANAGER') or hasRole('ROLE_FINANCEMANAGER')").
//				
//				
//				antMatchers(HttpMethod.GET,  "/department/department").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
//						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES')  "
//						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
//						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_PURCHASE') ").
//				
				// CustomerMaster API Access
				
				antMatchers(HttpMethod.POST,  "/customerMaster/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_SALES') or hasRole('ROLE_SALESMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/customerMaster/{id}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_CLIENT')").
				
				antMatchers(HttpMethod.GET,  "/customerMaster/customerMaster").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_CLIENT')").
				
				antMatchers(HttpMethod.POST,  "/customerMaster/listByDearlerAndDirectId").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/customerMaster/loadDealerAndDirectOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT')"
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
			
				
				antMatchers(HttpMethod.GET,  "/customerMaster/loadDirectCustomerOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				
				antMatchers(HttpMethod.GET,  "/customerMaster/loadDealerOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_CLIENT')").
				
				antMatchers(HttpMethod.GET,  "/customerMaster/loadCustomerOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_CLIENT')").
				
			
				
				// RegionMaster API Access
								
				antMatchers(HttpMethod.POST,  "/regionMaster/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
				
				antMatchers(HttpMethod.GET,  "/regionMaster/regionMaster").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/regionMaster/loadOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				// ProductMaster API Access
								
				antMatchers(HttpMethod.POST,  "/productMaster/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_SALESMANAGER') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_FINANCEMANAGER')  or hasRole('ROLE_MANAGEMENT') or hasRole('HO')").
				
				antMatchers(HttpMethod.GET,  "/productMaster/productMaster").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/productMaster/listRegionRates/{productId}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/productMaster/listQualityByUnit/{unitName}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.POST,  "/productMaster/listRegionRates").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
				
				antMatchers(HttpMethod.GET,  "/productMaster/productsByQualityAndGsm").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/productMaster/{Id}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/productMaster/loadQualityOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				antMatchers(HttpMethod.GET,  "/productMaster/listByMachineName").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				/* UnitMaster API Access (Terms Upload) */
				// DiscountController API Access (Terms Upload for Domestic and Export)
				
				antMatchers(HttpMethod.POST,  "/discountMaster/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
				
				antMatchers(HttpMethod.POST,  "/discountMaster/upload").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_DATAANALYST')").
				
				antMatchers(HttpMethod.GET,  "/discountMaster/listAll").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
				
				antMatchers(HttpMethod.GET,  "/discountMaster/{Id}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
				
				/* UnitMaster API Access (Price Upload) */
				// RegionRateController
				
				antMatchers(HttpMethod.POST,  "/regionrates/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
				
				antMatchers(HttpMethod.POST,  "/regionrates/upload").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('HO') or hasRole('ROLE_DATAANALYST')").
				
				antMatchers(HttpMethod.GET,  "/regionrates/listAll").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
				antMatchers(HttpMethod.GET,  "/regionrates/{Id}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DATAANALYST')").
			
				
			//  OrderMaster API Access 
				
							antMatchers(HttpMethod.POST, "/api/orders/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALES') or hasRole('ROLE_SALESMANAGER') "
									+ "or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT') or hasRole('ROLE_EXPORTMANAGER')").
							
							
							antMatchers(HttpMethod.PUT, "/api/orders/update").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_EXPORTMANAGER')  "
									+ "or hasRole('ROLE_SALESMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('ROLE_FINANCEMANAGER') or hasRole('HO') or hasRole('ROLE_CLIENT')").
						
							
							antMatchers(HttpMethod.PUT, "/api/orders/updatestatusandremarks").access("hasRole('ROLE_ADMIN')  "
									+ "or hasRole('ROLE_SALESMANAGER') or hasRole('ROLE_SALES') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT')").
							
							antMatchers(HttpMethod.POST, "/api/orders/all").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
									+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') or "
									+ "hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or "
									+ "hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_CLIENT')").
						
							antMatchers(HttpMethod.GET, "/api/orders/{orderId}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
									+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
									+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT')"
									+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
							
							antMatchers(HttpMethod.POST, "/api/orders/searchOrderDetails").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
									+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
									+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT')"
									+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
							
							antMatchers(HttpMethod.POST, "/api/orders/getOrdersByMachineNames").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
									+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
									+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT')"
									+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
							
				//Approvals API Access 
				
				antMatchers(HttpMethod.POST, "/approval/create").access("hasRole('ROLE_SALESMANAGER') or hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGEMENT') or hasRole('HO')")

				//PDF Reports API Access (Domestic) 
				.antMatchers(HttpMethod.POST, "/approval/report").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EMAILCOORDINATOR') "
						+ "or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER')").
				
				//PDF Reports API Access (Export)
				
				antMatchers(HttpMethod.POST, "/approval/export-report").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_EMAILCOORDINATOR')").
				
				// approval list in grid
				antMatchers(HttpMethod.POST, "/approval/approvalsList").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_EMAILCOORDINATOR')").
			
				
				antMatchers(HttpMethod.POST, "/approval/approvalLoadOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_SALES') "
						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_EMAILCOORDINATOR')")
				
				
				//UnitMaster API Access 
				
//				antMatchers(HttpMethod.POST, "/unitmaster/create").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PURCHASEMANAGER')").
//				
//				antMatchers(HttpMethod.GET, "/unitmaster/loadOptions").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
//						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_SALES') "
//						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_CLIENT') "
//						+ "or hasRole('ROLE_PURCHASE') or hasRole('ROLE_PURCHASEMANAGER') or hasRole('ROLE_STOREAPPROVER') or hasRole('ROLE_MANAGER')  or hasRole('ROLE_SUPERVISOR') or hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_PURCHASEMANAGER')").
//				
//				
//				antMatchers(HttpMethod.GET, "/unitmaster/unitMaster").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SALESMANAGER') or "
//						+ "hasRole('ROLE_FINANCEMANAGER') or hasRole('ROLE_MANAGEMENT') or hasRole('HO') or hasRole('ROLE_DATAANALYST') or hasRole('ROLE_SALES') "
//						+ "or hasRole('ROLE_FINANCE') or hasRole('ROLE_EXPORT') or hasRole('ROLE_EXPORTMANAGER') or hasRole('ROLE_EMAILCOORDINATOR') or hasRole('ROLE_FINANCEMANAGER')")
//				
				
		// Full Access No Authorization
				
				 .antMatchers("loadMenu/**").permitAll()
				 .antMatchers("country/**").permitAll()
			//	.antMatchers("customerMaster/**").permitAll()
				.antMatchers("customerSegment/**").permitAll()
				.antMatchers("dipatchDetails/**").permitAll()
				.antMatchers("machineAllocation/**").permitAll()
				.antMatchers("orderDetails/**").permitAll()
				.antMatchers("api/orderItems/**").permitAll().

				/*
				 * antMatchers("api/orders/create").hasAnyRole("admin","EmailCoordinator").
				 * antMatchers("api/orders/update").hasAnyRole("admin").
				 * antMatchers("api/orders/all").hasAnyRole("admin","EmailCoordinator","Sales",
				 * "SalesManager").
				 * antMatchers("api/orders/{orderId}").hasAnyRole("admin","EmailCoordinator",
				 * "Sales","SalesManager").
				 * antMatchers("api/orders/updatecommentsandccdate").hasAnyRole("admin","Sales",
				 * "SalesManager").
				 * antMatchers("api/orders/updatestatusandremarks").hasAnyRole("admin","Sales",
				 * "SalesManager").
				 */
				antMatchers("ordNotStarted/**")
				.permitAll().antMatchers("paymentTerms/**").permitAll()
			//	.antMatchers("productMaster/**").permitAll()
			//	.antMatchers("regionMaster/**").permitAll()
			//	.antMatchers("unitmaster/**").permitAll()
			//	.antMatchers("approval/**").permitAll()
			//	.antMatchers("discountMaster/**").permitAll()
				.antMatchers("termsandconditions/**").permitAll()
			//	.antMatchers("regionrates/**").permitAll()
			//	.antMatchers("roles/**").permitAll().
				.antMatchers("pallet/**").permitAll()
				.antMatchers("rack/**").permitAll()
				.antMatchers("zoneMaster/**").permitAll()
				.antMatchers("palletTransaction/**").permitAll()
				.antMatchers("reelTransaction/**").permitAll()
				.antMatchers("jasperReports/**").permitAll()
				
				.antMatchers("palletStatus/**").permitAll()
				.antMatchers("rackStatus/**").permitAll()
				.antMatchers("transacStatus/**").permitAll()
				.antMatchers("locationMaster/**").permitAll()
				.antMatchers("documentTransaction/**").permitAll()
				.antMatchers("documentTypeMaster/**").permitAll()
				.antMatchers("folderMaster/**").permitAll()
				.antMatchers("subFolderMaster/**").permitAll()
				.antMatchers("formConfigMaster/**").permitAll()
				.antMatchers("docUserMaster/**").permitAll()

				// Srinidhi api migration
				.antMatchers("storesRequest/**").permitAll()
				.antMatchers("materialCategory/**").permitAll()
				.antMatchers("material/**").permitAll()
				.antMatchers("matrialPrItems/**").permitAll()
				.antMatchers("storesReqItems/**").permitAll()
				.antMatchers("materialPr/**").permitAll()
				.antMatchers("costCenter/**").permitAll()
				.antMatchers("grnEntry/**").permitAll()
				.antMatchers("supplier/**").permitAll()
				.antMatchers("company/**").permitAll()
				.antMatchers("poMaster/**").permitAll()
				.antMatchers("deliveryTerms/**").permitAll()
				.antMatchers("uomMaster/**").permitAll()
				.antMatchers("terms/**").permitAll()
				.antMatchers("bankAccs/**").permitAll()
				.antMatchers("tdsSlab/**").permitAll()
				.antMatchers("poQuotes/**").permitAll()
				.antMatchers("poAdvance/**").permitAll()
				.antMatchers("invoice/**").permitAll()
				.antMatchers("payments/**").permitAll()
				.antMatchers("docsUpload/**").permitAll()
				.antMatchers("invoicePo/**").permitAll()
				.antMatchers("poItems/**").permitAll()
				.antMatchers("gateEntry/**").permitAll()
				.antMatchers("materialInward/**").permitAll()
				.antMatchers("gstMaster/**").permitAll()
				.antMatchers("damagedGoods/**").permitAll()
				.antMatchers("userDepMap/**").permitAll()
				.antMatchers("department/**").permitAll()
				.antMatchers("unitmaster/**").permitAll()
				.antMatchers("budgetMonth/**").permitAll()
				.antMatchers("moduleMenu/**").permitAll()
				.antMatchers("moduleProcess/**").permitAll()
				.antMatchers("customerMasterStatus/**").permitAll()
				.antMatchers("releaseTrack/**").permitAll()
				.antMatchers("releaseTrackItem/**").permitAll()
				.antMatchers("bugUpdate/**").permitAll()
				.antMatchers("/commentStatus/**").permitAll()
				
				.antMatchers("commentType/**").permitAll()
				.antMatchers("queryDoc/**").permitAll()
				.antMatchers("queryStatus/**").permitAll()
			//	.antMatchers("users/**").permitAll()
				.antMatchers("/reset-password/**").permitAll().

				/*
				 * and().authorizeRequests(). antMatchers("api/orders/**")
				 */
				anyRequest().authenticated().

				and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).logout()
				.logoutUrl("/logout") // URL for logout
				.addLogoutHandler(logoutHandler) // Set the custom logout success handler
				.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
				.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();

		// Add a filter to validate the tokens with every request
		// httpSecurity.addFilterBefore(jwtRequestFilter,
		// UsernamePasswordAuthenticationFilter.class);

	}
}