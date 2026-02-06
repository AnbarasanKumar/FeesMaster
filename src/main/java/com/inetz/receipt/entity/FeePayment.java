package com.inetz.receipt.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "fee_payment")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_structure_id")
    @JsonIgnore
    private FeeStructure feeStructure;

    private double amount;
    private String paymentMode;
    private String remarks;
    private LocalDate paymentDate;
    private String createdBy;
}
