package com.example.demo.response;

import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartResponse {
    private int id;
    private float totalPrice;
    private List<ProductResponse> products;

    public ShoppingCartResponse(ShoppingCart shoppingCart) {
        this.id = shoppingCart.getId();
        this.totalPrice = shoppingCart.getTotalPrice();
        List<ProductResponse> productList = new ArrayList<>();
        for(Product product: shoppingCart.getProducts()){
            productList.add(new ProductResponse(product));
        }
        this.products = productList;
    }
}
