package com.example.Cart_Demo.service;

import com.example.Cart_Demo.entity.Cart;
import com.example.Cart_Demo.entity.CustomUser;
import com.example.Cart_Demo.entity.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public RegistrationService(JdbcUserDetailsManager jdbcUserDetailsManager, BCryptPasswordEncoder bCryptPasswordEncoder,
                               UserService userService, CartService cartService) {
        this.userDetailsManager = jdbcUserDetailsManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.cartService = cartService;
    }

    public void addUser(WebUser webUser) {
        CustomUser user = new CustomUser(webUser.getUsername());
        userService.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartService.saveCart(cart);
        UserDetails userDetails = User.builder()
                .username(webUser.getUsername())
                .password(bCryptPasswordEncoder.encode(webUser.getPassword()))
                .roles("USER")
                .build();
        userDetailsManager.createUser(userDetails);
    }
}
