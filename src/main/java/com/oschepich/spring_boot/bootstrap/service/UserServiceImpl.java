package com.oschepich.spring_boot.bootstrap.service;

import com.oschepich.spring_boot.bootstrap.model.Role;
import com.oschepich.spring_boot.bootstrap.model.User;
import com.oschepich.spring_boot.bootstrap.repository.RoleRepository;
import com.oschepich.spring_boot.bootstrap.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    //  метод передачи всего списка user-ов
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //  метод добавления или изменение одного user-а в список(ке)
    @Override
    @Transactional
    public void saveUser(User user) {
            if (user.getPassword()==""){
                user.setPassword(userRepository.findUserByEmail(user.getEmail()).getPassword());
            }
            else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user);
   }

    //  метод нахождения одного user-а в списке
    @Override
    public User show(Long id) {
        User user=null;
        Optional <User> optional=userRepository.findById(id);
        if (optional.isPresent()){
            user=optional.get();
        }
        return user;
    }

    //  метод удаления одного user-а из списка
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Role getRole(String role) {
        return roleRepository.findRoleByRole(role);
    }

    @Override
    public List<Role> getListRole() {
        return roleRepository.findAll();
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("USER not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), grantedAuthorities);
    }

}
