package com.inetz.receipt.model;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class FeeStructureRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @Positive(message = "Total fees must be greater than zero")
    private double totalFees;
}
