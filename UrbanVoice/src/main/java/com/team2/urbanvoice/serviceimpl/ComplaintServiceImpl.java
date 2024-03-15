package com.team2.urbanvoice.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.team2.urbanvoice.entities.Complaint;
import com.team2.urbanvoice.entities.Jurisdiction;
import com.team2.urbanvoice.entities.Officer;
import com.team2.urbanvoice.entities.Status;
import com.team2.urbanvoice.entities.User;
import com.team2.urbanvoice.models.ComplaintModel;
import com.team2.urbanvoice.repositories.ComplaintRepo;
import com.team2.urbanvoice.repositories.OfficerRepo;
import com.team2.urbanvoice.service.ComplaintService;
import com.team2.urbanvoice.service.EmailSenderService;
import com.team2.urbanvoice.service.OfficerService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ComplaintServiceImpl implements ComplaintService {
	private static final Logger logger = LoggerFactory.getLogger(ComplaintServiceImpl.class);

	@Autowired
	private ComplaintRepo complaintRepo;
	@Autowired
	private OfficerRepo officerRepo;
	@Autowired
	private OfficerService officerService;
	@Autowired
	private EmailSenderService emailService;

	@Override
	public Complaint getComplaint(int complId) {
		logger.debug("Retrieving Complaint with Id {} !", complId);

		return complaintRepo.getReferenceById(complId);
	}

	@Override
	public List<Complaint> getAllComplaints() {
		logger.debug("Retrieving all the Complaints registered !");

		return complaintRepo.findAll();
	}

	@Override
	public Complaint createComplaint(ComplaintModel complaint, Jurisdiction jury, User user) throws Exception {
	    logger.debug("Complaint registration by {}, in {}", user.getFirstName(), jury.getArea());

	    Complaint complaintDetails = new Complaint();
	    complaintDetails.setAddress(complaint.getAddress());
	    complaintDetails.setLandmark(complaint.getLandmark());
	    complaintDetails.setIssue(complaint.getIssue());
	    complaintDetails.setComments(complaint.getComment());
	    complaintDetails.setLocation(complaint.getLocation());
	    
	    
	    
	    // Set the jurisdiction for the complaint
	    complaintDetails.setJuryComplaint(jury);
	    
	    // Find officer with the least number of complaints in the jurisdiction
	    Integer officerId = officerRepo.findOfficerIdWithMinComplaintCount(jury.getJuryId());
	    Officer officer = officerService.getOfficer(officerId);
	    
	    // Set officer and user for the complaint
	    complaintDetails.setOfficer(officer);
	    complaintDetails.setUser(user);
	    
	    // Set created date and status for the complaint
	    complaintDetails.setCreatedDate(LocalDateTime.now());
	    complaintDetails.setStatus(Status.WAITING);
	    
	    // Save the complaint
	    Complaint savedComplaint = complaintRepo.save(complaintDetails);
	    
	    // Send email notification to the user and officer
	    emailService.sendEmailToUser(user, officer, savedComplaint);

	    logger.info("Complaint Registered Successfully !");

	    return savedComplaint;
	}

	@Override
	public List<Complaint> getComplaintsByUser(User user) {
		logger.debug("Getting Complaints of a User {}", user.getFirstName());

		return complaintRepo.getComplaintByUser(user);
	}

	@Override
	public Long countComplaints() {
		logger.debug("Counting total number of Complaints Registered !");

		return complaintRepo.count();
	}

	@Override
	public List<Complaint> getComplaintByOfficer(Officer officer) {
		logger.debug("Retrieving Complaints associated to Officer: {}", officer.getOfficerName());

		return complaintRepo.getComplaintByOfficer(officer);
	}

	@Override
	public void saveComplaint(Complaint complaint, User user) throws Exception {
		logger.debug("Saving Response");
		emailService.sendResponseToUser(user, complaint);
		complaintRepo.save(complaint);

	}

	@Override
	public void updateComplaintStatus(int complaintId, Status newStatus, User user) throws Exception {
		logger.debug("Updating Status");
		Complaint complaint = complaintRepo.getReferenceById(complaintId);
		complaint.setStatus(newStatus);
		emailService.sendStatusToUser(user, complaint);
		logger.info("Changing Status");
		complaintRepo.save(complaint);
	}

	@Override
	public Long getCountOfClosedComplaints() {
		return complaintRepo.countByStatus(Status.CLOSED);
	}
}
