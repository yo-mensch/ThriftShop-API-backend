package com.example.demo.response;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private int id;
    private PaymentResponse payment;
    private List<ProductResponse> products;
    private String orderStatus;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.payment = new PaymentResponse(order.getPayment());
        this.orderStatus = order.getOrderStatus().toString();
        List<ProductResponse> productList = new ArrayList<>();
        for(Product product: order.getProducts()){
            productList.add(new ProductResponse(product));
        }
        this.products = productList;
    }
}
