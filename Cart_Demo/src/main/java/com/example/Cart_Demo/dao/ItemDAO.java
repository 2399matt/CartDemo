package com.example.Cart_Demo.dao;

import com.example.Cart_Demo.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemDAO {

    private final EntityManager entityManager;

    @Autowired
    public ItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Item> findAll() {
        return entityManager.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }

    public Item findByName(String name) {
        List<Item> item = entityManager.createQuery("SELECT i FROM Item i WHERE i.name=:name", Item.class)
                .setParameter("name", name)
                .getResultList();
        if (item.isEmpty()) {
            return null;
        } else {
            return item.get(0);
        }
    }

    public Item findById(int id) {
        return entityManager.find(Item.class, id);
    }

    public void save(Item item) {
        entityManager.persist(item);
    }

    public void updateItem(Item item) {
        entityManager.merge(item);
    }

}
