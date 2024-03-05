package com.team2.urbanvoice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team2.urbanvoice.exception.UrbanVoiceException;
import com.team2.urbanvoice.models.EmailModel;
import com.team2.urbanvoice.models.UserModel;
import com.team2.urbanvoice.service.ComplaintService;
import com.team2.urbanvoice.service.EmailSenderService;
import com.team2.urbanvoice.service.UserService;

import jakarta.validation.Valid;

@Controller
public class LoginController {
	private static final String LANDING_PAGE_VIEW = "urbanvoiceHome";
	private static final String LOGIN_PAGE_VIEW = "login";
	private static final String REGISTRATION_PAGE_VIEW = "registration";

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private ComplaintService complaintService;

	@GetMapping("/urbanvoice/registration")
	public String showRegistrationForm() {
		return REGISTRATION_PAGE_VIEW;
	}

	@PostMapping("/urbanvoice/registration")
	public String handleRegistration(@Valid @ModelAttribute("user") UserModel userModel, Model model,
			BindingResult bindingResult) {
		try {
			userService.createUser(userModel);
			model.addAttribute("sMessage", "You are registered Successfully !");

			return REGISTRATION_PAGE_VIEW;
		} catch (UrbanVoiceException e) {
			model.addAttribute("eMessage", e.getMessage());

			return REGISTRATION_PAGE_VIEW;
		}
	}

	@PostMapping("/urbanvoice/generateEmailOtp")
    public ResponseEntity<String> generateEmailOtp(@RequestBody String email) {
        log.info("Generate OTP method is called");
        log.info("Email: {}", email);
        
        try {
            String otp = emailSenderService.getotp(email);
            log.info("Generated OTP: {}", otp);
            return new ResponseEntity<>("OTP Sent Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to generate OTP: {}", e.getMessage());
            return new ResponseEntity<>("Failed to generate OTP", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/urbanvoice/validateOtp")
    public ResponseEntity<String> validateOtp(@RequestBody EmailModel validateOtpDto) {
        String email = validateOtpDto.getEmail();
        String otp = validateOtpDto.getOtp();
        log.info("Validate OTP method is called");

        try {
            emailSenderService.validate(email, otp);
            return new ResponseEntity<>("OTP Verified Successfully", HttpStatus.OK);
        } catch (UrbanVoiceException e) {
            log.error("OTP invalid or expired: {}", e.getMessage());
            return new ResponseEntity<>("OTP is invalid or expired", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error occurred during OTP validation: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred during OTP validation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@GetMapping("/urbanvoice")
	public String landingPage(Model model) {
		long usersCount = userService.countUsers();
		long complaintsCount = complaintService.countComplaints();
		long closedComplaintsCount = complaintService.getCountOfClosedComplaints();
		model.addAttribute("usersCount", usersCount);
		model.addAttribute("complaintsCount", complaintsCount);
		model.addAttribute("closedComplaintsCount", closedComplaintsCount);

		return LANDING_PAGE_VIEW;
	}
	
	@GetMapping("/urbanvoice/login")
	public String loginPage() {
		return LOGIN_PAGE_VIEW;
	}
}