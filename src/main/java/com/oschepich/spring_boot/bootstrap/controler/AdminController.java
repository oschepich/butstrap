package com.oschepich.spring_boot.bootstrap.controler;


import com.oschepich.spring_boot.bootstrap.model.Role;
import com.oschepich.spring_boot.bootstrap.model.User;
import com.oschepich.spring_boot.bootstrap.repository.UserRepository;
import com.oschepich.spring_boot.bootstrap.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final UserRepository userRepository;

    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //вывожу всех пользователей
    @GetMapping
    public ModelAndView getAllUsers(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject( "tecUser", this.userRepository.findUserByEmail(principal.getName()));
        modelAndView.addObject("allUser", userService.getAllUser());
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("admin");
        return modelAndView;
    }
    //создаю нового пользователя
    @GetMapping("/new")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        return "admin";
    }
    //создаю нового пользователя
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "roleSet", required = false) String[] roleSet) {
        user.setRoles(getAddRole(roleSet));
        userService.saveUser(user);
        return "redirect:/admin";
    }
    //редактирую пользователя
    @GetMapping("/edit/{id}")
    public ModelAndView getUserToEdit(@PathVariable Long id) {
        User user = (User) userService.show(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    //редактирую пользователя
    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roleSet", required = false) String[] roleSet) {
            user.setRoles(getAddRole(roleSet));
            userService.saveUser(user);
        return "redirect:/admin";
    }
    //удаляю пользователя
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Principal principal) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    private Set<Role> getAddRole(String[] roles) {
//        if (roles == null){
//            Set<Role> roleSet = new HashSet<>();
//            roleSet.add(userService.getRoleByName("ROLE_USER"));
//            return roleSet;
//        }
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(userService.getRole(role));
        }
        return roleSet;
    }

}

