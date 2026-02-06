package com.inetz.receipt.repositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import com.inetz.receipt.entity.FeeStructure;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT fs FROM FeeStructure fs
        WHERE fs.student.studentId = :studentId
    """)
    Optional<FeeStructure> findByStudentIdForUpdate(Long studentId);

    Optional<FeeStructure> findByStudentStudentId(Long studentId);


    @Query("SELECT COALESCE(SUM(f.totalFees),0) FROM FeeStructure f")
    double getTotalFees();

    @Query("SELECT COALESCE(SUM(f.paidAmount),0) FROM FeeStructure f")
    double getTotalPaidAmount();

    @Query("SELECT COALESCE(SUM(f.pendingAmount),0) FROM FeeStructure f")
    double getTotalPendingAmount();
}
