package com.team2.urbanvoice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team2.urbanvoice.entities.Roles;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {

	@Query("SELECT R FROM Roles R WHERE roleName=:role")
	public Roles findRolesByroleName(@Param("role") String role);
}
