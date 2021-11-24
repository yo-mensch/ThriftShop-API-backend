package com.example.demo.service;

import com.example.demo.hibController.OrderHibController;
import com.example.demo.hibController.ProductHibController;
import com.example.demo.hibController.UserHibController;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.enumerations.OrderStatus;
import com.example.demo.request.OrderRequest;
import com.example.demo.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private OrderHibController orderHibController = new OrderHibController(entityManagerFactory);
    private UserHibController userHibController = new UserHibController(entityManagerFactory);
    private ProductHibController productHibController = new ProductHibController(entityManagerFactory);

    @Autowired
    public OrderService() {
    }

    public List<OrderResponse> findAll(){
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order: orderHibController.getOrdersList()){
            orderResponses.add(new OrderResponse(order));
        }
        return orderResponses;
    }

    public List<OrderResponse> findOrdersByUserId(int userID){
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order: orderHibController.getOrdersByUserId(userID)){
            orderResponses.add(new OrderResponse(order));
        }
        return orderResponses;
    }

    public OrderResponse findOrderById(int id){
        for(Order order: orderHibController.getOrdersList()){
            if(order.getId() == id)
                return new OrderResponse(order);
        }
        return null;
    }

    public void createOrder(OrderRequest orderRequest, int buyerID){
        List<Product> productsInOrder = new ArrayList<>();
        User user = userHibController.getUserById(buyerID);

        for(Product product: user.getShoppingCart().getProducts()){
            productsInOrder.add(product);
        }

        Order order = new Order(productsInOrder, user, orderRequest.getStatus());
        orderHibController.createOrder(order);
    }

    public void updateOrder(OrderRequest orderRequest, int id){
        Order order = orderHibController.getOrderById(id);

        order.setOrderStatus(OrderStatus.valueOf(orderRequest.getStatus()));

        orderHibController.updateOrder(order);
    }

    public void deleteOrder(int id){
        orderHibController.deleteOrder(id);
    }
}
