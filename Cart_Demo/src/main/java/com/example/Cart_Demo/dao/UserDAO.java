package com.example.Cart_Demo.dao;

import com.example.Cart_Demo.entity.CustomUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(CustomUser user) {
        entityManager.persist(user);
    }

    public void update(CustomUser user) {
        entityManager.merge(user);
    }

    public void delete(CustomUser user) {
        entityManager.remove(user);
    }

    public CustomUser findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM CustomUser u WHERE u.username=:username", CustomUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new RuntimeException("No user found with username: " + username);
        }
    }

    public boolean userNameCheck(String username) {
        long exists = entityManager.createQuery("SELECT COUNT(c) FROM CustomUser c WHERE c.username=:username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return exists == 0;
    }
}
