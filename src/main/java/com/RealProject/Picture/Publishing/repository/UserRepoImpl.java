package com.RealProject.Picture.Publishing.repository;

import com.RealProject.Picture.Publishing.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class UserRepoImpl implements UserRepo{


    private final EntityManager entityManager;
    public UserRepoImpl( EntityManager entityManager) {
        this.entityManager = entityManager;
    }



@Override
    public User existsByEmail(String email) {
        return entityManager.find(User.class ,email);

    }

    @Override
    public Optional<User> findByEmail(String email) {

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            System.out.println("Query: " + query.getSingleResult());
            return Optional.of(query.getSingleResult());

        } catch (NoResultException e) {
            return Optional.empty(); // Return empty Optional if no user is found
        }

    }

    @Override
    public User findByUsername(String username) {
        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("select u from User u where username=:uName ", User.class);
        theQuery.setParameter("uName", username);
        return theQuery.getSingleResult();


    }

    @Override
    @Transactional
    public void save(User user) {

            TypedQuery<User> theQuery = entityManager.createQuery("insert into User (email,username,password,roles)  " +
                    " values(:email,:uName,:password,:roles) ", User.class);
            theQuery.setParameter("uName", user.getUsername());
            theQuery.setParameter("email", user.getEmail());
            theQuery.setParameter("password", user.getPassword());
          //  theQuery.setParameter("user_id", user.getId());
            theQuery.setParameter("roles", user.getRoles());
            theQuery.executeUpdate();
    }

   @Override
    @Transactional
    public void persist(User user){

      /*  Query query = entityManager.createNativeQuery("INSERT INTO users (id, username, email, password) VALUES (:user_id, :uName, :email, :password)");
        query.setParameter("user_id", user.getId());
        query.setParameter("uName", user.getUsername());
        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());
        query.executeUpdate();*/
    }
}
