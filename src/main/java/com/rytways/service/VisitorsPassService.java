package com.rytways.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.rytways.model.Categories;
import com.rytways.model.CompanyMaster;
import com.rytways.model.GateMaster;
import com.rytways.model.SeriousNo;
import com.rytways.model.UnitMaster;
import com.rytways.model.VisitorsPass;
import com.rytways.repository.CategoriesRepo;
import com.rytways.repository.CompanyMasterRepository;
import com.rytways.repository.GateMasterRepo;
import com.rytways.repository.SeriousNoRepo;
import com.rytways.repository.UnitRepository;
import com.rytways.repository.VisitorsPassRepo;
import com.rytways.specifications.VisitorsPassSpecs;
import com.rytways.utils.SmsUtils;
import com.rytways.utils.Utils;

@Service
public class VisitorsPassService {

	@Autowired
	VisitorsPassRepo visitorsPassRepo;

	@Autowired
	UnitRepository unitRepo;

	@Autowired
	SeriousNoService seriousService;

	@Autowired
	SeriousNoRepo seriousRepo;

	@Autowired
	CategoriesRepo categoriesRepo;

	@Autowired
	GateMasterRepo gateRepo;

	@Autowired
	private ApprovalProcessService approvalService;

	@Autowired
	CompanyMasterRepository companyMasterRepository;

	@Autowired
	private SmsUtils utilService;

	public VisitorsPass save(VisitorsPass visitorsPass) {
		String isSaved = "";
		if (visitorsPass.getVisitingDate() == null)
			visitorsPass.setVisitingDate(LocalDateTime.now());
		visitorsPass.setEntryDate(LocalDate.now());
		LocalDate financialDate = Utils.getFinancialStartDate(
				visitorsPass.getVisitingDate() != null ? visitorsPass.getVisitingDate().toLocalDate()
						: visitorsPass.getEntryDate());
		String finacialYear = Utils
				.getFinancialYear(visitorsPass.getVisitingDate() != null ? visitorsPass.getVisitingDate().toLocalDate()
						: visitorsPass.getEntryDate());
		Optional<SeriousNo> seriousNo = seriousRepo.findSeriousNo(financialDate, "Visitors Serious");
		Optional<UnitMaster> unit = unitRepo.findByUnitName(visitorsPass.getUnit());
		Optional<CompanyMaster> company = companyMasterRepository.findById(unit.get().getId().intValue());
		if (seriousNo.isPresent()) {
			int no = seriousNo.get().getSeriousNo() + 1;
			String formattedNo = String.format("%05d", no);
			visitorsPass.setPassNo((company.isPresent() ? company.get().getPrefixName() : "") + "VP-" + finacialYear
					+ "-" + formattedNo);
			seriousService.updateSerious(seriousNo.get());
		} else {
			seriousService.createSerious(financialDate, "Visitors Serious");
			visitorsPass.setPassNo(
					(company.isPresent() ? company.get().getPrefixName() : "") + "VP-" + finacialYear + "-00001");
		}
		if ("Yes".equals(visitorsPass.getPriorAppointment()))
			visitorsPass.setStatus("Created");
		else
			visitorsPass.setStatus("NoPriority");
		visitorsPass = visitorsPassRepo.save(visitorsPass);
		Optional<Categories> cat = categoriesRepo.findById((long) visitorsPass.getVisitorTypeId());
		visitorsPass.setVisitorType(cat.get());

//		Optional<UnitMaster> unit = unitRepo.findById((long) visitorsPass.getUnitId());
//		visitorsPass.setUnit(unit.get());
		Optional<GateMaster> gate = gateRepo.findById((long) visitorsPass.getGateId());
		visitorsPass.setGate(gate.get());
		String status = approvalService.createApporvalHistory("Visitor Entry", visitorsPass.getVisitorId(),
				visitorsPass.getUpdatedBy(), 1);
		return visitorsPass;
	}

	public VisitorsPass update(VisitorsPass NewVisitorsPass) {
		String isSaved = "";

		Optional<VisitorsPass> visitorsPassOpt = visitorsPassRepo.findById(NewVisitorsPass.getVisitorId());
		VisitorsPass visitorsPass = visitorsPassOpt.get();
		if (visitorsPass.getInTime() != null || visitorsPass.getOutTime() != null) {
			throw new RuntimeException("We can't Edit the Visitor Pass after InTime or OutTime is set");
		}
		if (NewVisitorsPass.getVisitingDate() != null && visitorsPass.getVisitingDate() != null) {
			if (NewVisitorsPass.getVisitingDate().isBefore(visitorsPass.getVisitingDate())) {
				throw new RuntimeException("Visiting Date Should not be a lower date than the previous Visiting Date");
			}
		}

		NewVisitorsPass = visitorsPassRepo.save(NewVisitorsPass);
		return NewVisitorsPass;
	}

	public VisitorsPass updateStatus(Long entryId, String status) {
		String isSaved = "";
		Optional<VisitorsPass> visitorsPass = visitorsPassRepo.findById(entryId);
		visitorsPass.get().setStatus(status);
		visitorsPassRepo.save(visitorsPass.get());
		return visitorsPass.get();
	}

	public List<VisitorsPass> writeExcel(VisitorsPass order) {
		Sort sort = Sort.by(Sort.Direction.ASC, "visitorId");
		List<Specification<VisitorsPass>> specs = new ArrayList<>();

		if (order.getCreateBy() != null && order.getCreateBy() != "") {
			specs.add(VisitorsPassSpecs.cusNameEquals(order.getCreateBy()));
		}
		if (order.getDepartment() != null && order.getDepartment() != "") {
			specs.add(VisitorsPassSpecs.department(order.getDepartment()));
		}
		if (order.getFromDate() != null) {
			LocalDateTime localdt = order.getFromDate().atStartOfDay();
			specs.add(VisitorsPassSpecs.fromDate(localdt));
		}
		if (order.getToDate() != null) {
			LocalDateTime localdt = order.getToDate().atTime(23, 59);
			specs.add(VisitorsPassSpecs.tillDate(localdt));
		}

		if (specs.isEmpty()) {
			return new ArrayList<VisitorsPass>();
		}

		Specification<VisitorsPass> combinedSpec = specs.stream().reduce(Specification::and).orElse(null);

		return visitorsPassRepo.findAll(combinedSpec, sort);
	}

	@Transactional
	public String processQrScan(String passNo) {
		Optional<VisitorsPass> visitorOptional = visitorsPassRepo.findByPassNo(passNo);
		if (!visitorOptional.isPresent()) {
			return "Error: Invalid Pass Number";
		}
		VisitorsPass visitor = visitorOptional.get();
//		visitor.getVisitorId().equals(visitorId);
//		if (!visitor.getVisitorId().equals(visitorId)) {
//			return "Invalid: Pass number does not belong to this visitor";
//		}
		if (visitor.getVisitingDate() == null) {
			return "Error: No visiting date set";
		}

		if (visitor.getVisitingDate() != null && visitor.getVisitingDate().toLocalDate().equals(LocalDate.now())) {

			LocalDateTime now = LocalDateTime.now();
			String currentStatus = visitor.getStatus() != null ? visitor.getStatus().trim().toUpperCase() : "";
			if (visitor.getInTime() == null && visitor.getOutTime() == null && currentStatus.equalsIgnoreCase("Created")
					|| currentStatus.equals("-") || currentStatus.isEmpty()) {
				visitor.setInTime(now);
				visitor.setStatus("IN");

				visitorsPassRepo.save(visitor);
				return "In-Time marked successfully at " + visitor.getInTime();
			} else if (visitor.getInTime() != null && visitor.getOutTime() == null
					&& currentStatus.equalsIgnoreCase("IN") && !currentStatus.equalsIgnoreCase("OUT")) {

				LocalDateTime inTime = visitor.getInTime();
				long minutesElapsed = Duration.between(inTime, now).toMinutes();

				if (minutesElapsed < 15) {
					long waitTime = 15 - minutesElapsed;
					return "Wait: Please wait " + waitTime + " more minutes to mark Out-Time.";
				} else {
					visitor.setOutTime(now);
					visitor.setStatus("OUT");
					visitorsPassRepo.save(visitor);
					return "Out-Time marked successfully at " + now.toLocalTime().toString().substring(0, 5);
				}
			} else if (currentStatus != null && currentStatus != null && currentStatus.equalsIgnoreCase("OUT")) {
				return "Warning: Visitor has already checked out.";
			} else {
				return "Error: Unknown pass status state: " + visitor;
			}
		} else {

//			throw new RuntimeException("Visting Date is Not Match With today Date. ");
			return "Error: This pass is valid only for " + visitor.getVisitingDate();

		}
	}

	public String sendSmsToVistor(String passNo) {
		Optional<VisitorsPass> visitorOptional = visitorsPassRepo.findByPassNo(passNo);

		if (!visitorOptional.isPresent()) {
			return "Invalid Pass Number";
		}

		VisitorsPass visitor = visitorOptional.get();
		String mobileNo = visitor.getPassNo();
		Long userId = visitor.getVisitorId();
		String finalMsg = "";
		String type = "Vistor";
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

			@Override
			public void afterCommit() {
				try {
					utilService.sendSMS("91" + mobileNo, userId, type, finalMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return "Sms Sent Succusfully to the Vistor:" + visitor.getVisitorName() + " Visito Pass No:"
				+ visitor.getPassNo();
	}

	public String sendEmailToVistor(String passNo) {
		Optional<VisitorsPass> visitorOptional = visitorsPassRepo.findByPassNo(passNo);

		if (!visitorOptional.isPresent()) {
			return "Invalid Pass Number";
		}

		VisitorsPass visitor = visitorOptional.get();
		return "Email Sent Succusfully to the Vistor:" + visitor.getVisitorName() + " Visito Pass No:"
				+ visitor.getPassNo();
	}

	public String sendWhatsAppToVistor(String passNo) {
		Optional<VisitorsPass> visitorOptional = visitorsPassRepo.findByPassNo(passNo);

		if (!visitorOptional.isPresent()) {
			return "Invalid Pass Number";
		}

		VisitorsPass visitor = visitorOptional.get();
		return "WhatsApp Message Sent Succusfully to the Vistor:" + visitor.getVisitorName() + " Visito Pass No:"
				+ visitor.getPassNo();
	}

}
