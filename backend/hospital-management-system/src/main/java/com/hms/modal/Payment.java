package com.hms.modal;


import com.hms.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    // Payment mode can be CASH, VISA, MASTERCARD, AMEX, or OTHER.
    @Enumerated(EnumType.STRING)
    private PaymentMode mode;

    // Total payment amount
    private Double totalPaymentAmount;

    // Optional comments regarding the payment
    private String commentsForPayment;
}
