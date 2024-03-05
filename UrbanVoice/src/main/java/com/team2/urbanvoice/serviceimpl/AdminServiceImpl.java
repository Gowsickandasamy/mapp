package com.team2.urbanvoice.serviceimpl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.team2.urbanvoice.entities.Admin;
import com.team2.urbanvoice.entities.Roles;
import com.team2.urbanvoice.exception.UrbanVoiceException;
import com.team2.urbanvoice.models.AdminModel;
import com.team2.urbanvoice.repositories.AdminRepo;
import com.team2.urbanvoice.repositories.RolesRepo;
import com.team2.urbanvoice.service.AdminService;
import com.team2.urbanvoice.service.EmailSenderService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdminServiceImpl implements AdminService {
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RolesRepo rolesrepo;
	@Autowired
	private EmailSenderService emailService;

	@Override
	public Admin findAdminByEmail(String email) {
		logger.debug("Retrieving Admin using Email: {}", email);

		return adminRepo.findByEmail(email);
	}

	@Override
	public Boolean isCurrentPasswordValid(String email, String currentPassword) {
		logger.debug("Checking if {}'s current Password is Valid !", email);
		Admin admin = adminRepo.findByEmail(email);

		return admin != null && passwordEncoder.matches(currentPassword, admin.getPassword());
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		logger.debug("Updating Password for {} !", email);
		Admin admin = adminRepo.findByEmail(email);

		if (admin != null) {
			admin.setPassword(passwordEncoder.encode(newPassword));
			adminRepo.save(admin);
		}
	}

	@Override
	public Admin createAdmin(AdminModel adminModel) throws Exception, UrbanVoiceException{
		Admin existingAdmin = adminRepo.findByEmail(adminModel.getEmail());
		if (Objects.isNull(existingAdmin)) {
			logger.info("Creating New Admin !");
			Admin admin = new Admin(adminModel.getAdminName(), adminModel.getEmail(), adminModel.getPhone());
			admin.setRegisteredDate(LocalDateTime.now());
			Roles role = rolesrepo.findRolesByroleName("ADMIN");
			admin.setRole(role);
			String firstThreeLetters = adminModel.getAdminName().substring(0, 3);
			String result = firstThreeLetters.substring(0, 1).toUpperCase() + firstThreeLetters.substring(1);
			String lastFourDigits = admin.getPhone().substring(admin.getPhone().length() - 4);
			String finalPassword = result + "@" + lastFourDigits;
			String password = passwordEncoder.encode(finalPassword);
			admin.setPassword(password);
			logger.info("Created New Admin !");
			logger.debug("Admin creation");
			adminRepo.save(admin);
			emailService.sendEmailToAdmin(admin);

			return adminRepo.save(admin);
		} else {
			logger.error("Admin with Email Id already Exists !");
			throw new UrbanVoiceException("Admin with Email Id already Exists !");
			
		}
	}
}
