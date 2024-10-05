package com.RealProject.Picture.Publishing.repository;

import com.RealProject.Picture.Publishing.model.entity.Role;
import com.RealProject.Picture.Publishing.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleEnum name);

}
