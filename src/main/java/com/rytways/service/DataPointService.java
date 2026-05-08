package com.rytways.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.ActivityMaster;
import com.rytways.model.DataPointModel;
import com.rytways.model.FunctionMaster;
import com.rytways.model.ProcessMaster;
import com.rytways.repository.ActivityMasterRepo;
import com.rytways.repository.DataPointRepo;
import com.rytways.repository.FunctionMasterRepo;
import com.rytways.repository.ProcessMasterRepo;

@Service
@Transactional
public class DataPointService {

	@Autowired
	private DataPointRepo dataPointRepo;

	@Autowired
	private ActivityMasterRepo activityMasterRepo;

	@Autowired
	private FunctionMasterRepo functionRepo;

	@Autowired
	private ProcessMasterRepo processRepo;

	// Create new DataPoint
	public DataPointModel saveDataPoint(DataPointModel dataPoint) {
		// First save the main DataPointModel
		DataPointModel saved = dataPointRepo.save(dataPoint);

		// Then check type and fetch respective master entity
		if (saved.getType() != null) {
			String type = saved.getType().trim().toUpperCase();

			
			 if ("PROCESS".equals(type) &&
			            dataPoint.getProcessId() != null &&
			            dataPoint.getDataId() != null &&
			            dataPoint.getProcessId().equals(dataPoint.getDataId())) {
			            throw new IllegalArgumentException("A process cannot be mapped to itself.");
			        }

			        if ("FUNCTION".equals(type) &&
			            dataPoint.getFunctionId() != null &&
			            dataPoint.getDataId() != null &&
			            dataPoint.getFunctionId().equals(dataPoint.getDataId())) {
			            throw new IllegalArgumentException("A function cannot be mapped to itself.");
			        }

//			        if ("ACTIVITY".equals(type) &&
//			            dataPoint.getActivityId() != null &&
//			            dataPoint.getDataId() != null &&
//			            dataPoint.getActivityId().equals(dataPoint.getDataId())) {
//			            throw new IllegalArgumentException("An activity cannot be mapped to itself.");
//			        }
			    
			        
			        boolean exists = false;

			        if ("PROCESS".equals(type) &&
			            dataPoint.getDataId() != null &&
			            dataPoint.getProcessId() != null) {

			            // Direct check (e.g., 1 -> 2)
			            boolean directExists = dataPointRepo.existsByDataIdAndProcessIdAndType(
			                    dataPoint.getDataId(), dataPoint.getProcessId(), type);

			            // Reverse check (e.g., 2 -> 1)
			            boolean reverseExists = dataPointRepo.existsByDataIdAndProcessIdAndType(
			                    dataPoint.getProcessId(), dataPoint.getDataId(), type);

			            exists = directExists || reverseExists;
			        }

			        if ("FUNCTION".equals(type) &&
			            dataPoint.getDataId() != null &&
			            dataPoint.getFunctionId() != null) {

			            // Direct check (e.g., 5 -> 6)
			            boolean directExists = dataPointRepo.existsByDataIdAndFunctionIdAndType(
			                    dataPoint.getDataId(), dataPoint.getFunctionId(), type);

			            // Reverse check (e.g., 6 -> 5)
			            boolean reverseExists = dataPointRepo.existsByDataIdAndFunctionIdAndType(
			                    dataPoint.getFunctionId(), dataPoint.getDataId(), type);

			            exists = directExists || reverseExists;
			        }

			        if (exists) {
			            throw new IllegalArgumentException(
			                "Duplicate or reverse mapping already exists between the selected records."
			            );
//			            else if ("ACTIVITY".equals(type) &&
//			                   dataPoint.getDataId() != null &&
//			                   dataPoint.getActivityId() != null) {
////			            exists = dataPointRepo.existsByDataIdAndActivityIdAndType(
////			                    dataPoint.getDataId(), dataPoint.getActivityId(), type);
//			        }
			        }
			      
			        
			if (type.equals("ACTIVITY") && saved.getActivityId() != null) {
				saved.setActivity(activityMasterRepo.findById(saved.getActivityId()).orElse(null));
			} else if (type.equals("FUNCTION") && saved.getFunctionId() != null) {
				saved.setFunction(functionRepo.findById(saved.getFunctionId()).orElse(null));
			} else if (type.equals("PROCESS") && saved.getProcessId() != null) {
				saved.setProcess(processRepo.findById(saved.getProcessId()).orElse(null));
			} else if (type.equals("INFOCAPTURED") && saved.getRefDataPointId() != null) {
				
				
				dataPointRepo.updateConnectedIfNotYes(saved.getRefReqDataPointId());
				
				saved.setRefDataPoint(dataPointRepo.findById(saved.getRefDataPointId()).orElse(null));
				saved.setRefReqDataPoint(dataPointRepo.findById(saved.getRefReqDataPointId()).orElse(null));
				saved.setActivity(activityMasterRepo.findById(saved.getActivityId()).orElse(null));
				saved.setFunction(functionRepo.findById(saved.getFunctionId()).orElse(null));
				saved.setProcess(processRepo.findById(saved.getProcessId()).orElse(null));
				
				
			}
		}

		return saved;
	}

	// Edit existing DataPoint (based on primary key)
	public DataPointModel updateDataPoint(DataPointModel dataPoint) {
		Long id = dataPoint.getDataPointId();
		if (id == null) {
			throw new IllegalArgumentException("DataPoint ID is required for update");
		}

		DataPointModel existing = dataPointRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("DataPoint not found with ID: " + id));

		// Update allowed fields
		existing.setDataId(dataPoint.getDataId());
		existing.setType(dataPoint.getType());
		existing.setRemarks(dataPoint.getRemarks());

		DataPointModel saved = dataPointRepo.save(existing);

		if (saved.getType() != null) {
			String type = saved.getType().trim().toUpperCase();

			if (type.equals("ACTIVITY") && saved.getActivityId() != null) {
				saved.setActivity(activityMasterRepo.findById(saved.getActivityId()).orElse(null));
			} else if (type.equals("FUNCTION") && saved.getFunctionId() != null) {
				saved.setFunction(functionRepo.findById(saved.getFunctionId()).orElse(null));
			} else if (type.equals("PROCESS") && saved.getProcessId() != null) {
				saved.setProcess(processRepo.findById(saved.getProcessId()).orElse(null));
			} else if (type.equals("INFOCAPTURED") && saved.getRefDataPointId() != null) {
				saved.setRefDataPoint(dataPointRepo.findById(saved.getRefDataPointId()).orElse(null));
			}
		}

		return saved;
	}

	@Transactional
	public void deleteDataPoint(DataPointModel dataPoint) {
	    Long id = dataPoint.getDataPointId();
	    if (id == null) {
	        throw new IllegalArgumentException("DataPoint ID is required for deletion");
	    }

	    DataPointModel existing = dataPointRepo.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("DataPoint not found with ID: " + id));

	    Long refReqDataPointId = existing.getRefReqDataPointId();

	    dataPointRepo.deleteById(id);

	    if (refReqDataPointId != null) {
	        dataPointRepo.updateConnectedStatusAfterDelete(refReqDataPointId);
	    }
	}


	// Get all DataPoints by dataId and type (from request body)
//	public List<DataPointModel> getByDataIdAndType(DataPointModel dataPoint) {
//		Long dataId = dataPoint.getDataId();
//		String type = dataPoint.getType();
//
//		if (dataId == null || type == null || type.trim().isEmpty()) {
//			throw new IllegalArgumentException("Both dataId and type are required");
//		}
//
//		List<DataPointModel> directRelations = dataPointRepo.findByDataIdAndType(dataId, type);
//		
//		List<DataPointModel> reverseRelations = new ArrayList<>();
//
//	    if ("PROCESS".equalsIgnoreCase(type)) {
//	        reverseRelations = dataPointRepo.findByProcessIdAndType(dataId, type);
//	    } else if ("FUNCTION".equalsIgnoreCase(type)) {
//	        reverseRelations = dataPointRepo.findByFunctionIdAndType(dataId, type);
//	    }
//		
//	    
//	    Set<DataPointModel> resultSet = new LinkedHashSet<>();
//	    resultSet.addAll(directRelations);
////	    resultSet.addAll(reverseRelations);
//
//	    List<DataPointModel> result = new ArrayList<>(resultSet);
//	    
//	    for (DataPointModel dp : reverseRelations) {
//	      
//	            if ("PROCESS".equalsIgnoreCase(type) && dp.getDataId() != null) {
//	                ProcessMaster proc = processRepo.findById(dp.getDataId()).orElse(null);
//	                dp.setProcess(proc);
//	            } else if ("FUNCTION".equalsIgnoreCase(type) && dp.getDataId() != null) {
//	                FunctionMaster func = functionRepo.findById(dp.getDataId()).orElse(null);
//	                dp.setFunction(func);
//	            }
//	            result.add(dp);
//	        }
//	        
//		  if ("PROCESS".equalsIgnoreCase(type)) {
//		        ProcessMaster process = processRepo.findById(dataId).orElse(null);
//		        if (process != null) {
//		            DataPointModel selfRow = new DataPointModel();
//		            selfRow.setProcessId(process.getProcessId());
//		            selfRow.setProcess(process);
//		            selfRow.setType("PROCESS");
//		            selfRow.setInfoName(process.getProcessName()); // optional display name
//		            result.add(0, selfRow); // Add self row to the top
//		        }
//		    } else if ("FUNCTION".equalsIgnoreCase(type)) {
//		        FunctionMaster function = functionRepo.findById(dataId).orElse(null);
//		        if (function != null) {
//		            DataPointModel selfRow = new DataPointModel();
//		            selfRow.setFunctionId(function.getFunctionId());
//		            selfRow.setFunction(function);
//		            selfRow.setType("FUNCTION");
//		            selfRow.setInfoName(function.getFunctionName());
//		            result.add(0, selfRow);
//		        }
//		    }
//
//		    return result;
//		}
	
	public List<DataPointModel> getByDataIdAndType(DataPointModel dataPoint) {
	    Long dataId = dataPoint.getDataId();
	    String type = dataPoint.getType();

	    if (dataId == null || type == null || type.trim().isEmpty()) {
	        throw new IllegalArgumentException("Both dataId and type are required");
	    }

	    List<DataPointModel> directRelations = dataPointRepo.findByDataIdAndType(dataId, type);
	    List<DataPointModel> reverseRelations = new ArrayList<>();

	    if ("PROCESS".equalsIgnoreCase(type)) {
	        reverseRelations = dataPointRepo.findByProcessIdAndType(dataId, type);
	    } else if ("FUNCTION".equalsIgnoreCase(type)) {
	        reverseRelations = dataPointRepo.findByFunctionIdAndType(dataId, type);
	    }

	    // Combine & remove duplicates
	    Set<DataPointModel> resultSet = new LinkedHashSet<>(directRelations);
	    List<DataPointModel> result = new ArrayList<>(resultSet);

	    if ("PROCESS".equalsIgnoreCase(type) && !reverseRelations.isEmpty()) {
	        // Collect all dataIds from reverseRelations
	        Set<Long> dataIds = reverseRelations.stream()
	                .map(DataPointModel::getDataId)
	                .filter(Objects::nonNull)
	                .collect(Collectors.toSet());

	        // Fetch all processes in one query
	        List<ProcessMaster> processes = processRepo.findAllById(dataIds);

	        // Map them by ID for quick lookup
	        Map<Long, ProcessMaster> processMap = processes.stream()
	                .collect(Collectors.toMap(ProcessMaster::getProcessId, p -> p));

	        // ? Assign process without extra DB calls
	        for (DataPointModel dp : reverseRelations) {
	            ProcessMaster proc = processMap.get(dp.getDataId());
	            dp.setProcess(proc);
	            result.add(dp);
	        }
	    } else if ("FUNCTION".equalsIgnoreCase(type) && !reverseRelations.isEmpty()) {
	        Set<Long> dataIds = reverseRelations.stream()
	                .map(DataPointModel::getDataId)
	                .filter(Objects::nonNull)
	                .collect(Collectors.toSet());

	        List<FunctionMaster> functions = functionRepo.findAllById(dataIds);

	        Map<Long, FunctionMaster> functionMap = functions.stream()
	                .collect(Collectors.toMap(FunctionMaster::getFunctionId, f -> f));

	        for (DataPointModel dp : reverseRelations) {
	            FunctionMaster func = functionMap.get(dp.getDataId());
	            dp.setFunction(func);
	            result.add(dp);
	        }
	    }

	    // ? Add self row
	    if ("PROCESS".equalsIgnoreCase(type)) {
	        processRepo.findById(dataId).ifPresent(process -> {
	            DataPointModel selfRow = new DataPointModel();
	            selfRow.setProcessId(process.getProcessId());
	            selfRow.setProcess(process);
	            selfRow.setType("PROCESS");
	            selfRow.setInfoName(process.getProcessName());
	            result.add(0, selfRow);
	        });
	    } else if ("FUNCTION".equalsIgnoreCase(type)) {
	        functionRepo.findById(dataId).ifPresent(function -> {
	            DataPointModel selfRow = new DataPointModel();
	            selfRow.setFunctionId(function.getFunctionId());
	            selfRow.setFunction(function);
	            selfRow.setType("FUNCTION");
	            selfRow.setInfoName(function.getFunctionName());
	            result.add(0, selfRow);
	        });
	    }

	    return result;
	}


//	public List<LoadOptionsDto> loadFunctionOptionsByDataId(Long dataId, String type) {
//		List<DataPointModel> dataPoints = dataPointRepo.findByDataIdAndType(dataId, type);
//	    List<LoadOptionsDto> dtoList = new ArrayList<>();
//
//	    if (!dataPoints.isEmpty()) {
//	        // To avoid duplicate function lookups
//	        Set<Long> functionIds = new HashSet<>();
//
//	        for (DataPointModel dp : dataPoints) {
//	            if (dp.getFunction() != null) {
//	                functionIds.add(dp.getFunction().getFunctionId());
//	            }
//	        }
//
//	        // Now get all activities for each function
//	        for (Long functionId : functionIds) {
//	            List<ActivityMaster> activities = activityMasterRepo.findByFunctionId(functionId);
//
//	            for (ActivityMaster activity : activities) {
//	                LoadOptionsDto dto = new LoadOptionsDto();
//	                dto.setValue(activity.getActivityId());
//	                dto.setLabel(activity.getActivityName());
//	                dtoList.add(dto);
//	            }
//	        }
//	    }
//
//	    return dtoList;
//	    
//	}

	public List<LoadOptionsDto> loadFunctionOptionsByDataId(Long dataId, String type) {
		List<DataPointModel> dataPoints = dataPointRepo.findByDataIdAndType(dataId, type);
		
		System.out.println(" size " + dataPoints.size());
		List<LoadOptionsDto> dtoList = new ArrayList<>();

		if (!dataPoints.isEmpty()) {
			Set<Long> functionIds = new HashSet<>();

			for (DataPointModel dp : dataPoints) {
				if (dp.getFunction() != null) {
					functionIds.add(dp.getFunction().getFunctionId());
				}
			}

			for (Long functionId : functionIds) {
				List<ActivityMaster> activities = activityMasterRepo.findByFunctionId(functionId);

				// Get all DataPoints of type 'ACTIVITY' only once

				for (ActivityMaster activity : activities) {
					List<DataPointModel> dataPointList = dataPointRepo.findByDataIdAndType(activity.getActivityId(),
							"ACTIVITY");

					for (DataPointModel dp : dataPointList) {
						if (dp.getInfoName() != null && dp.getDataPointId() != null) {
							LoadOptionsDto dto = new LoadOptionsDto();
							dto.setValue(dp.getDataPointId());
							dto.setLabel(dp.getInfoName());
							dtoList.add(dto);
						}
					}
				}
			}
		}

		return dtoList;
	}

	public List<LoadOptionsDto> loadFuncptionsByDataId(Long dataId, String type) {
	    // Step 1: Fetch unique function IDs directly from DB
	    List<Long> uniqueFunctionIds = dataPointRepo.findDistinctFunctionIdsByType(type);

	    // Step 2: Get all functions for this processId
	    List<FunctionMaster> allFunctions = functionRepo.findByProcessId(dataId);

	    // Step 3: Filter matching ones
	    List<FunctionMaster> matchedFunctions = allFunctions.stream()
	        .filter(f -> uniqueFunctionIds.contains(f.getFunctionId()))
	        .collect(Collectors.toList());

	    // Step 4: Convert to DTOs
	    return matchedFunctions.stream()
	        .map(f -> {
	            LoadOptionsDto dto = new LoadOptionsDto();
	            dto.setValue(f.getFunctionId());
	            dto.setLabel(f.getFunctionName());
	            return dto;
	        })
	        .collect(Collectors.toList());
	}



//	public List<LoadOptionsDto> loadProcoptionsByDataId(Long dataId, String type) {
//	    List<DataPointModel> dataPoints = dataPointRepo.findByDataIdAndType(dataId, type);
//	    
//
//	    List<LoadOptionsDto> dtoList = new ArrayList<>();
//	    
//	    
//	    for (DataPointModel dp : dataPoints) {
//	        if (dp.getProcessId() != null) {
//	            Optional<ProcessMaster> procOpt = processRepo.findById(dp.getProcessId());
//	            
//	            if (procOpt.isPresent()) {
//	                ProcessMaster proc = procOpt.get();
//	                
//	                LoadOptionsDto dto = new LoadOptionsDto();
//	                dto.setValue(proc.getProcessId());
//	                dto.setLabel(proc.getProcessName());
//	                dtoList.add(dto);
//	            }
//	        }
//	    }
//	    
//	    ProcessMaster selfProc = processRepo.findById(dataId).orElse(null);
//	    if (selfProc != null) {
//	        boolean alreadyExists = false;
//
//	        // Compare primitives with ==
//	        for (LoadOptionsDto existing : dtoList) {
//	            if (existing.getValue() == selfProc.getProcessId()) {
//	                alreadyExists = true;
//	                break;
//	            }
//	        }
//
//	        if (!alreadyExists) {
//	            LoadOptionsDto selfDto = new LoadOptionsDto();
//	            selfDto.setValue(selfProc.getProcessId());
//	            selfDto.setLabel(selfProc.getProcessName());
//	            dtoList.add(selfDto);
//	        }
//	    }
//
//	    return dtoList;
//
//	   
//	}
	
	public List<LoadOptionsDto> loadProcoptionsByDataId(Long dataId, String type) {
	    // Step 1: Get direct and reverse relations
	    List<DataPointModel> directRelations = dataPointRepo.findByDataIdAndType(dataId, type);
	    List<DataPointModel> reverseRelations = dataPointRepo.findByProcessIdAndType(dataId, type);

	    // Step 2: Collect unique process IDs
	    Set<Long> processIds = new LinkedHashSet<Long>();

	    for (DataPointModel dp : directRelations) {
	        if (dp.getProcessId() != null) {
	            processIds.add(dp.getProcessId());
	        }
	    }

	    for (DataPointModel dp : reverseRelations) {
	        if (dp.getDataId() != null) {
	            processIds.add(dp.getDataId());
	        }
	    }

	    // Step 3: Include self
	    processIds.add(dataId);

	    // Step 4: Single DB hit
	    List<ProcessMaster> processList = processRepo.findAllById(processIds);

	    // Step 5: Convert to dropdown DTOs
	    List<LoadOptionsDto> dtoList = new ArrayList<LoadOptionsDto>();
	    for (ProcessMaster proc : processList) {
	        LoadOptionsDto dto = new LoadOptionsDto();
	        dto.setValue(proc.getProcessId());
	        dto.setLabel(proc.getProcessName());
	        dtoList.add(dto);
	    }

	    return dtoList;
	}



	public List<LoadOptionsDto> loadActoptionsByDataId(Long dataId, String type) {
	    // Step 1: Get unique activity IDs from DataPointModel
	    List<Long> activityIds = dataPointRepo.findDistinctActivityIdsByDataIdAndType(type);

//	    List<ActivityMaster> activities = activityMasterRepo.findByActivityIdIn(activityIds);
//
//	    // Step 2: Get all matching activities in a single query
//	    
//
//	    // Step 3: Convert to DTO list
//	    List<ActivityMaster> matchedAct = activities.stream()
//		        .filter(f -> activityIds.contains(f.getActivityId()))
//		        .collect(Collectors.toList());

	    List<ActivityMaster> activitiesListAll = activityMasterRepo.findByFunctionId(dataId);
	    
	    List<ActivityMaster> finalMatchedAct = activitiesListAll.stream()
		        .filter(f -> activityIds.contains(f.getActivityId()))
		        .collect(Collectors.toList());
	    
	    List<LoadOptionsDto> dtoList = finalMatchedAct.stream()
	        .map(act -> {
	            LoadOptionsDto dto = new LoadOptionsDto();
	            dto.setValue(act.getActivityId());
	            dto.setLabel(act.getActivityName());
	            return dto;
	        })
	        .collect(Collectors.toList());

	    return dtoList;
	}

	
	public List<LoadOptionsDto> loadInfoOptionsByDataId(Long dataId, String type) {
		List<DataPointModel> dataPoints = dataPointRepo.findByDataIdAndType(dataId, type);
		List<LoadOptionsDto> dtoList = new ArrayList<>();

		
					for (DataPointModel dp : dataPoints) {
						if (dp.getInfoName() != null && dp.getDataPointId() != null) {
							LoadOptionsDto dto = new LoadOptionsDto();
							dto.setValue(dp.getDataPointId());
							dto.setLabel(dp.getInfoName());
							dtoList.add(dto);
						}
					}
				
		

		return dtoList;
	}

	public List<LoadOptionsDto> loadFunctionOptions(Long dataId, String type) {
		
		 List<Long> uniqueProcessIds = dataPointRepo.findDistinctProcessIdsByDataIdAndType(dataId, type);
		 
		 List<FunctionMaster> allFunctions = functionRepo.findByProcessIdIn(uniqueProcessIds);
		 
		 List<LoadOptionsDto> dtoList = new ArrayList<>();
		 if (!allFunctions.isEmpty()) {
			   for (FunctionMaster typeMaster : allFunctions) {
				   LoadOptionsDto loadDto = new LoadOptionsDto();

		            loadDto.setLabel(typeMaster.getFunctionName());
		            loadDto.setValue(typeMaster.getFunctionId());

		            dtoList.add(loadDto);
		        }
		 }
		return dtoList;
		 
	}

	public List<DataPointModel> getByDataIdAndTypeAndSource(DataPointModel dataPoint) {
		Long dataId = dataPoint.getDataId();
		String type = dataPoint.getType();

		if (dataId == null || type == null || type.trim().isEmpty()) {
			throw new IllegalArgumentException("Both dataId and type are required");
		}

		List<DataPointModel> result = dataPointRepo.findByDataIdAndTypeAndInfoType(dataId, type, dataPoint.getInfoType());
		
		
		
		    return result;
		}

	public List<DataPointModel> getByDataIdAndTypeAndReq(DataPointModel dataPoint) {
		
		Long dataId = dataPoint.getDataId();
		String type = dataPoint.getType();

		if (dataId == null || type == null || type.trim().isEmpty()) {
			throw new IllegalArgumentException("Both dataId and type are required");
		}

		List<DataPointModel> result = dataPointRepo.findByDataIdAndTypeAndInfoTypeAndRefReqDataPointId(dataId, type, dataPoint.getInfoType(), dataPoint.getRefReqDataPointId());
		
		
		
		    return result;
	}

	public List<LoadOptionsDto> loadInfoOptionsByDataIdInfoType(Long dataId, String type, String infoType) {
		List<DataPointModel> dataPoints = dataPointRepo.findByDataIdAndTypeAndInfoType(dataId, type, infoType);
		List<LoadOptionsDto> dtoList = new ArrayList<>();

		
					for (DataPointModel dp : dataPoints) {
						if (dp.getInfoName() != null && dp.getDataPointId() != null) {
							LoadOptionsDto dto = new LoadOptionsDto();
							dto.setValue(dp.getDataPointId());
							dto.setLabel(dp.getInfoName());
							dtoList.add(dto);
						}
					}
				
		

		return dtoList;
	}

}
