package com.RealProject.Picture.Publishing.controller;

import com.RealProject.Picture.Publishing.model.dto.UserDto;
import com.RealProject.Picture.Publishing.model.entity.User;
import com.RealProject.Picture.Publishing.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public RegistrationController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }



    @GetMapping("/showRegistrationForm")
    public String showFromRegister( Model model ) {
        model.addAttribute("userDto", new UserDto());
        return "/register/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String ProcessRegistrations(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult, HttpSession session, Model model) {

        String userEmail = userDto.getEmail();
        // form validation
        if (bindingResult.hasErrors()) {
            return "/register/registration-form";
        }
        Optional<User> check = userServiceImpl.findByEmail(userEmail);
        if (check.isPresent()) {
            model.addAttribute("userDto", new UserDto());
            model.addAttribute("registrationError", "User name already exists.");
            log.warn("User name already exists.");
            return "/register/registration-form";
        }
        /*
         * create user account and store in the database
         * */
        userServiceImpl.registerUser(userDto);
        System.out.println("processRegistrationForm is created successfully");
        // place user in the web http session for later use
        session.setAttribute("user", userDto);
        return "/register/registration-success";
    }


}
