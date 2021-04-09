package com.oschepich.spring_boot.bootstrap.repository;

import com.oschepich.spring_boot.bootstrap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     User findUserByEmail(String email);
     boolean existsByEmail(String email);
  }
