package com.inetz.receipt.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;
import com.inetz.receipt.entity.*;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {

    Optional<FeeStructure> findByStudentStudentId(Long studentId);

    @Query("SELECT COALESCE(SUM(f.totalFees),0) FROM FeeStructure f")
    double getTotalFees();

    @Query("SELECT COALESCE(SUM(f.paidAmount),0) FROM FeeStructure f")
    double getTotalPaidAmount();

    @Query("SELECT COALESCE(SUM(f.pendingAmount),0) FROM FeeStructure f")
    double getTotalPendingAmount();
}

