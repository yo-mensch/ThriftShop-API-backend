package com.example.demo.restController;

import com.example.demo.request.OrderRequest;
import com.example.demo.response.OrderResponse;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponse> getAllOrders(){
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable String id){
        return orderService.findOrderById(Integer.parseInt(id));
    }

    @PostMapping("/{buyerID}")
    ResponseEntity<HttpStatus> createOrder(@RequestBody OrderRequest orderRequest, @PathVariable String buyerID){
        orderService.createOrder(orderRequest, Integer.parseInt(buyerID));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateOrder(@RequestBody OrderRequest orderRequest, @PathVariable String id){
        orderService.updateOrder(orderRequest,Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id){
        orderService.deleteOrder(Integer.parseInt(id));
    }
}
