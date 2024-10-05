package com.RealProject.Picture.Publishing.repository;

import com.RealProject.Picture.Publishing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoJPA extends JpaRepository<User,Integer> {

    User findByUsername(String username);


}
