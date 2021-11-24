package com.example.demo.restController;

import com.example.demo.request.PaymentRequest;
import com.example.demo.response.PaymentResponse;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments(){
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable String id){
        return paymentService.findPaymentById(Integer.parseInt(id));
    }

    @GetMapping("/{orderID}")
    public PaymentResponse getPaymentByOrderId(@PathVariable String orderID){
        return paymentService.findPaymentByOrderId(Integer.parseInt(orderID));
    }

    @PostMapping("/{userID}/{orderID}")
    ResponseEntity<HttpStatus> createPayment(@RequestBody PaymentRequest paymentRequest, @PathVariable String userID, @PathVariable String orderID){
        paymentService.createPayment(paymentRequest, Integer.parseInt(userID),Integer.parseInt(orderID));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updatePayment(@RequestBody PaymentRequest paymentRequest, @PathVariable String id){
        paymentService.updatePayment(paymentRequest,Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable String id){
        paymentService.deletePayment(Integer.parseInt(id));
    }
}
