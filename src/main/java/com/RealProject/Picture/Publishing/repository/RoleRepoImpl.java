package com.RealProject.Picture.Publishing.repository;

import com.RealProject.Picture.Publishing.model.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class RoleRepoImpl {
    private final EntityManager entityManager;

    public RoleRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Role findByUserName(String roleUser) {

        // retrieve/read from database using name
        TypedQuery<Role> theQuery = entityManager.createQuery("from Role where roleName=:role_name ", Role.class);
        theQuery.setParameter("role_name", roleUser);
        Role theRole = null;
        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }

        return theRole;
    }


}
