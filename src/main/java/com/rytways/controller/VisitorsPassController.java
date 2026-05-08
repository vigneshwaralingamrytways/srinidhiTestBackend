package com.rytways.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rytways.model.DispatchPass;
import com.rytways.model.VisitorsPass;
import com.rytways.repository.VisitorsPassRepo;
import com.rytways.service.CategoriesService;
import com.rytways.service.VisitorsPassService;

@RestController
@RequestMapping("/visitorsPass")
public class VisitorsPassController {

	@Autowired
	CategoriesService catService;

	private final VisitorsPassService visitorsPassService;

	@Autowired
	private VisitorsPassRepo visitorsPassRepo;

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	public VisitorsPassController(VisitorsPassService visitorsPassService) {
		this.visitorsPassService = visitorsPassService;
	}

	@PostMapping("/create")
	public VisitorsPass createVisitorsPass(@RequestBody VisitorsPass visitorsPass) {

		String isSaved = "";

		visitorsPass = visitorsPassService.save(visitorsPass);

		return visitorsPass;
	}

	@PostMapping("/update")
	public VisitorsPass updateVisitorsPass(@RequestBody VisitorsPass visitorsPass) {

		String isSaved = "";

		visitorsPass = visitorsPassService.update(visitorsPass);

		return visitorsPass;
	}

	@PostMapping("/listAll")
	public List<VisitorsPass> loadAll(@RequestBody VisitorsPass visitorsPass) throws IOException {

		String isSaved = "";

		List<VisitorsPass> resultList = visitorsPassRepo.findAll();

		return resultList;
	}

	@PostMapping("/search")
	public ResponseEntity<List<VisitorsPass>> listOrders(@RequestBody VisitorsPass dispatch) {

		// Page<CustomerMaster> custPage = custService.findCustomers(pageNum);

		// List<CustomerMaster> customers = custPage.getContent();

		List<VisitorsPass> orders = visitorsPassService.writeExcel(dispatch);

		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@PostMapping("/intime")
	public VisitorsPass updateInTime(@RequestBody VisitorsPass updatedDispatchPass) throws IOException {

		String isSaved = "";

		updatedDispatchPass.setInTime(LocalDateTime.now());
		updatedDispatchPass = visitorsPassService.update(updatedDispatchPass);

		return updatedDispatchPass;
	}

	@PostMapping("/outtime")
	public VisitorsPass updateOutTime(@RequestBody VisitorsPass updatedDispatchPass) throws IOException {

		String isSaved = "";
		updatedDispatchPass.setOutTime(LocalDateTime.now());
		updatedDispatchPass = visitorsPassService.update(updatedDispatchPass);

		return updatedDispatchPass;
	}

	@PostMapping("/upload")
	public String uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("visitorId") Long visitorId) {
		String fileName = visitorId + ".png";
		try {
			File directory = new File(uploadPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			File uploadFile = new File(directory.getAbsolutePath() + File.separator + fileName);
			file.transferTo(uploadFile);
			return "File uploaded successfully: " + uploadFile.getAbsolutePath();
		} catch (IOException e) {
			return "Error uploading file: " + e.getMessage();
		}
	}

	@PostMapping("/sendSms")
	public ResponseEntity<String> sendSms(@RequestBody Map<String, String> request) {
		String passNo = request.get("passNo");
		if (passNo == null || passNo.isEmpty()) {
			return new ResponseEntity<>("Pass Number is required", HttpStatus.BAD_REQUEST);
		}

		try {
			String result = visitorsPassService.sendSmsToVistor(passNo);

			if ("Invalid Pass Number".equals(result)) {
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error sending SMS: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
		String passNo = request.get("passNo");
		if (passNo == null || passNo.isEmpty()) {
			return new ResponseEntity<>("Pass Number is required", HttpStatus.BAD_REQUEST);
		}

		try {
			String result = visitorsPassService.sendEmailToVistor(passNo);

			if ("Invalid Pass Number".equals(result)) {
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error sending Email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/sendWhatsApp")
	public ResponseEntity<String> sendWhatsApp(@RequestBody Map<String, String> request) {
		String passNo = request.get("passNo");
		if (passNo == null || passNo.isEmpty()) {
			return new ResponseEntity<>("Pass Number is required", HttpStatus.BAD_REQUEST);
		}

		try {
			String result = visitorsPassService.sendWhatsAppToVistor(passNo);

			if ("Invalid Pass Number".equals(result)) {
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error sending WhatsApp Message: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/scanQr")
	public ResponseEntity<String> ScanQrCode(@RequestBody Map<String, String> request) {
		String passNo = request.get("passNo");
		String visitorIdStr = request.get("visitorId");
		Long visitorId = Long.parseLong(visitorIdStr);
		if (passNo == null || passNo.isEmpty()) {
			return new ResponseEntity<>("Pass Number is required", HttpStatus.BAD_REQUEST);
		}

		try {
			String result = visitorsPassService.processQrScan(visitorId, passNo);

			if ("Invalid Pass Number".equals(result)) {
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error while Scan Qr " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}