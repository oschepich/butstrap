package com.oschepich.spring_boot.bootstrap.service;

import com.oschepich.spring_boot.bootstrap.model.Role;
import com.oschepich.spring_boot.bootstrap.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }
//    @Override
//    public Role getRoleById(Long id) {
//        return (Role) this.roleDao.getRoleById(id);
//    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByRole(name);
    }

    @Override
    public List<Role> getListRole() {
        return roleRepository.findAll();
    }


}
