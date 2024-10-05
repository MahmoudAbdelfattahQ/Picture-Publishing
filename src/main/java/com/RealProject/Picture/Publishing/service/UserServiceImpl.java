package com.RealProject.Picture.Publishing.service;

import com.RealProject.Picture.Publishing.model.dto.UserDto;
import com.RealProject.Picture.Publishing.model.entity.Role;
import com.RealProject.Picture.Publishing.model.entity.User;
import com.RealProject.Picture.Publishing.model.enums.RoleEnum;
import com.RealProject.Picture.Publishing.repository.RoleRepo;
import com.RealProject.Picture.Publishing.repository.UserRepo;
import com.RealProject.Picture.Publishing.repository.UserRepoJPA;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepoJPA userRepoJPA;
    private final RoleRepo roleRepository;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl( UserRepoJPA userRepoJPA,UserRepo userRepo,
                            RoleRepo roleRepository,  BCryptPasswordEncoder passwordEncoder) {
        this.userRepoJPA = userRepoJPA;
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(@NotNull UserDto userDto) {

        String password = passwordEncoder.encode(userDto.getPassword());
        Optional<Role> optionalRole = roleRepository.findByRoleName(RoleEnum.ROLE_USER);//as default
        User user = new User();

        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(password);
        user.setRoles(Arrays.asList(optionalRole.get()));
        userRepoJPA.save(user);
        log.info("User saved!");

    }

    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<User> findByEmail(String userEmail) {
        return userRepo.findByEmail(userEmail);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        if (user == null) {
            log.info("User Not found by username {} method : ", username);
            throw new UsernameNotFoundException("User not found");

        } else {
            Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());
            log.info("User {} is authenticated with roles {}", username, authorities);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    authorities);
        }

    }


    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(@NotNull Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(String.valueOf(role.getRoleName()));
            log.info("User roles : {}", tempAuthority);
            authorities.add(tempAuthority);
        }
        return authorities;
    }


}
