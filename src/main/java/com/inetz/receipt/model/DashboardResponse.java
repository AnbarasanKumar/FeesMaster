package com.inetz.receipt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private long totalStudents;
    private double totalAmount;
    private double totalPaidAmount;
    private double totalPendingAmount;
}
