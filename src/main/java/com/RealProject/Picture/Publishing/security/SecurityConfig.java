package com.RealProject.Picture.Publishing.security;



import com.RealProject.Picture.Publishing.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig   {



    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain
            (HttpSecurity http , CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/pictures/**").permitAll()
                                .requestMatchers("/userDashboard/**").hasRole("USER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().authenticated()
                )

                .formLogin(form ->
                        form
                                .loginPage("/signIn")
                                .loginProcessingUrl("/authenticateTheUser")
                             //   .defaultSuccessUrl("/userDashboard", true)
                                .successHandler(customAuthenticationSuccessHandler)
                                .permitAll()
                ).exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public OpenEntityManagerInViewFilter openSessionInViewFilter() { // to skip LazyInitializationException
        return new OpenEntityManagerInViewFilter();
    }

    @Bean
   public InMemoryUserDetailsManager inMemoryUserDetailsManager(){

        UserDetails admin = User.withUsername("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);




    }

}

