package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.FunctionWithProcessDTO;
import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.ActivityMaster;
import com.rytways.model.ActivityMasterFeatures;
import com.rytways.model.FunctionMaster;
import com.rytways.repository.ActivityMasterRepo;
import com.rytways.service.ActivityMasterService;

@RestController
@RequestMapping("/activityMaster")
public class ActivityMasterController {

	
	@Autowired
	private ActivityMasterService activityMasterService;
	
	
	@Autowired
	private ActivityMasterRepo activityMasterRepo;

	
	@PostMapping("/create")
	public ResponseEntity <ActivityMaster> createFunction (@RequestBody ActivityMaster doc){
		
		doc=activityMasterService.createActivity(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	@PostMapping("/edit")
	public ResponseEntity <ActivityMaster> editActivityMaster (@RequestBody ActivityMaster doc){
		
		doc=activityMasterService.editActivityMaster(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	@PostMapping("/updateLink")
	public ResponseEntity <ActivityMaster> updateStatus (@RequestBody ActivityMaster doc){
		
		doc=activityMasterService.updateActivityMaster(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteActivity(@RequestBody ActivityMaster request) {
	    try {
	       
	    	activityMasterService.deleteActivity(request.getActivityId());
	        return new ResponseEntity<>("Function deleted successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}
	@PostMapping("/activityMaster")
	public ResponseEntity<List<ActivityMaster>> loadModules(@RequestBody ActivityMaster request) {
	    List<ActivityMaster> menus = activityMasterService.getActivityMaster();
	    return new ResponseEntity<>(menus, HttpStatus.OK);
	}
	
	@PostMapping("/findByFunctionId")
	public ResponseEntity<List<ActivityMaster>> loadfindByFunctionId(@RequestBody ActivityMaster request) {
	    List<ActivityMaster> menus = activityMasterService.getActivityfindByFunctionId(request);
	    return new ResponseEntity<>(menus, HttpStatus.OK);
	}
/* for rol activity	
	@PostMapping("/loadProcess")
	public ResponseEntity<List<FunctionMaster>> loadModules(@RequestBody ActivityMasterFeatures request) {
	    List<FunctionMaster> menus = activityMasterService.getFunctionsByRole(request.getRoleId());
	    return new ResponseEntity<>(menus, HttpStatus.OK);
	} */
	
	/* @PostMapping("/loadProcess") no need
	public ResponseEntity<List<ProcessWithFunctionsDTO>> loadProcessesWithFunctions(@RequestBody ActivityMasterFeatures request) {
	    List<ProcessWithFunctionsDTO> result = activityMasterService.getProcessesAndFunctionsByRole(request.getRoleId());
	    return ResponseEntity.ok(result);
	}

	*/
	
	
	  @PostMapping("/loadProcess")
	public ResponseEntity<List<FunctionWithProcessDTO>> loadFunctionProcess(@RequestBody ActivityMasterFeatures request) {
	    // Even if roleId is passed, we ignore it (you may remove it from frontend too later)
	    List<FunctionWithProcessDTO> data = activityMasterService.getFunctionsWithProcesses();
	    return ResponseEntity.ok(data);
	}

	@PostMapping("/getActivityByFunction")
    public List<ActivityMaster> getModuleMenus(@RequestBody ActivityMaster request) {
        return activityMasterService.getActvityByFunctionAndUserRole(request.getFunctionId(), request.getUserId(), request.getRoleId()
        		,request.getLiveStatus());
    }
	
	
	@PostMapping("/createActivityRole")
	public ResponseEntity <ActivityMasterFeatures> createActivity (@RequestBody ActivityMasterFeatures doc){
		
		doc=activityMasterService.createActivityActivityMasterFeatures(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	@PostMapping("/findByActivityId")
	public ResponseEntity<List<ActivityMasterFeatures>> loadAllRole(@RequestBody ActivityMasterFeatures request) {
	    List<ActivityMasterFeatures> menus = activityMasterService.getFeatures(request);
	    return new ResponseEntity<>(menus, HttpStatus.OK);
	}
	@PostMapping("/loadOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadType(@RequestBody ActivityMaster request) {
	 		 	
	 
	 	List<LoadOptionsDto> loadTypeList=activityMasterService.loadType(request);
       
        return new ResponseEntity<>(loadTypeList,HttpStatus.OK);
    } 
	@PostMapping("/loadActivityOptions")
    public ResponseEntity <List<LoadOptionsDto>> loadTypes(@RequestBody ActivityMaster request) {
	 		 	
	 
	 	List<LoadOptionsDto> loadTypeList=activityMasterService.loadType(request);
       
        return new ResponseEntity<>(loadTypeList,HttpStatus.OK);
    } 
	
	@PostMapping("/findActivityById")
	public ResponseEntity<?> findById(@RequestBody ActivityMaster request) {
	    try {
	        
	    	ActivityMaster process = activityMasterService.findActivityById(request.getActivityId());
	        if (process != null) {
	            return new ResponseEntity<>(process, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Process not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
//	@PostMapping("/loadProcess")
//	public ResponseEntity<List<FunctionMaster>> loadModules(@RequestBody ActivityMasterFeatures request) {
//	    List<FunctionMaster> menus = activityMasterService.getFunctionsByRoleOrUser(request.getRoleId(),request.getUserId(),request.getLiveStatus());
//	    return new ResponseEntity<>(menus, HttpStatus.OK);
//	} 

}
