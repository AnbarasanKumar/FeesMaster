package com.inetz.receipt.model;

import java.time.*;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailsResponse {

    private Long studentId;
    private String studentName;
    private long phoneNumber;
    private String courseType;
    private String courseDomain;
    private LocalDate batchJoinDate;

    private double totalFees;
    private double paidAmount;
    private double pendingAmount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private String createdBy;
    private String modifiedBy;

    private List<ReceiptDetailsResponse> receipts;
}
