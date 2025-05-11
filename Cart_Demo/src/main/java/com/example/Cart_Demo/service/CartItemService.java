package com.example.Cart_Demo.service;

import com.example.Cart_Demo.dao.CartItemDAO;
import com.example.Cart_Demo.entity.Cart;
import com.example.Cart_Demo.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemDAO cartItemDAO;

    @Autowired
    public CartItemService(CartItemDAO cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }

    @Transactional
    public void saveItem(CartItem item, Cart cart) {
        item.setCart(cart);
        cartItemDAO.saveItem(item);
    }

    @Transactional
    public void deleteById(int id) {
        cartItemDAO.deleteById(id);
    }

    @Transactional
    public void deleteItem(CartItem item) {
        cartItemDAO.deleteItem(item);
    }

    public CartItem findById(int id) {
        return cartItemDAO.findById(id);
    }
}
