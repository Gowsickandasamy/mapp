package com.team2.urbanvoice.service;

import java.util.List;

import com.team2.urbanvoice.entities.User;
import com.team2.urbanvoice.exception.UrbanVoiceException;
import com.team2.urbanvoice.models.UserModel;

public interface UserService {


	User updateUserProfile(UserModel usermodel);

	
	User getUser(int uid);


	User createUser(UserModel userModel) throws UrbanVoiceException;

	User findUserByEmail(String email);

	Boolean isCurrentPasswordValid(String email, String currentPassword);

	
	void updatePassword(String email, String newPassword);

	
	Long countUsers();
	

	List<User> getAllUsers();


	User getUserByDetails(String email, String phone, int userId);
  
}