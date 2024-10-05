package com.RealProject.Picture.Publishing.security;

import com.RealProject.Picture.Publishing.model.entity.User;
import com.RealProject.Picture.Publishing.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserServiceImpl userServiceImpl;

    public CustomAuthenticationSuccessHandler(UserServiceImpl theUserServiceImpl) {
        userServiceImpl = theUserServiceImpl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {


        log.info("In customAuthenticationSuccessHandler");

        String userName = authentication.getName();
        User user = userServiceImpl.findByUserName(userName);

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);



        // Check user roles
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                // Redirect to systems page for admin
                response.sendRedirect(request.getContextPath() + "/systems");
                return;
            }
        }

        // forward to home page
        response.sendRedirect(request.getContextPath() + "/userDashboard");
    }

    }


