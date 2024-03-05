package com.team2.urbanvoice.service;

import org.springframework.stereotype.Service;

import com.team2.urbanvoice.entities.Admin;
import com.team2.urbanvoice.entities.Complaint;
import com.team2.urbanvoice.entities.Jurisdiction;
import com.team2.urbanvoice.entities.Officer;
import com.team2.urbanvoice.entities.User;
import com.team2.urbanvoice.exception.UrbanVoiceException;

@Service
public interface EmailSenderService {


	String getotp(String email);


	Boolean validate(String email, String otp) throws UrbanVoiceException;


	String getEmailContent(User user, Officer officer, Complaint complaint) throws Exception;


	Boolean sendEmailToUser(User user, Officer officer, Complaint complaint) throws Exception;

	Boolean sendEmailToOfficer(Officer officer, Jurisdiction jury) throws Exception;


	String getEmaiOfficerContent(Officer officer, Jurisdiction jury) throws Exception;


	String getResponseContent(User user, Complaint complaint) throws Exception;

	
	Boolean sendResponseToUser(User user, Complaint complaint) throws Exception;


	 
	Boolean sendStatusToUser(User user, Complaint complaint) throws Exception;


	String getStatusContent(User user, Complaint complaint) throws Exception;

	
	Boolean sendEmailToAdmin(Admin admin) throws Exception;

	
	String getEmailContentOfAdmin(Admin admin) throws Exception;

}