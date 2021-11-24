package com.example.demo.service;

import com.example.demo.hibController.AddressHibController;
import com.example.demo.hibController.OrderHibController;
import com.example.demo.hibController.PaymentHibController;
import com.example.demo.hibController.UserHibController;
import com.example.demo.model.Order;
import com.example.demo.model.Payment;
import com.example.demo.model.User;
import com.example.demo.model.enumerations.PaymentStatus;
import com.example.demo.request.PaymentRequest;
import com.example.demo.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private PaymentHibController paymentHibController = new PaymentHibController(entityManagerFactory);
    private UserHibController userHibController = new UserHibController(entityManagerFactory);
    private OrderHibController orderHibController = new OrderHibController(entityManagerFactory);

    @Autowired
    public PaymentService() {
    }

    public List<PaymentResponse> findAll(){
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for(Payment payment: paymentHibController.getPaymentList()){
            paymentResponses.add(new PaymentResponse(payment));
        }
        return paymentResponses;
    }

    public PaymentResponse findPaymentById(int id){
        return new PaymentResponse(paymentHibController.getPaymentById(id));
    }

    public PaymentResponse findPaymentByOrderId(int id){
        return new PaymentResponse(paymentHibController.getPaymentByOrderId(id));
    }

    public void createPayment(PaymentRequest paymentRequest, int userID, int orderID){
        User user = userHibController.getUserById(userID);
        Order order = orderHibController.getOrderById(orderID);

        Payment payment = new Payment(paymentRequest.getAmount(), paymentRequest.getPaymentStatus(), user, order);
        paymentHibController.createPayment(payment);
    }

    public void updatePayment(PaymentRequest paymentRequest, int id){
        Payment payment = paymentHibController.getPaymentById(id);

        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(PaymentStatus.valueOf(paymentRequest.getPaymentStatus()));

        paymentHibController.updatePayment(payment);
    }

    public void deletePayment(int id){
        paymentHibController.deletePayment(id);
    }
}
