package com.example.demo.service;

import com.example.demo.hibController.ProductHibController;
import com.example.demo.hibController.ShoppingCartHibController;
import com.example.demo.hibController.UserHibController;
import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.response.ShoppingCartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);
    private UserHibController userHibController = new UserHibController(entityManagerFactory);
    private ProductHibController productHibController = new ProductHibController(entityManagerFactory);

    @Autowired
    public ShoppingCartService() {
    }

    public List<ShoppingCartResponse> findAll(){
        List<ShoppingCartResponse> shoppingCarts = new ArrayList<>();

        for(ShoppingCart shoppingCart: shoppingCartHibController.getShoppingCartsList()){
            shoppingCarts.add(new ShoppingCartResponse(shoppingCart));
        }

        return shoppingCarts;
    }

    public ShoppingCartResponse findCartByUserId(int userId){
        return new ShoppingCartResponse(userHibController.getUserById(userId).getShoppingCart());
    }

    public ShoppingCartResponse findCartById(int id){
        return new ShoppingCartResponse(shoppingCartHibController.getCartById(id));
    }

    public void createShoppingCart(int userId){
        User user = userHibController.getUserById(userId);
        ShoppingCart shoppingCart = new ShoppingCart(new ArrayList<>(),0.0f,user);
        shoppingCartHibController.createShoppingCart(shoppingCart);
    }

    public void updateShoppingCart(int cartID, int productID){
        Product product = productHibController.getProductById(productID);
        ShoppingCart shoppingCart = shoppingCartHibController.getCartById(cartID);

        if(shoppingCart.getProducts().contains(product)){
            shoppingCart.getProducts().remove(product);
        } else {
            shoppingCart.getProducts().add(product);
        }

        float price = 0.0f;

        for(Product pr: shoppingCart.getProducts()){
            price += pr.getPrice();
        }

        shoppingCart.setTotalPrice(price);
        shoppingCartHibController.updateShoppingCart(shoppingCart);
    }

    public void deleteShoppingCart(int id){
        shoppingCartHibController.deleteShoppingCart(id);
    }
}
