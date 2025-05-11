package com.example.Cart_Demo.dao;

import com.example.Cart_Demo.entity.CartItem;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemDAO {

    private final EntityManager entityManager;

    @Autowired
    public CartItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveItem(CartItem item) {
        entityManager.persist(item);
    }

    public void deleteById(int id) {
        CartItem item = findById(id);
        if (item != null)
            entityManager.remove(item);
        else
            throw new RuntimeException("Item not found!");
    }

    public void deleteItem(CartItem item) {
        entityManager.remove(item);
    }

    public CartItem findById(int id) {
        return entityManager.find(CartItem.class, id);
    }


}
