package com.inetz.receipt.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyDashboardResponse {

	private double todayTotalFees;
    private double todayPaidAmount;
    private double todayPendingAmount;

    private long todayRegisteredStudents;
    private long todayReceipts;
    private double todayCollectedAmount;

    private List<ReceiptDetailsResponse> todayReceiptsList;
}
