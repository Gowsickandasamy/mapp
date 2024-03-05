package com.team2.urbanvoice.exception;

public class UrbanVoiceException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message = "";

	public UrbanVoiceException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}