package com.team2.urbanvoice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team2.urbanvoice.entities.Complaint;
import com.team2.urbanvoice.entities.Jurisdiction;
import com.team2.urbanvoice.entities.Officer;
import com.team2.urbanvoice.entities.Status;
import com.team2.urbanvoice.entities.User;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint, Integer> {

	List<Complaint> findByJuryComplaint(Jurisdiction jurisdiction);

	List<Complaint> getComplaintByOfficer(Officer officer);

	List<Complaint> getComplaintByUser(User user);
	
	@Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = :status")
    Long countByStatus(@Param("status") Status status);
}
