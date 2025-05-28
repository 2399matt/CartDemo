package com.example.Cart_Demo.service;

import com.example.Cart_Demo.entity.Cart;
import com.example.Cart_Demo.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public RegistrationService(BCryptPasswordEncoder bCryptPasswordEncoder,
                               UserService userService, CartService cartService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.cartService = cartService;
    }

    public void addUser(CustomUser user) {
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartService.saveCart(cart);
    }
}
