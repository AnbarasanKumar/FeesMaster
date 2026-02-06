package com.inetz.receipt.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.*;
import com.inetz.receipt.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
        SELECT COUNT(s)
        FROM Student s
        WHERE s.createdAt BETWEEN :start AND :end
    """)
    long countStudentsRegisteredToday(
        LocalDateTime start,
        LocalDateTime end
    );
}
