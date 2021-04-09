package com.oschepich.spring_boot.bootstrap.service;

import com.oschepich.spring_boot.bootstrap.model.Role;
import com.oschepich.spring_boot.bootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService<T>{
     List<T> getAllUser();

    void saveUser(User user);

    T show(Long id);

     void deleteUser(Long id);

    User findUserByEmail(String email);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

}
