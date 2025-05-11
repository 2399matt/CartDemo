package com.example.Cart_Demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customuser")
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy="user")
    private List<Order> orders;

    public CustomUser() {

    }

    public CustomUser(String username) {
        this.username = username;
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
