package com.team2.urbanvoice.service;

import java.util.List;

import com.team2.urbanvoice.entities.Jurisdiction;
import com.team2.urbanvoice.entities.Officer;
import com.team2.urbanvoice.exception.UrbanVoiceException;
import com.team2.urbanvoice.models.OfficerModel;

public interface OfficerService {

	
	Officer updateOfficer(OfficerModel officerCreateModel, Jurisdiction jury);

	
	Officer getOfficer(int officerId);

	
	List<Officer> getAllOfficers();

	
	String deleteOfficer(int officerId);


	Officer createOfficer(OfficerModel newOfficer, Jurisdiction jury)throws Exception, UrbanVoiceException;


	Officer findOfficerByEmail(String email);

	Officer findOfficerByPhoneNumber(String phone);

	
	Boolean isCurrentPasswordValid(String email, String currentPassword);

	void updatePassword(String email, String newPassword);


	Long countOfficers();

	Officer updateOfficerProfile(OfficerModel officerModel);


	Officer getOfficerByDetails(String email, String phone, int officerId);
  
}