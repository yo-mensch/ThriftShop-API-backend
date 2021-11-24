package com.example.demo.restController;

import com.example.demo.response.ShoppingCartResponse;
import com.example.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCarts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public List<ShoppingCartResponse> getAllShoppingCarts(){
        return shoppingCartService.findAll();
    }

    @GetMapping("/{id}")
    public ShoppingCartResponse getCartById(@PathVariable String id){
        return shoppingCartService.findCartById(Integer.parseInt(id));
    }

    @PostMapping("/{userID}")
    ResponseEntity<HttpStatus> createShoppingCart(@PathVariable String userID){
        shoppingCartService.createShoppingCart(Integer.parseInt(userID));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{cartID}/{productID}")
    ResponseEntity<HttpStatus> updateShoppingCart(@PathVariable String cartID, @PathVariable String productID){
        shoppingCartService.updateShoppingCart(Integer.parseInt(cartID), Integer.parseInt(productID));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping("/{id}")
    public void deleteShoppingCart(@PathVariable String id){
        shoppingCartService.deleteShoppingCart(Integer.parseInt(id));
    }
}
