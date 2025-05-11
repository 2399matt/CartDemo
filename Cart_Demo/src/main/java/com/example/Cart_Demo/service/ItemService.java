package com.example.Cart_Demo.service;

import com.example.Cart_Demo.dao.ItemDAO;
import com.example.Cart_Demo.entity.Item;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemDAO itemDAO;

    @Autowired
    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public List<Item> findAll() {
        return itemDAO.findAll();
    }

    public Item findByName(String name) {
        return itemDAO.findByName(name);
    }

    public Item findById(int id) {
        return itemDAO.findById(id);
    }

    @Transactional
    public void save(Item item) {
        itemDAO.save(item);
    }

    @Transactional
    public void updateItem(Item item) {
        itemDAO.updateItem(item);
    }
}
