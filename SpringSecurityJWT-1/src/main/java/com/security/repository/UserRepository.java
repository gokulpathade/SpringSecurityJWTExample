package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.entity.Role;
import com.security.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {

	Optional<User>findByEmail(String email);
	
      User findByRole(Role role);
}
