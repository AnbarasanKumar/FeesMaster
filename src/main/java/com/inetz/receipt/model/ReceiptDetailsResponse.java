package com.inetz.receipt.model;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetailsResponse {

    private Long receiptNumber;
    private String studentName;
    private double amount;
    private String paymentMode;
    private String remarks;
    private LocalDate paymentDate;
    private String createdBy;
}
