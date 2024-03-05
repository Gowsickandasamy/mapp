package com.team2.urbanvoice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.team2.urbanvoice.entities.Jurisdiction;
import com.team2.urbanvoice.models.JurisdictionModel;

public interface JurisdictionService {

	Jurisdiction updateJurisdiction(JurisdictionModel jurisdiction);

	
	Jurisdiction getJurisdiction(int juryId);


	List<Jurisdiction> getAllJurisdictions();

	Jurisdiction createJurisdiction(JurisdictionModel juryModel);


	String deleteJurisdictionById(int jurisdictionId);

	
	Jurisdiction getJuryByDetails(String area, String ward, String layout);

	
	List<String> getWardsByArea(String area);


	List<String> getLayoutByWard(String ward);

	
	List<String> getDistinctArea();

	Long countJurisdictions();


}