package com.example.demo.response;

import com.example.demo.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private int id;
    private float amount;
    private String paymentStatus;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getAmount();
        this.paymentStatus = payment.getPaymentStatus().toString();
    }
}
