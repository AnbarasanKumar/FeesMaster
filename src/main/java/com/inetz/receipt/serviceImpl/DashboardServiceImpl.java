package com.inetz.receipt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetz.receipt.model.DashboardResponse;
import com.inetz.receipt.repositroy.FeePaymentRepository;
import com.inetz.receipt.repositroy.FeeStructureRepository;
import com.inetz.receipt.repositroy.StudentRepository;
import com.inetz.receipt.service.DashboardService;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Override
    public DashboardResponse getOverallDashboard() {

        long totalStudents = studentRepository.count();
        double totalAmount = feeStructureRepository.getTotalFees();
        double totalPaidAmount = feeStructureRepository.getTotalPaidAmount();
        double totalPendingAmount = feeStructureRepository.getTotalPendingAmount();

        double totalGpayAmount = feePaymentRepository.getTotalGpayAmount();
        double totalCashAmount = feePaymentRepository.getTotalCashAmount();

        return new DashboardResponse(
                totalStudents,
                totalAmount,
                totalPaidAmount,
                totalPendingAmount,
                totalGpayAmount,
                totalCashAmount
        );
    }
}
