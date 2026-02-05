package com.inetz.receipt.feescontroller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.inetz.receipt.constant.Constant;
import org.springframework.data.domain.PageRequest;

import com.inetz.receipt.model.*;
import com.inetz.receipt.repositroy.*;

import com.inetz.receipt.response.ApiResponse;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> dashboard() {

        DashboardResponse response = new DashboardResponse(
                feeStructureRepository.getTotalFees(),
                feeStructureRepository.getTotalPaidAmount(),
                feeStructureRepository.getTotalPendingAmount(),
                studentRepository.count(),
                feePaymentRepository.totalReceipts(),
                feePaymentRepository.findRecentReceipts(PageRequest.of(0,4))
                        .stream()
                        .map(p -> modelMapper.map(p, ReceiptDetailsResponse.class))
                        .toList()
        );

        return ResponseEntity.ok(
                new ApiResponse<>("Dashboard loaded", response, true)
        );
    }
}
