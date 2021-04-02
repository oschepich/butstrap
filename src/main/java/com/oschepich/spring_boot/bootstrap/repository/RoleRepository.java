package com.oschepich.spring_boot.bootstrap.repository;


import com.oschepich.spring_boot.bootstrap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);

}
