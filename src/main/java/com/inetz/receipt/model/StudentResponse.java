package com.inetz.receipt.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class StudentResponse {

    private Long studentId;
    private String studentName;
    private String phoneNumber;
    private String courseType;
    private String courseDomain;
    private LocalDateTime batchJoinDate;
    private LocalDateTime createdAt;
}
