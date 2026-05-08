package com.rytways.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.Categories.ActiveStatus;
import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.ApprovalAuthorities;
import com.rytways.model.ApprovalProcess;
import com.rytways.model.ApprovalsHistory;
import com.rytways.repository.ApprovalAuthoritiesRepo;
import com.rytways.repository.ApprovalProcessRepo;
import com.rytways.repository.ApprovalsHistoryRepo;


@Component
@Service
@Transactional
public class ApprovalProcessService {
	@Autowired
	private ApprovalProcessRepo approvalProcessRepo;
	
	@Autowired
	private ApprovalAuthoritiesRepo approvalAuthRepo;
	
	@Autowired
	private ApprovalsHistoryRepo approvalsHisRepo;
	
	
	
	public Map saveApprovalProcess(ApprovalProcess approvalProcess){
		String isSaved = "";
		int status = 0;
		for(ApprovalAuthorities authority : approvalProcess.getAuthorities()) {
			//authority.setRole(null);
		//	authority.setUser(null);
		//	authority.setSecondaryUser(null);
		}
		approvalProcess=approvalProcessRepo.save(approvalProcess);
        
        
        Map map = new HashMap();
        map.put("approvalProcess",approvalProcess);
        map.put("message",isSaved);
        map.put("status",status);
        Map retValues = new HashMap();;
        retValues.put("retValues",map);
        return retValues;
    }
	
	
public void createApprovalHistory(String approvalName,long id,int userId,int department) {
	Optional<ApprovalProcess> approvalProcess = approvalProcessRepo.findByProcessName(approvalName);
	if(approvalProcess.isPresent()) {
		int i =0;
		if(approvalProcess.get().getIncludeCreator()==ActiveStatus.Yes) {
		ApprovalsHistory approvalHistory = new ApprovalsHistory();
		approvalHistory.setAppType(approvalProcess.get().getProcessName());
		approvalHistory.setId(id);
		approvalHistory.setAuthorityId(0);
		approvalHistory.setShowApproval(0);
		approvalHistory.setIsApproved(1);
		approvalHistory.setApprovedBy(userId);
		approvalHistory.setDepartmentId(department);
		approvalsHisRepo.save(approvalHistory);
		}
		for(ApprovalAuthorities authority : approvalProcess.get().getAuthorities()) {
			ApprovalsHistory approvalHistory = new ApprovalsHistory();
			approvalHistory.setAppType(approvalProcess.get().getProcessName());
			approvalHistory.setId(id);
			approvalHistory.setAuthorityId(authority.getAuthorityId());
			if(approvalProcess.get().getApprovalType()=="Parallel" ) {
				approvalHistory.setShowApproval(1);
			}else {
				if(i==0) {
					approvalHistory.setShowApproval(1);
				}else {
					approvalHistory.setShowApproval(0);
				}
			}
			approvalHistory.setDepartmentId(department);
			approvalsHisRepo.save(approvalHistory);
			i++;
		}
	}
}
	
	
public Map updateApprovalProcess(ApprovalProcess approvalProcess){
		
		String isSaved = "";
		int status = 0;
		
		Optional<ApprovalProcess> results =  approvalProcessRepo.findById(approvalProcess.getApprovalProcessId()) ;
		
		ApprovalProcess approvalProcessFromDb=results.get();
		
		//Check for deleted delivery entities in updated approvalProcess
		System.out.println(approvalProcessFromDb.getAuthorities().size());
		for (ApprovalAuthorities deliFromDb : approvalProcessFromDb.getAuthorities()){
			Boolean isDeleted = true;
				for(ApprovalAuthorities deliupdated : approvalProcess.getAuthorities()){
					if(deliFromDb.getAuthorityId()==deliupdated.getAuthorityId()){
						isDeleted = false;
						break;
					}				
				}
				if(isDeleted){
					deliFromDb.setApprovalProcess(approvalProcessFromDb);
					approvalAuthRepo.delete(deliFromDb);
				}
		}
		
		//check for newly entered delivery schedules 
		System.out.println(approvalProcess.getAuthorities().size());
		for(ApprovalAuthorities deliupdated : approvalProcess.getAuthorities()){
			deliupdated.setUser(null);
			deliupdated.setSecondaryUser(null);
		//	deliupdated.setRole(null);
			deliupdated.setApprovalProcess(approvalProcess);
		}
		approvalProcess.setAuthorities(approvalProcess.getAuthorities());
		approvalProcess=approvalProcessRepo.save(approvalProcess);
        
        

        Map map = new HashMap();
        map.put("approvalProcess",approvalProcess);
        map.put("message",isSaved);
        map.put("status",status);
        Map retValues = new HashMap();;
        retValues.put("retValues",map);
        return retValues;
        
    }

public String createApporvalHistory(String apporoval,Long entryId,int userId,int deptId) {
	Optional<ApprovalProcess> approvalProcess = approvalProcessRepo.findByProcessName(apporoval);
	if(approvalProcess.isPresent()) {
		int i =0;
		if(approvalProcess.get().getIncludeCreator()==ActiveStatus.Yes) {
		ApprovalsHistory approvalHistory = new ApprovalsHistory();
		approvalHistory.setAppType(approvalProcess.get().getProcessName());
		approvalHistory.setId(entryId);
		approvalHistory.setAuthorityId(0);
		approvalHistory.setShowApproval(0);
		approvalHistory.setIsApproved(1);
		approvalHistory.setApprovedBy(userId);
		approvalHistory.setDepartmentId(deptId);
		approvalsHisRepo.save(approvalHistory);
		}
		for(ApprovalAuthorities authority : approvalProcess.get().getAuthorities()) {
			ApprovalsHistory approvalHistory = new ApprovalsHistory();
			approvalHistory.setAppType(approvalProcess.get().getProcessName());
			approvalHistory.setId(entryId);
			approvalHistory.setAuthorityId(authority.getAuthorityId());
			if(approvalProcess.get().getApprovalType()=="Parallel" ) {
				approvalHistory.setShowApproval(1);
			}else {
				if(i==0) {
					approvalHistory.setShowApproval(1);
				}else {
					approvalHistory.setShowApproval(0);
				}
			}
			approvalHistory.setDepartmentId(deptId);
			approvalsHisRepo.save(approvalHistory);
			i++;
		}
	}
	
	return "";
}
	
	
	
	
    
}
