package com.example.Cart_Demo.service;

import com.example.Cart_Demo.dao.CartDAO;
import com.example.Cart_Demo.entity.Cart;
import com.example.Cart_Demo.entity.CartItem;
import com.example.Cart_Demo.entity.CustomUser;
import com.example.Cart_Demo.entity.Item;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDAO cartDAO;
    private final CartItemService cartItemService;

    @Autowired
    public CartService(CartDAO cartDAO, CartItemService cartItemService) {
        this.cartDAO = cartDAO;
        this.cartItemService = cartItemService;
    }

    public Cart findUserCart(int id) {
        return cartDAO.findUserCart(id);
    }

    @Transactional
    public void saveCart(Cart cart) {
        cartDAO.saveCart(cart);
    }

    @Transactional
    public Cart updateCart(Cart cart) {
        return cartDAO.updateCart(cart);
    }

    @Transactional
    public void addItemToCart(Cart cart, Item item){
        CartItem cartItem = new CartItem(item.getName(), item.getPrice());
        cartItem.setCart(cart);
        cart.addItem(cartItem);
        updateCart(cart);
    }

    @Transactional
    public void removeItemFromCart(Cart cart, int id){
        CartItem cartItem = cartItemService.findById(id);
        cart.removeItem(cartItem);
        cartItemService.deleteItem(cartItem);
        updateCart(cart);
    }
}
