package com.example.Cart_Demo.dao;

import com.example.Cart_Demo.entity.CustomUser;
import com.example.Cart_Demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer> {

    boolean existsById(int id);

    @Query("SELECT o FROM Order o WHERE o.user.id=:id")
    List<Order> findUserOrders(@Param(value = "id") int id);


}
