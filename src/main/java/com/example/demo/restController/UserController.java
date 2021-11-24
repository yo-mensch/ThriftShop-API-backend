package com.example.demo.restController;

import com.example.demo.request.UserRequest;
import com.example.demo.response.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final AddressService addressService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, ShoppingCartService shoppingCartService, ProductService productService, AddressService addressService, OrderService orderService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.addressService = addressService;
        this.orderService = orderService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable String id){
        return userService.findUserById(Integer.parseInt(id));
    }

    @GetMapping("/{id}/shoppingCart")
    public ShoppingCartResponse getShoppingCartByUserId(@PathVariable String id){
        return shoppingCartService.findCartByUserId(Integer.parseInt(id));
    }

    @GetMapping("/{id}/products")
    public List<ProductResponse> getProductsByAuthorId(@PathVariable String id){
        return productService.findProductsByAuthorId(Integer.parseInt(id));
    }

    @GetMapping("/{id}/address")
    public AddressResponse getAddressByUserId(@PathVariable String id){
        return addressService.findAddressByUserId(Integer.parseInt(id));
    }

    @GetMapping("/{id}/orders")
    public List<OrderResponse> getOrdersByUserId(@PathVariable String id){
        return orderService.findOrdersByUserId(Integer.parseInt(id));
    }

    @PostMapping
    ResponseEntity<HttpStatus> createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateUser(@RequestBody UserRequest userRequest, @PathVariable String id){
        userService.updateUser(userRequest, Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(Integer.parseInt(id));
    }
}
