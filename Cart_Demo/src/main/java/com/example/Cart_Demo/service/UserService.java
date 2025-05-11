package com.example.Cart_Demo.service;

import com.example.Cart_Demo.dao.UserDAO;
import com.example.Cart_Demo.entity.CustomUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public void save(CustomUser user) {
        userDAO.save(user);
    }

    @Transactional
    public void update(CustomUser user) {
        userDAO.update(user);
    }

    @Transactional
    public void delete(CustomUser user) {
        userDAO.delete(user);
    }

    public CustomUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public boolean userNameCheck(String username){
        return userDAO.userNameCheck(username);
    }
}
