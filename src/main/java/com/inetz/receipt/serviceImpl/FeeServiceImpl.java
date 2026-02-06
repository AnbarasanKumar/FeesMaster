package com.inetz.receipt.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetz.receipt.entity.FeePayment;
import com.inetz.receipt.entity.FeeStructure;
import com.inetz.receipt.entity.Student;
import com.inetz.receipt.model.FeeStructureRequest;
import com.inetz.receipt.repositroy.FeePaymentRepository;
import com.inetz.receipt.repositroy.FeeStructureRepository;
import com.inetz.receipt.repositroy.StudentRepository;
import com.inetz.receipt.service.FeeService;

@Service
@Transactional
public class FeeServiceImpl implements FeeService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    // ================================
    // CREATE FEE STRUCTURE
    // ================================
    @Override
    public FeeStructure createFeeStructure(FeeStructureRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (feeStructureRepository.findByStudentStudentId(student.getStudentId()).isPresent()) {
            throw new RuntimeException("Fee structure already exists");
        }

        FeeStructure feeStructure = new FeeStructure();
        feeStructure.setStudent(student);
        feeStructure.setTotalFees(request.getTotalFees());
        feeStructure.setPaidAmount(0);
        feeStructure.setPendingAmount(request.getTotalFees());

        return feeStructureRepository.save(feeStructure);
    }

    @Override
    @Transactional(readOnly = true)
    public FeeStructure getFeeStructureByStudent(Long studentId) {
        return feeStructureRepository.findByStudentStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Fee structure not found"));
    }
    

    @Override
    public FeePayment payFees(Long studentId, Double amount, String createdBy) {

        // 🔒 LOCK ROW (FOR UPDATE)
        FeeStructure feeStructure = feeStructureRepository
                .findByStudentIdForUpdate(studentId)
                .orElseThrow(() -> new RuntimeException("Fee structure not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (amount > feeStructure.getPendingAmount()) {
            throw new IllegalArgumentException("Payment exceeds pending amount");
        }

        // 💰 UPDATE AMOUNTS
        feeStructure.setPaidAmount(
                feeStructure.getPaidAmount() + amount
        );

        feeStructure.setPendingAmount(
                feeStructure.getTotalFees() - feeStructure.getPaidAmount()
        );

        // 💾 SAVE UPDATED FEES
        feeStructureRepository.save(feeStructure);

        // 🧾 CREATE PAYMENT RECORD
        FeePayment payment = new FeePayment();
        payment.setFeeStructure(feeStructure);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setCreatedBy(createdBy);

        return feePaymentRepository.save(payment);
    }
}
