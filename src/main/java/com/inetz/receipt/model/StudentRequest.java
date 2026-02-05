package com.inetz.receipt.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    private String studentName;
    private long phoneNumber;
    private String courseType;
    private String courseDomain;
    private LocalDate batchJoinDate;
    private double totalFees;
    private double paidAmount;
    
    //private List<ReceiptRequest> receipts;
    
}
