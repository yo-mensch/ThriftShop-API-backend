package com.example.demo.service;

import com.example.demo.hibController.UserHibController;
import com.example.demo.model.User;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private UserHibController userHibController = new UserHibController(entityManagerFactory);

    @Autowired
    public UserService() {
    }

    public List<UserResponse> findAll(){
        List<User> userList = userHibController.getUserList();
        List<UserResponse> responseList = new ArrayList<>();

        for (User user : userList) {
            responseList.add(new UserResponse(user));
        }
        return responseList;
    }

    public UserResponse findUserById(int id){
        return new UserResponse(userHibController.getUserById(id));
    }

    public void createUser(final UserRequest userRequest){
        //pavaliduoti ar egzistuoja username/email
        String encryptedPassword = Hashing.sha256()
                .hashString(userRequest.getPassword(), StandardCharsets.UTF_8)
                .toString();
        User user = new User(userRequest.getUsername(), encryptedPassword, userRequest.getEmail());
        userHibController.createUser(user);
    }

    public void updateUser(UserRequest userUpdated, int id) {

        User user = userHibController.getUserById(id);
        String encryptedPassword = Hashing.sha256()
                .hashString(userUpdated.getPassword(), StandardCharsets.UTF_8)
                .toString();

        user.setUsername(userUpdated.getUsername());
        user.setPassword(encryptedPassword);
        user.setEmail(userUpdated.getEmail());

        userHibController.updateUser(user);
    }

    public void deleteUser(int id){
        userHibController.deleteUser(id);
    }
}
