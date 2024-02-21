package com.login.repository;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}