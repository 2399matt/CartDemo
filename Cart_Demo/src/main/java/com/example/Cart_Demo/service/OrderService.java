package com.example.Cart_Demo.service;

import com.example.Cart_Demo.dao.OrderDAO;
import com.example.Cart_Demo.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OrderService {

    private final OrderDAO orderDAO;
    private final CartItemService cartItemService;
    private final ItemService itemService;

    @Autowired
    public OrderService(OrderDAO orderDAO, CartItemService cartItemService, ItemService itemService) {
        this.orderDAO = orderDAO;
        this.cartItemService = cartItemService;
        this.itemService = itemService;
    }

    public boolean existsById(int id) {
        return orderDAO.existsById(id);
    }

    public List<Order> findUserOrders(int id) {
        return orderDAO.findUserOrders(id);
    }

    @Transactional
    public void saveOrder(Order order) {
        orderDAO.save(order);
    }

    public Order findById(int id) {
        return orderDAO.findById(id).orElse(null);
    }

    public int generateOrderNumber() {
        Random random = new Random();
        int orderNumber = 1000 + random.nextInt(9000);
        if (!existsById(orderNumber)) {
            return orderNumber;
        } else {
            return generateOrderNumber();
        }
    }

    @Transactional
    public Order createNewOrder(Cart cart) {
        int total = 0;
        Order newOrder = new Order();
        newOrder.setId(generateOrderNumber());
        newOrder.setUser(cart.getUser());
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(cartItem.getName(), cartItem.getPrice());
            orderItem.setOrder(newOrder);
            newOrder.addItem(orderItem);
            total += cartItem.getPrice();
            Item item = itemService.findByName(cartItem.getName());
            item.setStock(item.getStock() - 1);
            itemService.updateItem(item);
            cartItemService.deleteItem(cartItem);
        }
        newOrder.setTotal(total);
        saveOrder(newOrder);
        return newOrder;
    }
}
