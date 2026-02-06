package com.inetz.receipt.feescontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inetz.receipt.entity.FeePayment;
import com.inetz.receipt.entity.FeeStructure;
import com.inetz.receipt.model.FeePaymentRequest;
import com.inetz.receipt.model.FeeStructureRequest;
import com.inetz.receipt.response.ApiResponse;
import com.inetz.receipt.service.FeeService;

@RestController
@RequestMapping("/api/fees")
@CrossOrigin("*")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/structure")
    public ApiResponse<FeeStructure> createFeeStructure(
            @RequestBody FeeStructureRequest request) {

        return new ApiResponse<>(
                "Fee structure created successfully",
                feeService.createFeeStructure(request),
                true
        );
    }

    @GetMapping("/structure/{studentId}")
    public ApiResponse<FeeStructure> getFeeDetails(
            @PathVariable Long studentId) {

        return new ApiResponse<>(
                "Fee details fetched successfully",
                feeService.getFeeStructureByStudent(studentId),
                true
        );
    }
    
    @PostMapping("/pay")
    public ApiResponse<FeePayment> payFees(
            @RequestBody FeePaymentRequest request,
            Authentication authentication) {

        String createdBy = authentication.getName();

        return new ApiResponse<>(
                "Fee paid successfully",
                feeService.payFees(request, createdBy),
                true
        );
    }

}
