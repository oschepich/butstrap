package com.oschepich.spring_boot.bootstrap.controler;


import com.oschepich.spring_boot.bootstrap.model.Role;
import com.oschepich.spring_boot.bootstrap.model.User;
import com.oschepich.spring_boot.bootstrap.service.RoleService;
import com.oschepich.spring_boot.bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //вывожу всех пользователей
    @GetMapping
    public ModelAndView getAllUsers(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("tecUser", this.userService.findUserByEmail(principal.getName()));
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
       if (userService.findUserByEmail(user.getEmail()) == null) {
            user.setRoles(getAddRole(roleSet));
            userService.saveUser(user);
            return "redirect:/admin";
        }
        return "access";
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
    public String editUser(@ModelAttribute("user") User user, @PathVariable Long id,
                           @RequestParam(value = "roleSet", required = false) String[] roleSet) {
            user.setRoles(getAddRole(roleSet));
            userService.saveUser(user);
            return "redirect:/admin";

    }

    //удаляю пользователя
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Principal principal) {
        if (userService.findUserByEmail(principal.getName()).getId().equals(id)) {
            return "access";
        }
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    private Set<Role> getAddRole(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(roleService.getRoleByName(role));
        }
        return roleSet;
    }

}

