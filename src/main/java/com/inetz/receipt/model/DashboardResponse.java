package com.inetz.receipt.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private double totalFees;
    private double totalPaidAmount;
    private double totalPendingAmount;

    private long totalStudents;
    private long totalReceipts;

    private List<ReceiptDetailsResponse> receipts;
}
