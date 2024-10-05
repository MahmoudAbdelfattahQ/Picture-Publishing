package com.RealProject.Picture.Publishing.bootstrap;

import com.RealProject.Picture.Publishing.model.entity.Role;
import com.RealProject.Picture.Publishing.model.entity.User;
import com.RealProject.Picture.Publishing.model.enums.RoleEnum;
import com.RealProject.Picture.Publishing.repository.RoleRepo;
import com.RealProject.Picture.Publishing.repository.UserRepoJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

import static com.RealProject.Picture.Publishing.model.enums.RoleEnum.ROLE_ADMIN;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(RoleSeeder.class);
    private final RoleRepo roleRepository;
    private final UserRepoJPA userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RoleSeeder(RoleRepo roleRepository , UserRepoJPA userRepository , BCryptPasswordEncoder passwordEncoder, BCryptPasswordEncoder passwordEncoder1) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder1;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
       this.insertAdmin();

    }

    private void loadRoles() {
        Optional<Role> roleOptional = roleRepository.findByRoleName(RoleEnum.ROLE_USER);//check if the database is empty
        if (roleOptional.isEmpty()) {
            RoleEnum[] roles = new RoleEnum[]{ RoleEnum.ROLE_USER , ROLE_ADMIN ,RoleEnum.ROLE_GUESt};
            for (RoleEnum role : roles) {
                Role newRole = new Role();
                newRole.setRoleName(role);
                this.roleRepository.save(newRole);

            }

       }


    }
    private void  insertAdmin(){
        Optional<Role> adminRole = roleRepository.findByRoleName(ROLE_ADMIN);//as default
        User adminFound = userRepository.findByUsername("admin");

       if (adminFound == null) {
           User user = new User();
           user.setUsername("admin");
           user.setPassword(passwordEncoder.encode("admin123"));
           user.setEmail("admin@gmail.com");
           user.setRoles(Arrays.asList(adminRole.get()));
           userRepository.save(user);

       }

    }

}

