package com.hms.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;
    private Double amountPaid;
    private String paymentMethod;  // e.g., "Credit Card", "Cash"
    private String transactionId;  // Payment transaction ID

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)  // ✅ Delete on cascade only    @JoinColumn(name = "discount_id")
    @JoinColumn(name = "booking_id", nullable = false)
    private SlotBooking booking;

//    @OneToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)  // ✅ Delete on cascade only    @JoinColumn(name = "discount_id")
    private Discount discount;

//    @OneToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)  // ✅ Delete on cascade only    @JoinColumn(name = "insurance_id")
    private InsuranceOption insurance;

    private Boolean paymentDone;
}
