package com.inetz.receipt.model;

import java.time.LocalDate;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class StudentUpdateRequest {

    @NotBlank(message = "Student name is required")
    private String studentName;

    @Digits(integer = 10, fraction = 0, message = "Phone number must be 10 digits")
    private long phoneNumber;

    @NotBlank(message = "Course type is required")
    private String courseType;

    @NotBlank(message = "Course domain is required")
    private String courseDomain;

    @NotNull(message = "Batch join date is required")
    private LocalDate batchJoinDate;
}
