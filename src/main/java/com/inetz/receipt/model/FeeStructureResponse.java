package com.inetz.receipt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeeStructureResponse {

    private Long studentId;
    private double totalFees;
    private double paidAmount;
    private double pendingAmount;
}
