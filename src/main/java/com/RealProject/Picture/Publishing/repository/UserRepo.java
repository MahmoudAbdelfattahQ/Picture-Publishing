package com.RealProject.Picture.Publishing.repository;

import com.RealProject.Picture.Publishing.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo  {

    User existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
    @Transactional
    void save(User user);

    @Transactional
    void persist(User user);
}
