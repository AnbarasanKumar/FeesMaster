package com.inetz.receipt.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptRequest {

    private Long studentId;
    
    private String studentName;

    private double amount;
    
    private String paymentMode;

    private String remarks;
    
}
