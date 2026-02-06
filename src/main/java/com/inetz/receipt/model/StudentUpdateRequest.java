package com.inetz.receipt.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class StudentUpdateRequest {

    private String studentName;
    private long phoneNumber;
    private String courseType;
    private String courseDomain;
    private LocalDate batchJoinDate;
}
