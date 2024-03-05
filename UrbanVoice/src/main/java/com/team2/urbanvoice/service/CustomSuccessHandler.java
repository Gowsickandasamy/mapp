package com.team2.urbanvoice.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		var authourities = authentication.getAuthorities();
		var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();

		if (roles.orElse("").equals("ADMIN")) {
			response.sendRedirect("/urbanvoice/admin/track-complaints");
		} else if (roles.orElse("").equals("USER")) {
			response.sendRedirect("/urbanvoice/user/track-complaints");
		} else if (roles.orElse("").equals("OFFICER")) {
			response.sendRedirect("/urbanvoice/officer/track-complaints");
		} else {
			response.sendRedirect("/error");
		}
	}
}
