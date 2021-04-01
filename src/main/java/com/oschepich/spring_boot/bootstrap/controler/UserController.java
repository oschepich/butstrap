package com.oschepich.spring_boot.bootstrap.controler;

import com.oschepich.spring_boot.bootstrap.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping (value = "/user")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", this.userRepository.findUserByEmail(principal.getName()));
        return "user";
    }
}
