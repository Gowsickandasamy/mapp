package com.team2.urbanvoice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.team2.urbanvoice.entities.User;
import com.team2.urbanvoice.models.UserModel;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	User save(UserModel usermodel);
	
	@Query("SELECT U FROM User U WHERE (email=?1 OR phone=?2) AND id <> ?3")
	User getUserByDetails(String email, String phone, int userId);
}
