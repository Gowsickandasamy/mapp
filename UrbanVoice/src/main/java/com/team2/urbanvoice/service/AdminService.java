package com.team2.urbanvoice.service;

import com.team2.urbanvoice.entities.Admin;
import com.team2.urbanvoice.exception.UrbanVoiceException;
import com.team2.urbanvoice.models.AdminModel;

public interface AdminService {

    
    Admin findAdminByEmail(String email);

    Boolean isCurrentPasswordValid(String email, String currentPassword);


   
    void updatePassword(String email, String newPassword);
    
   
    Admin createAdmin(AdminModel adminModel) throws UrbanVoiceException, Exception;

}