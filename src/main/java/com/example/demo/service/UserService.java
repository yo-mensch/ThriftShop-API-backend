package com.example.demo.service;

import com.example.demo.hibController.UserHibController;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Service
public class UserService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private UserHibController userHibController = new UserHibController(entityManagerFactory);

    @Autowired
    public UserService() {
    }

    public List<User> findAll(){
        return userHibController.getUserList();
    }
}
