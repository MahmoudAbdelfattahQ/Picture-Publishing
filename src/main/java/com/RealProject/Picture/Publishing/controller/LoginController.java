package com.RealProject.Picture.Publishing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/signIn")
    public String signIn(Model model) {
        return "login-Page";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
//    @GetMapping("/logout")
//    public String logout() {
//        return "home";
//    }


}

