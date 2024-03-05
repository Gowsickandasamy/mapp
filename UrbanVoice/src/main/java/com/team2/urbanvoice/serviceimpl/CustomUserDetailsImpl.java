package com.team2.urbanvoice.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team2.urbanvoice.customlogindetails.CustomAdminDetails;
import com.team2.urbanvoice.customlogindetails.CustomOfficerDetails;
import com.team2.urbanvoice.customlogindetails.CustomUserDetails;
import com.team2.urbanvoice.entities.Admin;
import com.team2.urbanvoice.entities.Officer;
import com.team2.urbanvoice.entities.User;
import com.team2.urbanvoice.repositories.AdminRepo;
import com.team2.urbanvoice.repositories.OfficerRepo;
import com.team2.urbanvoice.repositories.UserRepo;

@Service
public class CustomUserDetailsImpl implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private OfficerRepo officerRepo;
	@Autowired
	private AdminRepo adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		Officer officer = officerRepo.findByEmail(username);
		Admin admin = adminRepo.findByEmail(username);

		if (user != null) {
			return new CustomUserDetails(user);
		} else if (officer != null) {
			return new CustomOfficerDetails(officer);
		} else if (admin != null) {
			return new CustomAdminDetails(admin);
		} else {
			throw new UsernameNotFoundException("user not found");
		}
	}
}