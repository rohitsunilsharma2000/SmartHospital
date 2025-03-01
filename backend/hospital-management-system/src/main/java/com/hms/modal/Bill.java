package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double amountPaid = 0.0;
    private Double amountDue;
    private Boolean paymentDone = false;
    private String paymentMethod;  // e.g., "Credit Card", "Cash"
    private String transactionId;  // Payment transaction ID


    //Use Case: If a single booking can have multiple types of bills (e.g., OPD Bill,
    // Pharmacy Bill, etc.), the best approach is Solution 1 (@ManyToOne).
    @ManyToOne
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    // ✅ Delete on cascade only    @JoinColumn(name = "discount_id")
    @JoinColumn(name = "booking_id", nullable = false)
    private SlotBooking booking;

    //    @OneToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    // ✅ Delete on cascade only    @JoinColumn(name = "discount_id")
    private Discount discount;

    //    @OneToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    // ✅ Delete on cascade only    @JoinColumn(name = "insurance_id")
    private InsuranceOption insurance;


}
