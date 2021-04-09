package com.oschepich.spring_boot.bootstrap.service;


import com.oschepich.spring_boot.bootstrap.model.Role;

import java.util.List;

public interface RoleService<T>{

//    T getRoleById(Long id);

    Role getRoleByName(String name);

    public List<Role> getListRole();

}
