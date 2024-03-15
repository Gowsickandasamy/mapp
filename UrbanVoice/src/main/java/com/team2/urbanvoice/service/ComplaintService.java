package com.team2.urbanvoice.service;
 
import java.util.List;

import com.team2.urbanvoice.entities.Complaint;
import com.team2.urbanvoice.entities.Jurisdiction;
import com.team2.urbanvoice.entities.Officer;
import com.team2.urbanvoice.entities.Status;
import com.team2.urbanvoice.entities.User;
import com.team2.urbanvoice.models.ComplaintModel;
 
public interface ComplaintService {
 
	Complaint getComplaint(int complId);
 
	
	List<Complaint> getAllComplaints();
 
	Complaint createComplaint(ComplaintModel complaint, Jurisdiction jury, User user) throws Exception;
 
	
	List<Complaint> getComplaintsByUser(User user);
 

	List<Complaint> getComplaintByOfficer(Officer officer);
 

	Long countComplaints();
 
	
	void saveComplaint(Complaint complaint, User user) throws Exception;
 
	
	void updateComplaintStatus(int complaintId, Status newStatus,User user) throws Exception;


	Long getCountOfClosedComplaints();

 
}