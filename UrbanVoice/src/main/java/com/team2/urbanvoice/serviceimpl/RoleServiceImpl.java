package com.team2.urbanvoice.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.urbanvoice.entities.Roles;
import com.team2.urbanvoice.repositories.RolesRepo;
import com.team2.urbanvoice.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RolesRepo rolesRepo;

	@Override
	public Roles getRoles(String role) {
		logger.debug("Retrieving Role based on Rolename: {}",role);
		
		return rolesRepo.findRolesByroleName(role);
	}
}
