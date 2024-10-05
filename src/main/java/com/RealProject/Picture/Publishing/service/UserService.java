package com.RealProject.Picture.Publishing.service;

import com.RealProject.Picture.Publishing.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

     User findByUserName(String userName);

}
