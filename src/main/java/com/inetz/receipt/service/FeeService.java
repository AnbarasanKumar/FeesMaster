package com.inetz.receipt.service;

import com.inetz.receipt.entity.FeePayment;
import com.inetz.receipt.entity.FeeStructure;
import com.inetz.receipt.model.FeeStructureRequest;

public interface FeeService {

    FeeStructure createFeeStructure(FeeStructureRequest request);

    FeeStructure getFeeStructureByStudent(Long studentId);
    
    FeePayment payFees(Long studentId, Double amount, String createdBy);
}
