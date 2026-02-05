package com.inetz.receipt.entity;


import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "fee_structure")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeStructureId;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private double totalFees;
    private double paidAmount;
    private double pendingAmount;

    @OneToMany(mappedBy = "feeStructure",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<FeePayment> payments = new ArrayList<>();
}
