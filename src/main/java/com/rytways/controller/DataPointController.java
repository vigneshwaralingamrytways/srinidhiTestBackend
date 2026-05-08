package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.DataPointModel;
import com.rytways.repository.DataPointRepo;
import com.rytways.service.DataPointService;

@RestController
@RequestMapping("/dataPoint")
public class DataPointController {

	@Autowired
	private DataPointService dataPointService;

	@Autowired
	private DataPointRepo dataPointRepo;

	@PostMapping("/create")
	public ResponseEntity<DataPointModel> addDataPoint(@RequestBody DataPointModel dataPoint) {
		DataPointModel saved = dataPointService.saveDataPoint(dataPoint);
		return ResponseEntity.ok(saved);
	}

	@PostMapping("/edit")
	public ResponseEntity<DataPointModel> updateDataPoint(@RequestBody DataPointModel dataPoint) {
		DataPointModel updated = dataPointService.updateDataPoint(dataPoint);
		return ResponseEntity.ok(updated);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteDataPoint(@RequestBody DataPointModel dataPoint) {
		dataPointService.deleteDataPoint(dataPoint);
		return ResponseEntity.ok("DataPoint deleted successfully");
	}

	@PostMapping("/getByDataIdAndType")
	public ResponseEntity<List<DataPointModel>> getByDataIdAndType(@RequestBody DataPointModel dataPoint) {
		List<DataPointModel> result = dataPointService.getByDataIdAndType(dataPoint);
		return ResponseEntity.ok(result);
	}
	

	@PostMapping("/getByDataIdAndTypeAndReqSet")
	public ResponseEntity<List<DataPointModel>> getByDataIdAndTypeAndReq(@RequestBody DataPointModel dataPoint) {
		List<DataPointModel> result = dataPointService.getByDataIdAndTypeAndReq(dataPoint);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/getByDataIdAndTypeAndSource")
	public ResponseEntity<List<DataPointModel>> getByDataIdAndTypeAndSourch(@RequestBody DataPointModel dataPoint) {
		List<DataPointModel> result = dataPointService.getByDataIdAndTypeAndSource(dataPoint);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/loadFunctionOptionsByDataIdMapedProcess")
	public ResponseEntity<List<LoadOptionsDto>> loadFuncOpt(@RequestBody DataPointModel request) {
		List<LoadOptionsDto> loadOptions = dataPointService.loadFunctionOptions(request.getDataId(), request.getType());
		return new ResponseEntity<>(loadOptions, HttpStatus.OK);
	}

	@PostMapping("/loadActivityByDataId")
	public ResponseEntity<List<LoadOptionsDto>> loadActOptionsByDataId(@RequestBody DataPointModel request) {
		List<LoadOptionsDto> loadOptions = dataPointService.loadFunctionOptionsByDataId(request.getDataId(),
				request.getType());
		return new ResponseEntity<>(loadOptions, HttpStatus.OK);
	}

	@PostMapping("/loadProcessOptByDataId")
	public ResponseEntity<List<LoadOptionsDto>> loadProcOptionByDataId(@RequestBody DataPointModel request) {
		List<LoadOptionsDto> loadOptions = dataPointService.loadProcoptionsByDataId(request.getDataId(),
				request.getType());
		return new ResponseEntity<>(loadOptions, HttpStatus.OK);
	}

	@PostMapping("/loadFuncOptByDataId")
	public ResponseEntity<List<LoadOptionsDto>> loadFunctionOptionsByDataId(@RequestBody DataPointModel request) {
		List<LoadOptionsDto> loadOptions = dataPointService.loadFuncptionsByDataId(request.getDataId(),
				request.getType());
		return new ResponseEntity<>(loadOptions, HttpStatus.OK);
	}

	@PostMapping("/loadActOptByDataId")
	public ResponseEntity<List<LoadOptionsDto>> loadActOptionByDataId(@RequestBody DataPointModel request) {
		List<LoadOptionsDto> loadOptions = dataPointService.loadActoptionsByDataId(request.getDataId(),
				request.getType());
		return new ResponseEntity<>(loadOptions, HttpStatus.OK);
	}

	@PostMapping("/loadInfoByDataId")
	public ResponseEntity<List<LoadOptionsDto>> loadInfoOptionsByDataId(@RequestBody DataPointModel request) {
		List<LoadOptionsDto> loadOptions = dataPointService.loadInfoOptionsByDataId(request.getDataId(),
				request.getType());
		return new ResponseEntity<>(loadOptions, HttpStatus.OK);
	}
	
	@PostMapping("/loadInfoByDataIdAndInfoType")
	public ResponseEntity<List<LoadOptionsDto>> loadInfoOptionsByDataIdAndType(@RequestBody DataPointModel request) {
		List<LoadOptionsDto> loadOptions = dataPointService.loadInfoOptionsByDataIdInfoType(request.getDataId(),
				request.getType(), request.getInfoType());
		return new ResponseEntity<>(loadOptions, HttpStatus.OK);
	}
}
