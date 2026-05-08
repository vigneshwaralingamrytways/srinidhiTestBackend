package com.rytways.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.FunctionWithProcessDTO;
import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.ActivityMaster;
import com.rytways.model.ActivityMasterFeatures;
import com.rytways.model.FunctionMaster;
import com.rytways.repository.ActivityMasterFeaturesRepo;
import com.rytways.repository.ActivityMasterRepo;
import com.rytways.repository.FunctionMasterRepo;

@Component
@Service
@Transactional
public class ActivityMasterService {

	@Autowired
	private ActivityMasterRepo activityMasterRepo;

	@Autowired
	private ActivityMasterFeaturesRepo actFeatureRepo;

	@Autowired
	private FunctionMasterRepo functionRepo;

	public ActivityMaster createActivity(ActivityMaster doc) {
		// TODO Auto-generated method stub
		return activityMasterRepo.save(doc);
	}

//	public List<FunctionMaster> getFunctionsByRole(Integer roleId) {
//	    // Fetch all activity-feature mappings for the given role
//	    List<ActivityMasterFeatures> featureList = actFeatureRepo.findByRoleIdOrderByActivityId(roleId);
//
//	    // Extract unique activity IDs
//	    List<Long> activityIds = featureList.stream()
//	        .map(ActivityMasterFeatures::getActivityId)
//	        .distinct()
//	        .collect(Collectors.toList());
//
//	    if (activityIds.isEmpty()) {
//	        return Collections.emptyList(); // No activities, return empty
//	    }
//
//	    // Fetch activities by their IDs
//	    List<ActivityMaster> activityList = activityMasterRepo.findByActivityIdIn(activityIds);
//
//	    // Extract unique function IDs from activities
//	    List<Long> functionIds = activityList.stream()
//	        .map(ActivityMaster::getFunctionId)
//	        .filter(Objects::nonNull)
//	        .distinct()
//	        .collect(Collectors.toList());
//
//	    if (functionIds.isEmpty()) {
//	        return Collections.emptyList(); // No functions, return empty
//	    }
//
//	    // Fetch and return functions by their IDs
//	    return functionRepo.findByFunctionIdIn(functionIds);
//	}

	public List<FunctionMaster> getFunctionsByRole(Integer roleId) {
		return functionRepo.findFunctionsByRoleId(roleId);
	}

	//Comment By Lokesh
//	public List<ActivityMaster> getActvityByFunctionAndUserRole(long functionId, Integer userId, Integer roleId,
//			int liveStatus) {
//
//		// Step 1: Get all menus for the module
//		List<ActivityMaster> allFunctons = activityMasterRepo.findByFunctionIdOrderByDisplaySeqNoAsc(functionId);
//
//		// If role is Admin (roleId == 1), return all activities
////        if (roleId != null && roleId == 1) {
////            return allFunctons;
////        }
//
//		return allFunctons.stream().filter(menu -> {
//			String display = menu.getUserType();
//
//			if ("ROLE_WISE".equalsIgnoreCase(display)) {
//				return actFeatureRepo.existsByActivityIdAndRoleIdAndActivityStatusGreaterThanEqual(menu.getActivityId(),
//						roleId, liveStatus);
//			} else if ("USER_WISE".equalsIgnoreCase(display)) {
//				return actFeatureRepo.existsByActivityIdAndRoleIdAndActivityStatusGreaterThanEqual(menu.getActivityId(),
//						userId, liveStatus);
//			} else if ("GENERAL".equalsIgnoreCase(display)) {
//				return actFeatureRepo.existsByActivityIdAndActivityStatusGreaterThanEqual(menu.getActivityId(),
//						liveStatus);
//			}
//			return false;
//		}).collect(Collectors.toList());
//	}
	
	public List<ActivityMaster> getActvityByFunctionAndUserRole(long functionId, Integer userId, Integer roleId,
			int liveStatus) {

		// Step 1: Get all menus for the module
		List<ActivityMaster> allFunctons = activityMasterRepo.findByFunctionIdOrderByDisplaySeqNoAsc(functionId);

		 return allFunctons.stream()
		            .filter(menu -> {
		                // ? check activity status only from ActivityMaster
		            	if (liveStatus >= 100) {
			            	if (menu.getActivityStatus() == null || menu.getActivityStatus() < liveStatus) {
			            	    return false;
			            	}
		            	}

		                String userType = menu.getUserType();

		                if ("ROLE_WISE".equalsIgnoreCase(userType)) {
		                    // only check role mapping, no status in feature
		                    return actFeatureRepo.existsByActivityIdAndRoleId(menu.getActivityId(), roleId);
		                } else if ("USER_WISE".equalsIgnoreCase(userType)) {
		                    // only check user mapping, no status in feature
		                    return actFeatureRepo.existsByActivityIdAndUserId(menu.getActivityId(), userId);
		                } else if ("GENERAL".equalsIgnoreCase(userType)) {
		                    // no mapping needed
		                    return true;
		                }
		                return false;
		            })
		            .collect(Collectors.toList());
	}

	public List<ActivityMaster> getActivityMaster() {
		// TODO Auto-generated method stub
		return activityMasterRepo.findAll();
	}

	public List<ActivityMaster> getActivityfindByFunctionId(ActivityMaster request) {
		// TODO Auto-generated method stub
		return activityMasterRepo.findByFunctionId(request.getFunctionId());
	}

	public ActivityMasterFeatures createActivityActivityMasterFeatures(ActivityMasterFeatures doc) {
		// TODO Auto-generated method stub
		return actFeatureRepo.save(doc);
	}

	public List<ActivityMasterFeatures> getFeatures(ActivityMasterFeatures request) {
		// TODO Auto-generated method stub
		return actFeatureRepo.findByActivityId(request.getActivityId());
	}

	public List<FunctionWithProcessDTO> getFunctionsWithProcesses() {
		List<Object[]> rows = functionRepo.findAllProcessesWithFunctions();
		List<FunctionWithProcessDTO> result = new ArrayList<>();
		int dummyFunctionIdCounter = -1; // negative unique IDs for dummy funcs

		for (Object[] row : rows) {
			Long processId = row[0] != null ? ((Number) row[0]).longValue() : null;
			String processName = (String) row[1];
			String processPath = (String) row[2];
			Long functionId = row[3] != null ? ((Number) row[3]).longValue() : null;
			String functionName = (String) row[4];
			String functionPath = (String) row[5];
			String desc = (String) row[6];

			if (functionId == null) {
				// Process with no function - create dummy function
				functionId = (long) dummyFunctionIdCounter--;
				functionName = "";
				functionPath = "";
			}

			FunctionWithProcessDTO dto = new FunctionWithProcessDTO();
			dto.setProcessId(processId);
			dto.setProcess(new FunctionWithProcessDTO.ProcessInfo(processName, processPath,desc));
			dto.setFunctionId(functionId);
			dto.setFunctionName(functionName);
			dto.setFunctionPath(functionPath);

			result.add(dto);
		}

		return result;
	}

	public ActivityMaster editActivityMaster(ActivityMaster doc) {

		doc.setFunction(null);

		doc = activityMasterRepo.save(doc);
		return doc;
	}

	public void deleteActivity(long activityId) throws Exception {

		boolean isUsed = actFeatureRepo.existsByActivityId(activityId);
		if (isUsed) {
			throw new Exception("Cannot delete Activity: it is in use by Acivity Feature Master.");
		}
		// Check if the record exists
		ActivityMaster act = activityMasterRepo.findById(activityId)
				.orElseThrow(() -> new Exception("Activity with ID " + activityId + " not found."));

		// Delete the DocumentUserMaster record
		activityMasterRepo.deleteById(activityId);

	}

	public List<LoadOptionsDto> loadType(ActivityMaster request) {

		List<ActivityMaster> typeList = activityMasterRepo.findByFunctionId(request.getFunctionId());
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		if (!typeList.isEmpty()) {
			for (ActivityMaster typeMaster : typeList) {
				LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

				loadDto.setLabel(typeMaster.getActivityName());
				loadDto.setValue(typeMaster.getActivityId());

				dtoList.add(loadDto); // Add each instance to the list
			}
		}
		return dtoList;
	}

	
	public List<LoadOptionsDto> loadTypes(ActivityMaster request) {

		List<ActivityMaster> typeList = activityMasterRepo.findAll();
		List<LoadOptionsDto> dtoList = new ArrayList<>();
		if (!typeList.isEmpty()) {
			for (ActivityMaster typeMaster : typeList) {
				LoadOptionsDto loadDto = new LoadOptionsDto(); // Move inside the loop

				loadDto.setLabel(typeMaster.getActivityName());
				loadDto.setValue(typeMaster.getActivityId());

				dtoList.add(loadDto); // Add each instance to the list
			}
		}
		return dtoList;
	}

	public ActivityMaster findActivityById(long activityId) {

		return activityMasterRepo.findById(activityId).orElse(null);
	}

	public ActivityMaster updateActivityMaster(ActivityMaster doc) {
		ActivityMaster act = activityMasterRepo.findById(doc.getActivityId()).orElseThrow(
				() -> new EntityNotFoundException("ActivityMaster not found with ID: " + doc.getActivityId()));

		act.setDevPath(doc.getDevPath());
		act.setFunction(null); // Confirm if this is required
		return activityMasterRepo.save(act);
	}

	public List<FunctionMaster> getFunctionsByRoleOrUser(Integer roleId, Integer userId,Long liveStatus) {
		// TODO Auto-generated method stub
		return functionRepo.findFunctionsByRoleOrUser(roleId, userId,liveStatus);
	}

}

//package com.rytways.service;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.rytways.dto.FunctionDTO;
//import com.rytways.dto.FunctionWithProcessDTO;
//import com.rytways.dto.ProcessWithFunctionsDTO;
//import com.rytways.model.ActivityMaster;
//import com.rytways.model.ActivityMasterFeatures;
//import com.rytways.model.FunctionMaster;
//import com.rytways.repository.ActivityMasterFeaturesRepo;
//import com.rytways.repository.ActivityMasterRepo;
//import com.rytways.repository.FunctionMasterRepo;
//
//@Component
//@Service
//@Transactional
//public class ActivityMasterService {
//
//	
//	@Autowired
//	private ActivityMasterRepo activityMasterRepo;
//	
//	@Autowired
//	private ActivityMasterFeaturesRepo actFeatureRepo;
//	
//	@Autowired
//	private FunctionMasterRepo functionRepo;
//
//	public ActivityMaster createActivity(ActivityMaster doc) {
//		// TODO Auto-generated method stub
//		return activityMasterRepo.save(doc);
//	}
//
////	public List<FunctionMaster> getFunctionsByRole(Integer roleId) {
////	    // Fetch all activity-feature mappings for the given role
////	    List<ActivityMasterFeatures> featureList = actFeatureRepo.findByRoleIdOrderByActivityId(roleId);
////
////	    // Extract unique activity IDs
////	    List<Long> activityIds = featureList.stream()
////	        .map(ActivityMasterFeatures::getActivityId)
////	        .distinct()
////	        .collect(Collectors.toList());
////
////	    if (activityIds.isEmpty()) {
////	        return Collections.emptyList(); // No activities, return empty
////	    }
////
////	    // Fetch activities by their IDs
////	    List<ActivityMaster> activityList = activityMasterRepo.findByActivityIdIn(activityIds);
////
////	    // Extract unique function IDs from activities
////	    List<Long> functionIds = activityList.stream()
////	        .map(ActivityMaster::getFunctionId)
////	        .filter(Objects::nonNull)
////	        .distinct()
////	        .collect(Collectors.toList());
////
////	    if (functionIds.isEmpty()) {
////	        return Collections.emptyList(); // No functions, return empty
////	    }
////
////	    // Fetch and return functions by their IDs
////	    return functionRepo.findByFunctionIdIn(functionIds);
////	}
//
//	
//	public List<FunctionMaster> getFunctionsByRole(Integer roleId) {
//	    return functionRepo.findFunctionsByRoleId(roleId);
//	}
//
//	public List<ActivityMaster> getActvityByFunctionAndUserRole(long functionId, Integer userId, Integer roleId) {
//		
//		
//		 // Step 1: Get all menus for the module
//        List<ActivityMaster> allFunctons = activityMasterRepo.findByFunctionId(functionId);
//        
//       
//        
//        return allFunctons.stream().filter(menu -> {
//            String display = menu.getUserType();
//
//            if ("ROLE_WISE".equalsIgnoreCase(display)) {
//                return actFeatureRepo.existsByActivityIdAndRoleId(menu.getActivityId(), roleId);
//            } else if ("USER_WISE".equalsIgnoreCase(display)) {
//                return actFeatureRepo.existsByActivityIdAndUserId(menu.getActivityId(), userId);
//            } else if ("GENERAL".equalsIgnoreCase(display)) {
//                return true;
//            }
//            return false;
//        }).collect(Collectors.toList());
//	}
//
//	public List<ActivityMaster> getActivityMaster() {
//		// TODO Auto-generated method stub
//		return activityMasterRepo.findAll();
//	}
//
//	public List<ActivityMaster> getActivityfindByFunctionId(ActivityMaster request) {
//		// TODO Auto-generated method stub
//		return activityMasterRepo.findByFunctionId(request.getFunctionId());
//	}
//
//	public ActivityMasterFeatures createActivityActivityMasterFeatures(ActivityMasterFeatures doc) {
//		// TODO Auto-generated method stub
//		return actFeatureRepo.save(doc);
//	}
//
//	public List<ActivityMasterFeatures> getFeatures(ActivityMasterFeatures request) {
//		// TODO Auto-generated method stub
//		return actFeatureRepo.findByActivityId(request.getActivityId());
//	}
//
//	
//
//	public List<FunctionWithProcessDTO> getFunctionsWithProcesses() {
//	    List<Object[]> rows = functionRepo.findAllProcessesWithFunctions();
//	    List<FunctionWithProcessDTO> result = new ArrayList<>();
//	    int dummyFunctionIdCounter = -1; // negative unique IDs for dummy funcs
//
//	    for (Object[] row : rows) {
//	    	Long processId = row[0] != null ? ((Number) row[0]).longValue() : null;
//	        String processName = (String) row[1];
//	        String processPath = (String) row[2];
//	        Long functionId =  row[3] != null ? ((Number) row[3]).longValue() : null;
//	        String functionName = (String) row[4];
//	        String functionPath = (String) row[5];
//
//	        if (functionId == null) {
//	            // Process with no function - create dummy function
//	            functionId = (long) dummyFunctionIdCounter--;
//	            functionName = "";
//	            functionPath = "";
//	        }
//
//	        FunctionWithProcessDTO dto = new FunctionWithProcessDTO();
//	        dto.setProcessId(processId);
//	        dto.setProcess(new FunctionWithProcessDTO.ProcessInfo(processName, processPath));
//	        dto.setFunctionId(functionId);
//	        dto.setFunctionName(functionName);
//	        dto.setFunctionPath(functionPath);
//
//	        result.add(dto);
//	    }
//
//	    return result;
//	}
//
//
//	
//	
//	
//}
