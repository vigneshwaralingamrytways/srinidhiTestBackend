package com.rytways.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.rytways.model.ActivityMaster;
import com.rytways.model.CompanyMaster;
import com.rytways.model.GateMaster;
import com.rytways.model.IssueSolution;
import com.rytways.model.IssueSolutionComments;
import com.rytways.model.ReleaseTrack;
import com.rytways.model.ReleaseTrackItems;
import com.rytways.model.UnitMaster;
import com.rytways.model.VisitorsPass;
import com.rytways.repository.ActivityMasterRepo;
import com.rytways.repository.CompanyMasterRepository;
import com.rytways.repository.GateMasterRepo;
import com.rytways.repository.IssueSolutionCommentsRepo;
import com.rytways.repository.IssueSolutionRepo;
import com.rytways.repository.ReleaseTrackItemsRepo;
import com.rytways.repository.UnitRepository;
import com.rytways.repository.UserRepository;
import com.rytways.service.ReportService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class TransactionServiceImpl implements ReportService {

	@Value("${companyImageFilePath}")
	private String companyLogo;

	@Value("${upload.path}")
	private String uploadPath;
	@Autowired
	private CompanyMasterRepository companyMasterRepository;

//	@Autowired
//	private PalletTransactionRepository palletTransactionRepository;
//	
//	@Autowired
//	private ReelTransactionRepository reelTransactionRepo;
//	
//	@Autowired
//	private PalletTransactionService palletTransactionService;
//	
//	@Autowired
//	private ReelTransacService reelTransacService;
//	
//	@Autowired
//	private StoresRequestRepo storesRequestRepo;
//	
//	@Autowired
//	private StoresRequestItemsRepo storesItemsRepo;

	@Autowired
	private ReleaseTrackItemsRepo releaseTrackItemsRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private GateMasterRepo gateMasterRepo;

//	@Autowired
//	private CompanyMasterRepository companyMasterRepository;
//	
//	@Autowired
//	private PoItemsRepository poItemsRepo;
//	
//	@Autowired
//	private PoMasterRepository poMasterRepo;
//
//	@Autowired
//	private PoApprovalRepository poApprovalRepo;
//	
//	@Autowired
//	private CompanyMasterRepository companyMasterRepo;
//	
//	@Autowired
//	private TermsAndConditionRepository termsRepo;
//	
//	@Autowired
//	private SupplierRepository supplierRepo;
//	
//	@Autowired
//	private ApprovalsHistoryRepo approvalhistRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ActivityMasterRepo activityMasterRepo;

	@Autowired
	private IssueSolutionRepo issueSolutionRepo;

	@Autowired
	private IssueSolutionCommentsRepo issueSolutionCommmentsRepo;

	JasperReport jasperReport;

//	   @Value("${jrxmlfilePathPalTransaction}")
//	    private String jrxmlfilePathPalTransaction;
//	   
//	   @Value("${jrxmlfilePathPalletCheckOut}")
//	    private String jrxmlfilePathPalletCheckOut;
//	    
//	    @Value("${pathofPalletTrasnsacpdfReport}")
//		private String pathofPalTransacpdfReport;
//	    
//	    @Value("${jrxmlfilePathRealTransaction}")
//	    private String jrxmlfilePathRealTransaction;
//	    
//	    @Value("${jrxmlfilePathRealCheckOutReport}")
//	    private String jrxmlfilePathRealCheckOutReport;
//	    
//	    @Value("${pathofrealTrasnsacpdfReport}")
//		private String pathofrealTrasnsacpdfReport;
//	    
//	    
//	    @Value("${jrxmlfilePathmaterial1}")
//	    private String jrxmlfilePathmaterial1;
//	    
//	    @Value("${pathofMaterialpdfReport}")
//		private String pathofMaterialpdfReport;
//	    
//	 	
//		@Value("${jrxmlfilePath1}")
//		private String filePath1;
//			
//		@Value("${jrxmlfilePath2}")
//		private String filePath2;
//		
//		@Value("${pathofpdfReport}")
//		private String pathToPDF;

	// public String exportReport(int poId) throws FileNotFoundException,
	// JRException {

//		@SuppressWarnings("deprecation")
//		public StreamingResponseBody exportReport(int poId) throws FileNotFoundException, JRException {
//			//String filePath = "/filedir/pugal/templates/VJP_Invoice_V1.6.jrxml";
//			
//			//local To Run - path details:
//			//String filePath="/home/pugalenthi/workspace/VJP/src/main/resources/templates/VJP_Invoice_V1.6.jrxml";
//			//String pathofpdfReport ="/home/pugalenthi/workspace/VJP/src/main/resources/static";
//			
//			//JasperReport jasperReport=null;
//			Optional<PoMaster> poMaster = poMasterRepo.findById(poId);
//			Optional<CompanyMaster> company = companyMasterRepo.findById(poMaster.get().getCompanyId());
//			
//			Optional<TermsAndCondition> termsOptional = termsRepo.findById(poMaster.get().getTermsAndConId());
//			Optional<SupplierMaster> supplierOptional = supplierRepo.findById(poMaster.get().getSupplierId());
//			
//			List<PoItems> poItemsList = poItemsRepo.findByPoId(poId);
//		//	Optional<PoApproval> poApproval = poApprovalRepo.findByPoId(poId);
//			List<ApprovalsHistory> approvalHists = approvalhistRepo.
//					findByAppTypeAndIdOrderByApprovalHistoryId(poMaster.get().getApprovalType(),(long) poMaster.get().getPoId());
//			
//			 double gst5 =poItemsList.stream().filter(o -> o.getGst()== 5).mapToDouble(o -> o.getGstAmt()).sum();
//			 
//			 double gst12 = poItemsList.stream().filter(o -> o.getGst()== 12).mapToDouble(o -> o.getGstAmt()).sum();
//			 
//			 double gst18 = poItemsList.stream().filter(o -> o.getGst()== 18).mapToDouble(o -> o.getGstAmt()).sum();
//			 
//			 double gst28 = poItemsList.stream().filter(o -> o.getGst()== 28).mapToDouble(o -> o.getGstAmt()).sum();
//				
//			 String symbol = null;
//			// symbol = "INR";
//			 if (poMaster.get().getCurrency() != null && poMaster.get().getCurrency().getCurrencyName() != null) {
//			     String currencyName = poMaster.get().getCurrency().getCurrencyName();
//			     System.out.println(currencyName);
//			     if ("Rupees".equals(currencyName)) {
//			         symbol = "";
//			     } else if ("USD".equals(currencyName)) {
//			         symbol = "USD"; 
//			     } else if ("Euro".equals(currencyName)) {
//			         symbol = "EURO"; 
//			     }else {
//			    	 symbol = "";
//			    	 }
//			     
//			     String symbole1 =" \u0930";
//			    
////System.out.println(symbole1 + " symbol ====> "+symbol);
////GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
////String[] fontNames = ge.getAvailableFontFamilyNames();
////for (String fontName : fontNames) {
////    System.out.println(fontName);
////}
//
//			 }
//			 for(PoItems poIt : poItemsList){
//				 poIt.setAmountBig(new BigDecimal(String.format("%.2f", poIt.getAmount())));
//				 poIt.setAmtString(NumberFormater.formatAsIndianRupee(poIt.getAmount()));
//				 poIt.setGstString(NumberFormater.formatAsIndianRupee(poIt.getGstAmt()));
//				 
//
//				 
//			//	 String formattedAmount = NumberFormater.formatAsIndianRupee(poIt.getUnitPrice()) + " " + symbol;
//				 poIt.setUnitString(NumberFormater.formatAsIndianRupee(poIt.getUnitPrice()));
//			//	 System.out.println("Formatted Amount: " + formattedAmount);
//System.out.println(poIt.getUnitString());
//				 poIt.setUomName(poIt.getUomDetails().getUomName());
//					if(poIt.getSpecs()!=null && !poIt.getSpecs().isEmpty()){
//						if(poIt.getMaterialId() !=1551 && poIt.getMaterialId() !=1487){
//						poIt.setItemName(poIt.getItemName()+"\n"+"Spec : "+poIt.getSpecs()+"");
//						}else {
//							poIt.setItemName(poIt.getSpecs());
//						}
//					}
//				}		
//			Map<String, Object> parameters = new HashMap<>();
//			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(poItemsList);
//
//			parameters.put("Created By", "Rytways");
//			parameters.put("PoItemdataset", dataSource);
//			parameters.put("currencySymbol", symbol);
//
//			if (poMaster.isPresent()) {
//				PoMaster poMaster1 = poMaster.get();
//				poMaster1.setCompany(company.get());
//				poMaster1.setSupplier(supplierOptional.get());
//				System.out.println("Inside poMaster" + poMaster1);
//
//				/*
//				 * if (poApproval.isPresent()) { System.out.println("poApproval:::::::" +
//				 * poApproval.toString()); poMaster1.setPoApproval(poApproval.get());
//				 * 
//				 * } else { poMaster1.setPoApproval(new PoApproval()); }
//				 */
//				parameters.put("poId", poMaster1.getPoId());
//				parameters.put("poNo", poMaster1.getPoNo());
//				parameters.put("agentName", poMaster1.getAgentDetails().getCategoryName());
//				parameters.put("countryOfOrigin", poMaster1.getCountryOfOrgin().getCategoryName());
//				parameters.put("portLoading", poMaster1.getPortOfLoading().getCategoryName());
//				parameters.put("portDelivery", poMaster1.getPortOfDelivery().getCategoryName());
//				parameters.put("shipmentSchedule", poMaster1.getShipmentDetails());
//				parameters.put("allowedMoisture", poMaster1.getAllowedMoisture());
//				parameters.put("outThrows", poMaster1.getOutThrows());
//				parameters.put("allowanceQuantity", poMaster1.getAllowanceInQuantity());
//				parameters.put("iecNo", poMaster1.getCompany().getIecNo());
//				
//				
//				//	parameters.put("costCenter", poMaster1.getCostCenterDetails().getCostName());
//				
//				parameters.put("costCenter", 
//					    poMaster1 != null && poMaster1.getCostCenterDetails() != null 
//					        ? poMaster1.getCostCenterDetails().getCostName() 
//					        : "N/A");
//
//				
//				LocalDate localDate = poMaster1.getPoDate();
//				
//				String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//				System.out.println("formattedpoDate" + formattedDate);
//				parameters.put("poDate", formattedDate);
//				parameters.put("supplierAddress", poMaster1.getSupplier().getSupplierAddress());
//				parameters.put("deliveryAddress", poMaster1.getDeliveryAddress());
//				parameters.put("supplierId", poMaster1.getSupplier().getSupplierId());
//				parameters.put("company", poMaster1.getCompany().getCompanyName());
////				parameters.put("", poMaster1.getIsAmendment());
//				if(poMaster1.getIsAmendment()==ActiveStatus.Yes) {
//					parameters.put("isAmendment", "1");
//				}else {
//					parameters.put("isAmendment", "2");
//				}
//				parameters.put("imageLocation",poMaster1.getCompany().getImageLocation());
//				/* 
//				 * // Assuming poMaster1.getTotalGst() returns a Float value
//				Float totalGst = poMaster1.getTotalGst();
//				BigDecimal cGst = BigDecimal.valueOf(totalGst.doubleValue() / 2);
//				parameters.put("cGst", cGst);
//
//				Float totalGst1 = poMaster1.getTotalGst();
//				BigDecimal sGst = BigDecimal.valueOf(totalGst1.doubleValue() / 2);
//				parameters.put("sGst", sGst);
//
//				Float totalGst2 = poMaster1.getTotalGst();
//				BigDecimal iGst = BigDecimal.valueOf(totalGst2.doubleValue());
//				parameters.put("iGst", iGst);
//
//				BigDecimal grossAmt = BigDecimal.valueOf(poMaster1.getGrossAmt().doubleValue());
//				parameters.put("grossAmt", grossAmt);
//				BigDecimal roundOff = BigDecimal.valueOf(poMaster1.getRoundOff().doubleValue());
//				parameters.put("roundOff", roundOff);
//
//				Float totalNet = poMaster1.getNetAmt();
//				BigDecimal netAmt = BigDecimal.valueOf(totalNet.doubleValue());
//				parameters.put("netAmt", netAmt);
//				System.out.println("####################################");
//
//				 * */
//			
//				// This method will convert the BigDecimal Number into comma Separated IndianRupees in String format.
//						
//				 
//		int displayGst=0;
//				 			
//		 if(poMaster1.getSupplier().getStateId()==33){
//				// Assuming poMaster1.getTotalGst() returns a Float value
//			//		poItemsList
//			    if(gst5>0){
//			    	displayGst=displayGst+1;
//			    	String cGst = NumberFormater.formatAsIndianRupee(gst5/ 2);
//					parameters.put("cGst", cGst);
//					String cGSTName = "CGST@2.5% :";
//					parameters.put("cGSTName", cGSTName);
//					
//					String sGSTName = "SGST@2.5% :";
//					String sGst = NumberFormater.formatAsIndianRupee(gst5 / 2);
//					parameters.put("sGst", sGst);
//					parameters.put("sGSTName", sGSTName);
//			    }
//			    
//			    if(gst12> 0){
//			    	if(displayGst ==0){
//			    		String cGst = NumberFormater.formatAsIndianRupee(gst12 / 2);
//			    		String cGSTName = "CGST@6% :";
//						parameters.put("cGst", cGst);
//						parameters.put("cGSTName", cGSTName);
//						
//						String sGSTName = "SGST@6% :";
//						String sGst = NumberFormater.formatAsIndianRupee(gst12 / 2);
//						parameters.put("sGst", sGst);
//						parameters.put("sGSTName", sGSTName);
//						
//						parameters.put("cGst1", null);
//						parameters.put("cGSTName1", "");
//						
//						parameters.put("sGst1", null);
//						parameters.put("sGSTName1", "");
//			    		
//			    	}else{
//			    		String cGSTName = "CGST@6% :";
//			    		String sGSTName = "SGST@6% :";
//			    		String cGst = NumberFormater.formatAsIndianRupee(gst12 / 2);
//						parameters.put("cGst1", cGst);
//						parameters.put("cGSTName1", cGSTName);
//						
//						String sGst = NumberFormater.formatAsIndianRupee(gst12 / 2);
//						parameters.put("sGst1", sGst);
//						parameters.put("sGSTName1", sGSTName);
//			    	}
//			    	displayGst=displayGst+1;
//			    	
//			    }
//			    
//			    
//			    if(gst18> 0){
//			    	if(displayGst ==0){
//			    		String cGSTName = "CGST@9% :";
//			    		String sGSTName = "SGST@9% :";
//			    		
//				    	String cGst = NumberFormater.formatAsIndianRupee(gst18 / 2);
//						parameters.put("cGst", cGst);
//						parameters.put("cGSTName", cGSTName);
//						
//						String sGst = NumberFormater.formatAsIndianRupee(gst18 / 2);
//						parameters.put("sGst", sGst);
//						parameters.put("sGSTName", sGSTName);
//						
//						parameters.put("cGst1", null);
//						parameters.put("cGSTName1", "");
//						
//						parameters.put("sGst1", null);
//						parameters.put("sGSTName1", "");
//			    		
//			    	}else if(displayGst==1){
//			    		String cGSTName = "CGST@9% :";
//			    		String sGSTName = "SGST@9% :";
//			    		
//			    		String cGst = NumberFormater.formatAsIndianRupee(gst18 / 2);
//						parameters.put("cGst1", cGst);
//						parameters.put("cGSTName1", cGSTName);
//						
//						String sGst = NumberFormater.formatAsIndianRupee(gst18 / 2);
//						parameters.put("sGst1", sGst);
//						parameters.put("sGSTName1", sGSTName);
//			    	}else if(displayGst>1){
//			    		String cGSTName = "CGST :";
//			    		String sGSTName = "SGST :";
//			    		Double totalGst = poMaster1.getTotalGst();
//						String cGst = NumberFormater.formatAsIndianRupee(totalGst / 2);
//						parameters.put("cGst", cGst);
//						parameters.put("cGSTName", cGSTName);
//						
//						String sGst = NumberFormater.formatAsIndianRupee(totalGst / 2);
//						parameters.put("sGst", sGst);
//						parameters.put("sGSTName", sGSTName);
//						
//						
//						parameters.put("cGst1", null);
//						parameters.put("cGSTName1", "");
//						
//						parameters.put("sGst1", null);
//						parameters.put("sGSTName1", "");
//			    	}else{
//			    		
//			    	}
//			    	displayGst=displayGst+1;
//			    }
//			    
//			    if(gst28> 0){
//			    	if(displayGst ==0){
//			    		String cGSTName = "CGST@14% :";
//			    		String sGSTName = "SGST@14% :";
//			    		String cGst = NumberFormater.formatAsIndianRupee(gst28 / 2);
//						parameters.put("cGst", cGst);
//						parameters.put("cGSTName", cGSTName);
//						
//						String sGst = NumberFormater.formatAsIndianRupee(gst28 / 2);
//						parameters.put("sGst", sGst);
//						parameters.put("sGSTName", sGSTName);
//						
//						parameters.put("cGst1", null);
//						parameters.put("cGSTName1", "");
//						
//						parameters.put("sGst1", null);
//						parameters.put("sGSTName1", "");
//			    		
//			    	}else if(displayGst==1){
//			    		String cGSTName = "CGST@14% :";
//			    		String sGSTName = "SGST@14% :";
//			    		
//			    		String cGst = NumberFormater.formatAsIndianRupee(gst28 / 2);
//						parameters.put("cGst1", cGst);
//						parameters.put("cGSTName1", cGSTName);
//						
//						String sGst = NumberFormater.formatAsIndianRupee(gst28 / 2);
//						parameters.put("sGst1", sGst);
//						parameters.put("sGSTName1", sGSTName);
//			    	}else if(displayGst>1){
//			    		String cGSTName = "CGST :";
//			    		String sGSTName = "SGST :";
//			    		
//			    		Double totalGst = poMaster1.getTotalGst();
//						String cGst = NumberFormater.formatAsIndianRupee(totalGst / 2);
//						parameters.put("cGst", cGst);
//						parameters.put("cGSTName", cGSTName);
//						
//						String sGst = NumberFormater.formatAsIndianRupee(totalGst / 2);
//						parameters.put("sGst", sGst);
//						parameters.put("sGSTName", sGSTName);
//						
//						
//						parameters.put("cGst1", null);
//						parameters.put("cGSTName1", "");
//						
//						parameters.put("sGst1", null);
//						parameters.put("sGSTName1", "");
//			    	}else{
//			    		displayGst=displayGst+1;
//			    	}
//			    	displayGst=displayGst+1;
//			    }
//				}else{
//
//					// Assuming poMaster1.getTotalGst() returns a Float value
//				//		poItemsList
//				    if(gst5>0){
//				    	displayGst=displayGst+1;
//				    	String cGst = NumberFormater.formatAsIndianRupee(gst5);
//						parameters.put("cGst", cGst);
//						parameters.put("cGSTName", "IGST@5% :");
//						
//						parameters.put("sGst", null);
//						parameters.put("sGSTName", "");
//						
//						parameters.put("cGst1", null);
//						parameters.put("cGSTName1", "");
//						
//						parameters.put("sGst1", null);
//						parameters.put("sGSTName1", "");
//				    }
//				    
//				    if(gst12> 0){
//				    	if(displayGst ==0){
//				    		String cGst = NumberFormater.formatAsIndianRupee(gst12);
//							parameters.put("cGst", cGst);
//							parameters.put("cGSTName", "IGST@12% :");
//							
//							parameters.put("sGst", null);
//							parameters.put("sGSTName", "");
//							
//							parameters.put("cGst1", null);
//							parameters.put("cGSTName1", "");
//							
//							parameters.put("sGst1", null);
//							parameters.put("sGSTName1", "");
//				    		
//				    	}else{
//				    		String cGst = NumberFormater.formatAsIndianRupee(gst12);
//							parameters.put("sGst", cGst);
//							parameters.put("sGSTName", "IGST@12% :");
//							
//							parameters.put("cGst1", null);
//							parameters.put("cGSTName1", "");
//							
//							parameters.put("sGst1", null);
//							parameters.put("sGSTName1", "");
//				    	}
//				    	displayGst=displayGst+1;
//				    	
//				    }
//				    
//				    
//				    if(gst18> 0){
//				    	if(displayGst ==0){
//					    	String cGst = NumberFormater.formatAsIndianRupee(gst18);
//							parameters.put("cGst", cGst);
//							parameters.put("cGSTName", "IGST@18% :");
//							
//							parameters.put("sGst", null);
//							parameters.put("sGSTName", "");
//							
//							parameters.put("cGst1", null);
//							parameters.put("cGSTName1", "");
//							
//							parameters.put("sGst1", null);
//							parameters.put("sGSTName1", "");
//				    		
//				    	}else if(displayGst==1){
//				    		
//							String sGst = NumberFormater.formatAsIndianRupee(gst18);
//							parameters.put("sGst", sGst);
//							parameters.put("sGSTName", "IGST@18% :");
//							
//							parameters.put("cGst1", null);
//							parameters.put("cGSTName1", "");
//							
//							parameters.put("sGst1", null);
//							parameters.put("sGSTName1", "");
//				    	}else if(displayGst>1){
//				    		Double totalGst = poMaster1.getTotalGst();
//							String cGst = NumberFormater.formatAsIndianRupee(totalGst);
//							parameters.put("cGst1", cGst);
//							parameters.put("cGSTName1", "IGST@18% :");
//													
//							parameters.put("sGst1", null);
//							parameters.put("sGSTName1", "");
//				    	}else{
//				    		
//				    	}
//				    	displayGst=displayGst+1;
//				    }
//				    
//				    if(gst28> 0){
//				    	if(displayGst ==0){
//				    		String cGst = NumberFormater.formatAsIndianRupee(gst28);
//							parameters.put("cGst", cGst);
//							parameters.put("cGSTName", "IGST@28% :");
//							
//							//BigDecimal sGst = BigDecimal.valueOf(gst28 / 2);
//							parameters.put("sGst", null);
//							parameters.put("sGSTName", "");
//							
//							parameters.put("cGst1", null);
//							parameters.put("cGSTName1", "");
//							
//							parameters.put("sGst1", null);
//							parameters.put("sGSTName1", "");
//				    		
//				    	}else if(displayGst==1){
//				    		String cGst = NumberFormater.formatAsIndianRupee(gst28);
//				    		
//				    		parameters.put("sGst", cGst);
//							parameters.put("sGSTName", "IGST@28% :");
//							
//							parameters.put("cGst1", null);
//							parameters.put("cGSTName1", "");
//							
//							parameters.put("sGst1", null);
//							parameters.put("sGSTName1", "");
//				    	}else if(displayGst==2){
//				    		
//				    		String cGst = NumberFormater.formatAsIndianRupee(gst28);
//				    		parameters.put("cGst1", cGst);
//							parameters.put("cGSTName1", "IGST@28% :");
//				    	}else if(displayGst==3){
//				    		
//				    		String cGst = NumberFormater.formatAsIndianRupee(gst28);
//				    		parameters.put("sGst1", cGst);
//							parameters.put("sGSTName1", "IGST@28% :");
//				    	}else{
//				    		displayGst=displayGst+1;
//				    	}
//				    	displayGst=displayGst+1;
//				    }
//				    
//			}
//				
//				
//				
//				
//				BigDecimal grossAmt = BigDecimal.valueOf(poMaster1.getGrossAmt());//-poMaster1.getRoundOff().doubleValue()
//				String grossString = NumberFormater.
//						formatAsIndianRupee(poMaster1.getGrossAmt());//-poMaster1.getRoundOff().doubleValue()
//				parameters.put("grossAmt", grossString);
//				
//				
//			//	BigDecimal roundOff = BigDecimal.valueOf(poMaster1.getRoundOff());
//				parameters.put("roundOff", NumberFormater.formatAsIndianRupee(poMaster1.getRoundOff()));
//				
//				parameters.put("supplierQuoteNo", poMaster1.getSupplierQuoteNo());
//				
//				parameters.put("rateBasis", poMaster1.getRateBasis());
//				
//				parameters.put("state", poMaster1.getSupplier().getState().getStateName());
//				System.out.println(poMaster1.getPoNo());
//				
//				// commended By Sakthi
//			/* 	if(poMaster1.getPoNo().equals("PO-23/24-00871")){
//					parameters.put("currency", "EUR");
//				}else {
//					parameters.put("currency", poMaster1.getSupplier().getCountry().getCurrency());
//				}
//				*/
//				parameters.put("currency", poMaster1.getCurrency().getCurrencyName());
//				
//				parameters.put("supGst", poMaster1.getSupplier().getGst());
//				
//				
//				/*Float totalNet = poMaster1.getNetAmt();
//				BigDecimal netAmt = BigDecimal.valueOf(totalNet.doubleValue());
//				parameters.put("netAmt", netAmt);*/
//				
//			//	Float totalNet=poMaster1.getNetAmt(); 
//				Double netAmtWR = poMaster1.getNetAmt() +poMaster1.getRoundOff();
//				/*+poMaster1.getRoundOff();*/
//		//		BigDecimal netAmt = BigDecimal.valueOf(totalNet.doubleValue());
//				BigDecimal netAmtW = BigDecimal.valueOf(netAmtWR);
//				
//				String formatIndianRupeesNw = AmountInWords.formatIndianRupees(netAmtW);
//			//	String formatIndianRupees = AmountInWords.formatIndianRupees(netAmt);
//				parameters.put("netAmt", NumberFormater.
//						formatAsIndianRupee(netAmtWR));
//				parameters.put("netAmtW", formatIndianRupeesNw);
//				//parameters.put("netAmt", netAmt);
//				System.out.println("####################################");
//				int approvalNumber =1;
//				for(ApprovalsHistory approvals:approvalHists  ) {
//					if(approvals.getIsApproved()==1) {
//						Optional<Users> user = userRepo.findById(approvals.getApprovedBy());
//						if(user.isPresent()) {
//							LocalDate l1 = approvals.getUpdatedOn().toLocalDate();
//							String poApproval1 = l1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//							
//							parameters.put("poApprovalType", approvals.getAppType());
//							parameters.put("poApproval"+approvalNumber, poApproval1);
//							parameters.put("approver"+approvalNumber, user.get().getPersonName());
////							parameters.put("approver"+approvalNumber+"Sign", user.get().getSign());
//							System.out.println(approvalNumber+"approvals.getAppType()"+approvals.getAppType());
//							if ("CEO Purchase Order".equals(approvals.getAppType()) && approvalNumber == 3) {
//							    parameters.put("approver2Sign", user.get().getSign());
//							} else if ("CEO Purchase Order".equals(approvals.getAppType()) && approvalNumber == 4) {
//							    parameters.put("approver3Sign", user.get().getSign());
//							} else {
//							    parameters.put("approver" + approvalNumber + "Sign", user.get().getSign());
//							}
//						}
//					}
//					approvalNumber++;
//				}
////				
////				if (poMaster1 != null && poMaster1.getPoApproval() != null
////						&& poMaster1.getPoApproval().getPoApproval1() != null) {
////					LocalDate l1 = poMaster1.getPoApproval().getPoApproval1().toLocalDate();
////					String poApproval1 = l1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
////					parameters.put("poApproval1", poApproval1);
////					parameters.put("approver1", poMaster1.getPoApproval().getApprover1());
////					parameters.put("approver1Sign", "/filedir/pugal/templates/sign3.png");
////				} else {
////					// Handle the case where poMaster1 or its properties are null
////					try {
////						throw new Exception("poApproval1--Null");
////					} catch (Exception e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}
////				}
////				parameters.put("approver2", poMaster1.getPoApproval().getApprover2());
////			//	parameters.put("approver2Sign", "/filedir/pugal/templates/sign3.png");
//				/*
//				 * LocalDate
//				 * l2=poMaster1.getPoApproval().getPoApproval2().toLocalDate();
//				 * String
//				 * poApproval2=l2.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//				 * parameters.put("poApproval2", poApproval2);
//				 */
//				parameters.put("termsAndConditions", poMaster1.getTermsAndCondition().getTermsAndConditions());
//				System.out.println("####################################111");
//				//String supplierGst=poMaster1.getSupplier().getGst();
//				String supplierName=poMaster1.getSupplier().getSupplierName();
//				parameters.put("gst", poMaster1.getSupplier().getGst());
//				supplierName=supplierName.concat("");
//				parameters.put("supplierName", supplierName);
//				parameters.put("docNo", poMaster1.getDocNo());
//				parameters.put("supplierAddress", poMaster1.getSupplier().getSupplierAddress());
//				System.out.println("####################################222");
//				parameters.put("companyName", poMaster1.getCompany().getCompanyName().split("-")[0]);
//				parameters.put("regAddress", poMaster1.getCompany().getRegisteredAddress());
//				parameters.put("factoryAddress", poMaster1.getCompany().getCompanyAddress());
//				parameters.put("gstin", poMaster1.getCompany().getGst());
//				parameters.put("cin", poMaster1.getCompany().getCin());
//				parameters.put("unit", poMaster1.getCompany().getCompanyName().split("-")[1]);
//				parameters.put("pan", poMaster1.getCompany().getPan());
//				System.out.println("####################################3333");
//				String paymentTerms = ""+poMaster1.getPaymentTerms().toString().replace("_"," ");
//				System.out.println(paymentTerms);
//				parameters.put("paymentTerms",poMaster1.getPaymentTerms().getPaymentTermsName());
//				//parameters.put("delivery", poMaster1.getDeliveryTerms().toString().replace("_"," "));
//				if (poMaster1.getDeliveryTerms() != null) {
//				    parameters.put("delivery", poMaster1.getDeliveryTerms().getDeliveryTermsName());
//				} else {
//				    parameters.put("delivery", ""); // or any default value you prefer
//				}
//				if(poMaster1.getSupplier().getCountry().getCountryId()==1) {
//				    parameters.put("dispatchThrough", poMaster1.getDispatchThrough());
//				} else {
//				    parameters.put("dispatchThrough", "N/A"); // or any default value you prefer
//				}
//				parameters.put("countryId", poMaster1.getSupplier().getCountry().getCountryId());
//
//				parameters.put("remarks", poMaster1.getPaymentRemarks());
//				parameters.put("notes", poMaster1.getNotes());
//				
//				Integer d1=netAmtWR.intValue();
//				String amountInWords=AmountInWords.convert(d1);
//				System.out.println("AmountInWords:::"+d1+":"+amountInWords);
//				parameters.put("amountInWords", amountInWords);
//				Integer getcatValue = poMaster1.getPoType().getcatValue();
//				
//					// To check PoType and print report based on category(PO,JO,SPO)
//						if(getcatValue!=null && getcatValue==1 || getcatValue==2)
//						{
//							String servicePO = getcatValue.toString();
//							System.out.println("servicePO::PoType"+servicePO);
//							parameters.put("servicePO", servicePO);
//							///SRIPATHI/src/main/resources/reports/VJP_Invoice_V1.3.3New.jrxml
//							if(poMaster1.getSupplier().getCountry().getCountryId()==1) {
//							jasperReport = JasperCompileManager.compileReport(getClass().
//									getResourceAsStream("/reports/Invoice_V1.3.3New.jrxml"));
//							}else {
//								if(poMaster1.getPoType()==PoType.Service_Purchase_Order) {
//									jasperReport = JasperCompileManager.compileReport(getClass().
//											getResourceAsStream("/reports/Invoice_V1.3.3New.jrxml"));
//								}else {
//									jasperReport = JasperCompileManager.compileReport(getClass().
//											getResourceAsStream("/reports/InvoiceExportNew.jrxml"));
//								}
//								
//							}
//						}else{
//							jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Invoice_V1.3.4.jrxml"));
//						}
//						if(poMaster1.getIsDraft()==ActiveStatus.Yes) {
//							parameters.put("isDraft", "2");
//						}else {
//							parameters.put("isDraft", "1");
//						}
//				
//			} else {
//
//				System.out.println("Requested PO Not Present!!!");
//
//			}
//
//			// load file and compile it
//			System.out.println("\n poItemsList:" + poItemsList.size());
//			
//
//			//JasperReport jasperReport = JasperCompileMan+ager.compileReport(filePath);
//		
//
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//			// Create a Date object representing the current time
//	        Date now = new Date();
//
//	        // Define the desired date format
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//
//	        // Format the date to a string representation
//	        String timestamp = dateFormat.format(now);
//	        
//	        timestamp="/report"+timestamp+".pdf";
//			
//		        
//	        String pdfFileName = getCurrentTimestamp();
//				
//			//generate PDF file on the specified path. 
//		//	JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFileName);
//			
//			//Save the generated PDF file path in PoMaster table.
//					
//				PoMaster updateFileName = poMaster.get();
//				updateFileName.setFileName(timestamp);
//				updateFileName.setCompany(null);
//				updateFileName.setPoApproval(null);
//				updateFileName.setSupplier(null);
//				updateFileName.setPaymentTerms(null);
//				updateFileName.setDeliveryTerms(null);
//				poMasterRepo.save(updateFileName);
//				System.out.println("File saved successfully");
//			
//			
//		//	return  timestamp;
//				 // Inside some other logic, for example:
//			//    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//
//			    // Create a ByteArrayOutputStream to hold the generated PDF
//			    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//			    // Export the JasperPrint object to PDF and write to the output stream
//			    JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
//
//			/* commanded By sakthi
//			     // Convert the ByteArrayOutputStream to a byte array
//			 
//			    byte[] pdfData = byteArrayOutputStream.toByteArray();
//
//			    // Return the StreamingResponseBody which writes the PDF to the response output stream
//			    return outputStream -> outputStream.write(pdfData);  
//			    */
//			    
//			    // Added By Sakthi
//			    
//			  
//			    	if(supplierOptional.get().getCountryId()!=1) {
//			        // Load the annexure PDF document
//			        try (InputStream annexureStream = getClass().getResourceAsStream("/reports/PO ANNEXURE - DA TERMS.pdf")) {
//			            if (annexureStream == null) {
//			                throw new FileNotFoundException("Annexure PDF file not found");
//			            }
//
//			            PDDocument annexureDocument = PDDocument.load(annexureStream);
//
//			            // Load the main report as a PDDocument from the ByteArrayOutputStream
//			            PDDocument mainReportDocument = PDDocument.load(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
//
//			            // Create temporary files to save both documents
//			            File tempMainReport = File.createTempFile("mainReport", ".pdf");
//			            File tempAnnexure = File.createTempFile("annexure", ".pdf");
//
//			            // Save the main report document to the temp file
//			            mainReportDocument.save(tempMainReport);
//			            annexureDocument.save(tempAnnexure);
//
//			            // Close the documents
//			            mainReportDocument.close();
//			            annexureDocument.close();
//
//			            // Specify the final merged PDF file path
//			            File mergedFile = File.createTempFile("mergedReport", ".pdf");
//
//			            // Initialize PDFMergerUtility
//			            PDFMergerUtility merger = new PDFMergerUtility();
//			            merger.addSource(tempMainReport); // Add main report file
//			            merger.addSource(tempAnnexure); // Add annexure file
//			            merger.setDestinationFileName(mergedFile.getAbsolutePath()); // Set destination file path
//
//			            // Merge the documents
//			            merger.mergeDocuments(null);
//
//			            // Optionally, read the merged document into a ByteArrayOutputStream
//			            ByteArrayOutputStream mergedOutputStream = new ByteArrayOutputStream();
//			            try (PDDocument mergedDocument = PDDocument.load(mergedFile)) {
//			                mergedDocument.save(mergedOutputStream);
//			            }
//
//			            // Optionally, delete temporary files after merging
//			            tempMainReport.delete();
//			            tempAnnexure.delete();
//			            mergedFile.delete(); // If you don't need the merged file on disk
//
//			            // Return the StreamingResponseBody which writes the merged PDF to the response output stream
//			            return outputStream -> outputStream.write(mergedOutputStream.toByteArray());
//			        
//
//			        } catch (IOException e) {
//			            // Handle IOException appropriately (log it, rethrow it, etc.)
//			            e.printStackTrace(); // or use a logger
//			            throw new RuntimeException("Failed to load or merge PDF documents", e);
//			        }
//			    	}else {
//			        	 byte[] pdfData = byteArrayOutputStream.toByteArray();
//
//						    // Return the StreamingResponseBody which writes the PDF to the response output stream
//						    return outputStream -> outputStream.write(pdfData);  
//			        }
//		}
//		
//		
//		/* 
//		 * This method will concat the path and filename with timestamp.pdf format
//		 *   
//		 * i.e /downloads/downloads/pugal/generated/report2023-07-06_18-22-14.pdf
//		 * 
//		 * */
//		
//		private String getCurrentTimestamp() {
//			
//			// Generated PDF file.
//			// String pathofpdfReport = "/downloads/downloads/pugal/generated";
//				
//				
//	        // Create a Date object representing the current time
//	        Date now = new Date();
//
//	        // Define the desired date format
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//
//	        // Format the date to a string representation
//	        String timestamp = dateFormat.format(now);
//	        
//	        timestamp="/report"+timestamp+".pdf";
//	        
//	        String path = pathToPDF.concat(timestamp);
//	                               
//	        // Return the timestamp
//	        return path;
//	    }
//
//	    
//	    
//	    @Override
//	    public String exportReportTransaction(PalletTransaction palletTransaction) throws FileNotFoundException, JRException {
//	    	
//	    	List<PalletTransaction> transactionList = palletTransactionService.getPalletTransaction(palletTransaction);
//	    	
//	    	String companyName="Sripathi Paper & Boards (P) Ltd";
//	        String address="2, Sukkiravarpatti, Anaikuttam, (Post), Sivakasi, Tamil Nadu, 626130, India";
//	        
//	        Map<String, Object> parameters = new HashMap<>(); 
//	        
//	        
//	        parameters.put("Created By", "Rytways");
//	        
//	        
//	        parameters.put("companyName", companyName);
//	        parameters.put("address", address);
//	        
//	        JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(transactionList);
//	        
//	        parameters.put("transactionDataSet", datasource);
//	        
//	        jasperReport = JasperCompileManager.compileReport(jrxmlfilePathPalTransaction);
//	        
//	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//	        
//	        
//	        Date now = new Date();
//
//	        // Define the desired date format
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//
//	        // Format the date to a string representation
//	        String timestamp = dateFormat.format(now);
//	        String filename="/PalletTransaction"+timestamp+".pdf";
//	        String path = pathofPalTransacpdfReport.concat(filename);
//		        
//	       // String pdfFileName = getCurrentTimestamp();
//				
//			//generate PDF file on the specified path. 
//			JasperExportManager.exportReportToPdfFile(jasperPrint, path);
//
//	        return filename;
//	    	
//	    }
//	    
//	    
//	    
//	    
//	    
//	    
//	    @Override
//	    public String exportPalCheckOutTransaction(CheckOutTransaction palletTransaction) throws FileNotFoundException, JRException {
//	    	
//	    	List<CheckOutTransaction> transactionList = palletTransactionService.getPalletChekoutTransaction(palletTransaction);
//	    	
//	    	
//	    	for (CheckOutTransaction transaction : transactionList) {
//	    	    // Assuming you have the getter and setter methods for reamWeight and bundleWeight
//	    	    Float calculatedWeight = ((transaction.getBundlesTaken() * transaction.getBundleWeight()) 
//	    	                             + (transaction.getReamsTaken() * transaction.getReamWeight()));
//	    	    transaction.setTotalWeight(Math.round(calculatedWeight * 100.0) / 100.0f);
//	    	}
//
//	    	
//	    	
//	    	String companyName="Sripathi Paper & Boards (P) Ltd";
//	        String address="2, Sukkiravarpatti, Anaikuttam, (Post), Sivakasi, Tamil Nadu, 626130, India";
//	        
//	        Map<String, Object> parameters = new HashMap<>(); 
//	        
//	        
//	        parameters.put("Created By", "Rytways");
//	        
//	        
//	        parameters.put("companyName", companyName);
//	        parameters.put("address", address);
//	        
//	        JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(transactionList);
//	        
//	        parameters.put("transactionDataSet", datasource);
//	        
//	        jasperReport = JasperCompileManager.compileReport(jrxmlfilePathPalletCheckOut);
//	        
//	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//	        
//	        
//	        Date now = new Date();
//
//	        // Define the desired date format
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//
//	        // Format the date to a string representation
//	        String timestamp = dateFormat.format(now);
//	        String filename="/PalletTransaction"+timestamp+".pdf";
//	        String path = pathofPalTransacpdfReport.concat(filename);
//		        
//	       // String pdfFileName = getCurrentTimestamp();
//				
//			//generate PDF file on the specified path. 
//			JasperExportManager.exportReportToPdfFile(jasperPrint, path);
//
//	        return filename;
//	    	
//	    }
//	    
//	    
//	    
//	    
//	    
//	    
//	    
//	    @Override
//	    public String exportReportRealTransaction(ReelTransaction reelTransaction) throws FileNotFoundException, JRException{
//	    	
//   List<ReelTransaction> transactionList = reelTransacService.getReelTransaction(reelTransaction);
//	    	
//	    	String companyName="Sripathi Paper & Boards (P) Ltd";
//	        String address="2, Sukkiravarpatti, Anaikuttam, (Post), Sivakasi, Tamil Nadu, 626130, India";
//	        
//	        Map<String, Object> parameters = new HashMap<>(); 
//	        
//	        
//	        parameters.put("Created By", "Rytways");
//	        
//	        
//	        parameters.put("companyName", companyName);
//	        parameters.put("address", address);
//	        
//	        JRBeanCollectionDataSource datasource=new JRBeanCollectionDataSource(transactionList);
//	        
//	        parameters.put("transactionDataSet", datasource);
//	        
//	      //  jasperReport = JasperCompileManager.compileReport(jrxmlfilePathRealCheckOutReport);
//	        
//	        jasperReport = JasperCompileManager.compileReport(
//	                transactionList.get(0).getReelStatusId() == 0L ? 
//	                jrxmlfilePathRealCheckOutReport : 
//	                jrxmlfilePathRealTransaction
//	            );
//	        
//	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//	        
//	        
//	        Date now = new Date();
//
//	        // Define the desired date format
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//
//	        // Format the date to a string representation
//	        String timestamp = dateFormat.format(now);
//	        String filename="/RealTransaction"+timestamp+".pdf";
//	        String path = pathofrealTrasnsacpdfReport.concat(filename);
//		        
//	       // String pdfFileName = getCurrentTimestamp();
//				
//			//generate PDF file on the specified path. 
//			JasperExportManager.exportReportToPdfFile(jasperPrint, path);
//
//	        return filename;
//	    }
//	    
//	    
//	    
//	    
//	    
//	    
//	    
//	    
//	    
//	    
//	    @Override
//		public String exportMaterialReport(int storesRequestId) throws FileNotFoundException, JRException {
//			
//			Optional<StoresRequest> storesRequestOptional = storesRequestRepo.findById(storesRequestId);
//			Optional<Users> approver = userRepository.findById(storesRequestOptional.get().getApprovedBy());
//			List<StoresRequestItems> schedules = storesItemsRepo.findstoreRequestItemsWithStoresRequestId(storesRequestId);
//			 //String userName = userRepository.findById(storesRequestId).map(Users::getUserName).orElse("");
//
//			CompanyMaster company=null;
//			Optional<CompanyMaster> companyMasterOptional = companyMasterRepository.findById(1);
//			if(companyMasterOptional.isPresent()) {
//				company=companyMasterOptional.get();
//			}else {
//				
//				throw new UsernameNotFoundException("Company Not Found");
//			}
//			
//			
//
//			//String materialImgLocation = this.materialImgLocation;
//			
//		
//	        String requestNo = storesRequestOptional.get().getRequestNo();
//	       // LocalDate requestDate=materialList.get().getRequestDate();
//	        String department = storesRequestOptional.get().getReqDepartment().getDepartmentName().toString();
//
//	     // Define your desired date format
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//	        // Format the LocalDate object using the formatter
//	        String requestDate = storesRequestOptional.get().getRequestDate().format(formatter);
//			
//			
//			
//			
//			Map<String, Object> parameters = new HashMap<>(); 
//			
//			
//			
//			 parameters.put("Created By", "Rytways");
//		        
//		        parameters.put("materialImgLocation", company.getImageLocation());
//		        parameters.put("companyName", company.getCompanyName());
//		        parameters.put("regAddress", company.getRegisteredAddress());
//		        parameters.put("factoryAddress", company.getCompanyAddress());
//		        parameters.put("requestNo", requestNo);
//		        parameters.put("requestDate", requestDate);
//		        parameters.put("department", department);
//		        parameters.put("requestedBy", storesRequestOptional.get().getUser().getUserName());
//		       
//	           if(approver.isPresent()) {
//	        	   parameters.put("approvedBy", approver.get().getPersonName());
//	                
//	           }else {
//	        	   parameters.put("approvedBy", "Admin");
//	           }
//	             parameters.put("storeIncharge", "");
//	            parameters.put("receiverSign", "");
//
//		    	//parameters.put("imageLocation",company.getImageLocation());
//		        
//		        
//		        List<Map<String, Object>> materialData = new ArrayList<>();
//		        for (StoresRequestItems item : schedules) {
//		            Map<String, Object> materialMap = new HashMap<>();
//		            materialMap.put("materialName", item.getMaterial().getMaterialName());
//		            materialMap.put("uom", item.getMaterial().getUom());
//		            materialMap.put("reqQty", item.getReqQty());
//		            materialMap.put("approvedQty", item.getApprovedQty());
//		            materialMap.put("issuedQty", item.getIssuedQty());
//		        
//
//		            // Add other fields if needed
//		            materialData.add(materialMap);
//		        }
//		     // Use materialData as the data source
//		        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(materialData);
//
//		        
//		        parameters.put("MaterialDataset", datasource);
//		        
//		        jasperReport = JasperCompileManager.compileReport(jrxmlfilePathmaterial1);
//		        
//		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//				        
//		        Date now = new Date();
//
//		        // Define the desired date format
//		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//
//		        // Format the date to a string representation
//		        String timestamp = dateFormat.format(now);
//		        String filename="/material"+timestamp+".pdf";
//		        String path = pathofMaterialpdfReport.concat(filename);
//			        
//		       // String pdfFileName = getCurrentTimestamp();
//					
//				//generate PDF file on the specified path. 
//				JasperExportManager.exportReportToPdfFile(jasperPrint, path);
//
//		        return filename;
//		
//		}

	@SuppressWarnings("deprecation")
	public StreamingResponseBody exportReleaseReport(ReleaseTrackItems release)
			throws FileNotFoundException, JRException {

//	    	

		ActivityMaster act = activityMasterRepo.findById(release.getActivityId()).orElse(null);

		// Convert it into a List
		List<ActivityMaster> activityData = new ArrayList<>();
		if (act != null) {
			activityData.add(act);
		}

		IssueSolution solution = issueSolutionRepo.findByIssueTypeAndFunctionActivityId(release.getIssueType(),
				release.getActivityId());

		// Create dummy data without a POJO
//	        List<Map<String, Object>> activityData = new ArrayList<>();
//
//	        Map<String, Object> row1 = new HashMap<>();
//	        row1.put("activityName", "Activity A");
//	        activityData.add(row1);
//
//	        Map<String, Object> row2 = new HashMap<>();
//	        row2.put("activityName", "Activity B");
//	        activityData.add(row2);
//
//	        Map<String, Object> row3 = new HashMap<>();
//	        row3.put("activityName", "Activity C");
//	        activityData.add(row3);

		List<IssueSolutionComments> list = new ArrayList<>();

		if (solution != null) {
			list = issueSolutionCommmentsRepo.findByIssueId(solution.getIssueId());
		}

		// Wrap the list in JRBeanCollectionDataSource
		JRBeanCollectionDataSource releaseDataSource = new JRBeanCollectionDataSource(activityData);

		JRBeanCollectionDataSource commentList = new JRBeanCollectionDataSource(list);

		jasperReport = JasperCompileManager
				.compileReport(getClass().getResourceAsStream("/reports/releasereport.jrxml"));

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("Created By", "Rytways");
//			parameters.put("companyName", "SRIPATHI PAPER AND BOARDS PVT LTD");
//			parameters.put("companyAddress", "SF No 22, 23, 89/1C, 89/2A, 89/4B, 89/5C, 89/6E, 90/2, Sukkiravarpatti,Namaskarithanpatti Village,Anaikuttam post, Sivakasi, Virudhunagar, Tamil Nadu, 626130");
//			String imagePath = getClass().getResource("/images/sign5.png").getPath();
//			parameters.put("imageLocation", imagePath);

		switch (release.getProjectName()) {
		case "SRESTHABACKEND":
			parameters.put("companyName", "SRESTHA BUILDERS PRIVATE LIMITED");
			parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//	            String imagePath = getClass().getResource("/images/Srestha.jpg").getPath();
			parameters.put("imageLocation", getClass().getResource("/images/Srestha.jpg").getPath());
			break;

		case "SRINDHIBACKEND":
			parameters.put("companyName", "SRINIDHI INVESTORS PRIVATE LIMITED");
			parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//	            String imagePath = getClass().getResource("/images/Srinidhi.jpg").getPath();
			parameters.put("imageLocation", getClass().getResource("/images/Srinidhi.jpg").getPath());
			break;

		case "VAFBACKEND":
			parameters.put("companyName", "VAF AERO - SYSTEMS PRIVATE LIMITED");
			parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//	            String imagePath = getClass().getResource("/images/VAF.jpg").getPath();
			parameters.put("imageLocation", getClass().getResource("/images/VAF.jpg").getPath());
			break;

		case "SRIPATHIBACKEND":
			parameters.put("companyName", "SRIPATHI PAPER AND BOARDS PRIVATE LIMITED");
			parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//	            String imagePath = getClass().getResource("/images/Sripathi.jpg").getPath();
			parameters.put("imageLocation", getClass().getResource("/images/Sripathi.jpg").getPath());
			break;

		default:
			throw new IllegalArgumentException("Project Name Not Found");
//	            	parameters.put("companyName", "");
//		            parameters.put("companyAddress", ""); // Trimmed for clarity
//
////		            String imagePath = getClass().getResource("/images/Sripathi.jpg").getPath();
//		            parameters.put("imageLocation", getClass().getResource("/images/Sripathi.jpg").getPath());

		}

		parameters.put("currentProblem", solution.getCurrentProblem() != null ? solution.getCurrentProblem() : "N/A");
		parameters.put("solution", solution.getSolution() != null ? solution.getSolution() : "N/A");
		parameters.put("dataPoints", solution.getDataPoints() != null ? solution.getDataPoints() : "N/A");
		parameters.put("controls", solution.getControls() != null ? solution.getControls() : "N/A");

		parameters.put("releaseDataSet", releaseDataSource);
		parameters.put("commentsDataSet", commentList);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// Export the JasperPrint object to PDF and write to the output stream
		JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);

		// Convert the ByteArrayOutputStream to a byte array

		byte[] pdfData = byteArrayOutputStream.toByteArray();

		// Return the StreamingResponseBody which writes the PDF to the response output
		// stream
		return outputStream -> outputStream.write(pdfData);
	}

	public StreamingResponseBody exportReleaseReportForAllActivities(ReleaseTrack releaseTrack)
			throws FileNotFoundException, JRException {
		List<ReleaseTrackItems> items = releaseTrackItemsRepo.findByReleaseTrackId(releaseTrack.getReleaseTrackId());

		List<JasperPrint> jasperPrints = new ArrayList<>();
//	        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/releasereport.jrxml"));

		boolean isFirst = true;

		for (ReleaseTrackItems item : items) {
			ActivityMaster act = activityMasterRepo.findById(item.getActivityId()).orElse(null);
			if (act == null)
				continue;

			JasperReport jasperReport = isFirst
					? JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/releasereport.jrxml"))
					: JasperCompileManager
							.compileReport(getClass().getResourceAsStream("/reports/releasesubreport.jrxml"));

			List<ActivityMaster> activityData = Collections.singletonList(act);

			IssueSolution solution = issueSolutionRepo.findByIssueTypeAndFunctionActivityId("Activity",
					item.getActivityId());
			List<IssueSolutionComments> commentList = (solution != null)
					? issueSolutionCommmentsRepo.findByIssueId(solution.getIssueId())
					: new ArrayList<>();

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("Created By", "Rytways");
			switch (releaseTrack.getProjectName()) {
			case "SRESTHABACKEND":
				parameters.put("companyName", "SRESTHA BUILDERS PRIVATE LIMITED");
				parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//		            String imagePath = getClass().getResource("/images/Srestha.jpg").getPath();
				parameters.put("imageLocation", getClass().getResource("/images/Srestha.jpg").getPath());
				break;

			case "SRINDHIBACKEND":
				parameters.put("companyName", "SRINIDHI INVESTORS PRIVATE LIMITED");
				parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//		            String imagePath = getClass().getResource("/images/Srinidhi.jpg").getPath();
				parameters.put("imageLocation", getClass().getResource("/images/Srinidhi.jpg").getPath());
				break;

			case "VAFBACKEND":
				parameters.put("companyName", "VAF AERO - SYSTEMS PRIVATE LIMITED");
				parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//		            String imagePath = getClass().getResource("/images/VAF.jpg").getPath();
				parameters.put("imageLocation", getClass().getResource("/images/VAF.jpg").getPath());
				break;

			case "SRIPATHIBACKEND":
				parameters.put("companyName", "SRIPATHI PAPER AND BOARDS PRIVATE LIMITED");
				parameters.put("companyAddress", "SF No 22, 23..."); // Trimmed for clarity

//		            String imagePath = getClass().getResource("/images/Sripathi.jpg").getPath();
				parameters.put("imageLocation", getClass().getResource("/images/Sripathi.jpg").getPath());
				break;

			default:
				throw new IllegalArgumentException("Project Name Not Found");
//		            	parameters.put("companyName", "");
//			            parameters.put("companyAddress", ""); // Trimmed for clarity
//
////			            String imagePath = getClass().getResource("/images/Sripathi.jpg").getPath();
//			            parameters.put("imageLocation", getClass().getResource("/images/Sripathi.jpg").getPath());

			}

			parameters.put("currentProblem",
					solution != null && solution.getCurrentProblem() != null ? solution.getCurrentProblem() : "N/A");
			parameters.put("solution",
					solution != null && solution.getSolution() != null ? solution.getSolution() : "N/A");
			parameters.put("dataPoints",
					solution != null && solution.getDataPoints() != null ? solution.getDataPoints() : "N/A");
			parameters.put("controls",
					solution != null && solution.getControls() != null ? solution.getControls() : "N/A");

			parameters.put("releaseDataSet", new JRBeanCollectionDataSource(activityData));
			parameters.put("commentsDataSet", new JRBeanCollectionDataSource(commentList));

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			jasperPrints.add(jasperPrint);

			isFirst = false;
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
		exporter.exportReport();

		byte[] pdfData = byteArrayOutputStream.toByteArray();
		return outputStream -> outputStream.write(pdfData);
	}

	public StreamingResponseBody generateReportForVisitorGatePass(VisitorsPass visitorPass) throws JRException {
		Optional<UnitMaster> unit = unitRepository.findByUnitName(visitorPass.getUnit());

		Optional<GateMaster> gate = gateMasterRepo.findById((long) visitorPass.getGateId());
		Optional<CompanyMaster> company = companyMasterRepository.findById(1);
		System.out.println("company  for print======" + company.get());
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (unit.isPresent())
			parameters.put("unitId", unit.get().getUnitName());
		if (gate.isPresent())
			parameters.put("gateNo", gate.get().getGateName());
		if (company.isPresent()) {
//				parameters.put("vafLogo", company.get().getImageLocation());
			parameters.put("vafLogo", companyLogo);
			parameters.put("companyName", company.get().getCompanyName());
			parameters.put("address", company.get().getCompanyAddress());
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//			String formattedDate = visitorPass.getInTime().format(formatter);
//				parameters.put("entryDate", formattedDate);
		if (visitorPass.getCreateBy() != null)
			parameters.put("createdBy", visitorPass.getCreateBy());
		else
			parameters.put("createdBy", visitorPass.getEnteredBy());

		parameters.put("department", visitorPass.getDepartment() != null ? visitorPass.getDepartment() : "");
		parameters.put("visitorName", visitorPass.getVisitorName() != null ? visitorPass.getVisitorName() : "");
		parameters.put("visitorCompanyName",
				visitorPass.getVisitorCompany() != null ? visitorPass.getVisitorCompany() : "");
		parameters.put("modeOfVehicle", visitorPass.getModeOfVehicle() != null ? visitorPass.getModeOfVehicle() : "");
		parameters.put("vehicleNo", visitorPass.getVehicleNo() != null ? visitorPass.getVehicleNo() : "");
		parameters.put("phoneNo", visitorPass.getPhoneNo() != null ? visitorPass.getPhoneNo() : "");
		parameters.put("emailId", visitorPass.getEmailId() != null ? visitorPass.getEmailId() : "");

		parameters.put("toWhomMeet", visitorPass.getToWhomMeet() != null ? visitorPass.getToWhomMeet() : "");
		parameters.put("toWhomMeetDepartment",
				visitorPass.getToWhomMeetDepartment() != null ? visitorPass.getToWhomMeetDepartment() : "");
		parameters.put("visitorType",
				visitorPass.getVisitorType() != null ? visitorPass.getVisitorType().getCategoryName() : "");
		parameters.put("foodReq", visitorPass.getFood() != null ? visitorPass.getFood() : "");
		parameters.put("noOfPerson", visitorPass.getNoOfPersons() > 0 ? visitorPass.getNoOfPersons() : 0);
		parameters.put("foodToken", visitorPass.getNumberTaken() != null ? visitorPass.getNumberTaken() : 0);
		parameters.put("visitorAddress", visitorPass.getAddress() != null ? visitorPass.getAddress() : "");
		parameters.put("visitorPassNo", visitorPass.getPassNo() != null ? visitorPass.getPassNo() : "");
		parameters.put("nameList", visitorPass.getNameList() != null ? visitorPass.getNameList() : "");
		File visitorImg = new File(uploadPath + visitorPass.getVisitorId() + ".png");
		if (visitorImg.exists())
			parameters.put("visitorImg", uploadPath + visitorPass.getVisitorId() + ".png");
		else
			parameters.put("visitorImg", uploadPath + "visitorImg.png");

		JasperReport report = JasperCompileManager
				.compileReport(getClass().getResourceAsStream("/reports/VisitorPass.jrxml"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//			JasperExportManager.exportReportToPdfFile(jasperPrint,"/home/lokesh/Gate Entry/SRIPATHI/src/main/resources/reports/rgpRep.pdf");
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
		byte[] pdfData = byteArrayOutputStream.toByteArray();
		return outputStream -> outputStream.write(pdfData);
	}

//		@Override
//		public StreamingResponseBody exportReleaseReport() throws FileNotFoundException, JRException {
//			// TODO Auto-generated method stub
//			return null;
//		}

}