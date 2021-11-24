package com.example.demo.service;

import com.example.demo.hibController.ShoppingCartHibController;
import com.example.demo.hibController.UserHibController;
import com.example.demo.response.ShoppingCartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Service
public class ShoppingCartService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);
    private UserHibController userHibController = new UserHibController(entityManagerFactory);

    @Autowired
    public ShoppingCartService() {
    }

    public ShoppingCartResponse findCartByUserId(int userId){
        return new ShoppingCartResponse(userHibController.getUserById(userId).getShoppingCart());
    }
}
