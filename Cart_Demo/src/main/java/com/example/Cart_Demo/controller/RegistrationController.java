package com.example.Cart_Demo.controller;

import com.example.Cart_Demo.entity.CustomUser;
import com.example.Cart_Demo.service.RegistrationService;
import com.example.Cart_Demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;

    @Autowired
    public RegistrationController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String regForm(Model model) {
        model.addAttribute("user", new CustomUser());
        return "reg-form";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute CustomUser user, @RequestParam("passCheck") String passCheck, Model model) {
        if(!userService.userNameCheck(user.getUsername())) {
            model.addAttribute("userError", "Username is already in use!");
            return "reg-form";
        }
        else if(!passCheck.equals(user.getPassword())) {
            model.addAttribute("passError", "Passwords do not match.");
            return "reg-form";
        }
        else{
            registrationService.addUser(user);
            return "login-form";
        }
    }
}
