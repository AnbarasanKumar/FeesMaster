package com.inetz.receipt.serviceImpl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import com.inetz.receipt.entity.*;
import com.inetz.receipt.model.ReceiptRequest;
import com.inetz.receipt.model.ReceiptResponse;
import com.inetz.receipt.repositroy.*;

import com.inetz.receipt.service.ReceiptService;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

    private final FeeStructureRepository feeStructureRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReceiptResponse createReceipt(ReceiptRequest request, String createdBy) {

        FeeStructure fee = feeStructureRepository
                .findByStudentStudentId(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Fee structure not found"));

        FeePayment payment = new FeePayment();
        payment.setFeeStructure(fee);
        payment.setAmount(request.getAmount());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setRemarks(request.getRemarks());
        payment.setPaymentDate(LocalDate.now());
        payment.setCreatedBy(createdBy);

        fee.getPayments().add(payment);
        fee.setPaidAmount(payment.getAmount());
        fee.setPendingAmount(fee.getTotalFees() - fee.getPaidAmount());

        feePaymentRepository.save(payment);

        return mapToResponse(payment);
    }

    @Override
    public ReceiptResponse getReceiptById(Long id) {
        return feePaymentRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
    }

    @Override
    public Page<ReceiptResponse> getAllReceipts(int page, int size) {
        return feePaymentRepository.findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    @Override
    public void deleteReceipt(Long id) {
        feePaymentRepository.deleteById(id);
    }


    @Override
    public long getTotalReceipts() {
        return feePaymentRepository.count();
    }

    @Override
    public long getTodayReceipts() {
        return feePaymentRepository.totalReceiptsToday(LocalDate.now());
    }

    private ReceiptResponse mapToResponse(FeePayment p) {
        return new ReceiptResponse(
                p.getPaymentId(),
                p.getFeeStructure().getStudent().getStudentName(),
                p.getAmount(),
                p.getPaymentMode(),
                p.getRemarks(),
                p.getPaymentDate(),
                p.getCreatedBy()
        );
    }
}
