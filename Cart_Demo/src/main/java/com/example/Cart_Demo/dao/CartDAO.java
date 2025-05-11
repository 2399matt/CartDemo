package com.example.Cart_Demo.dao;

import com.example.Cart_Demo.entity.Cart;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAO {

    private final EntityManager entityManager;

    @Autowired
    public CartDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Cart findUserCart(String username) {
        return entityManager.createQuery("SELECT c FROM Cart c LEFT JOIN FETCH c.items LEFT JOIN FETCH c.user WHERE c.user.username=:username", Cart.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public Cart updateCart(Cart cart) {
        return entityManager.merge(cart);
    }

    public void saveCart(Cart cart) {
        entityManager.persist(cart);
    }
}
