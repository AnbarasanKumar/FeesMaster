package com.inetz.receipt.model;

import java.time.LocalDate;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class FeePaymentRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Payment mode is required")
    private String paymentMode;

    private String remarks;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;
}
