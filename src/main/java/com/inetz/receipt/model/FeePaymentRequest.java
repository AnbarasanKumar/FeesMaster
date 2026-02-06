package com.inetz.receipt.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class FeePaymentRequest {

    private Long studentId;
    private double amount;
    private String paymentMode;
    private String remarks;
    private LocalDate paymentDate;
}
