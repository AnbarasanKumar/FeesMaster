package com.inetz.receipt.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.*;
import java.util.*;
import com.inetz.receipt.entity.*;
import org.springframework.data.domain.Pageable;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    @Query("SELECT COUNT(p) FROM FeePayment p")
    long totalReceipts();

    @Query("""
        SELECT COALESCE(SUM(p.amount),0)
        FROM FeePayment p
        WHERE p.paymentDate = :today
    """)
    double getTodayCollectedAmount(LocalDate today);

    @Query("SELECT COUNT(p) FROM FeePayment p WHERE p.paymentDate = :today")
    long totalReceiptsToday(LocalDate today);

    @Query("""
        SELECT p FROM FeePayment p
        ORDER BY p.paymentDate DESC, p.paymentId DESC
    """)
    List<FeePayment> findRecentReceipts(Pageable pageable);
}
