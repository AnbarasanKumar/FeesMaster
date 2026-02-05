package com.inetz.receipt.service;

import org.springframework.data.domain.Page;

import com.inetz.receipt.entity.Student;
import com.inetz.receipt.model.*;

public interface StudentService {

	Student createStudent(StudentRequest request, String createdBy);

    Page<Student> getAllStudents(int page, int size);

    Student getStudentById(Long id);

    Student updateStudent(Long id, StudentRequest request, String modifiedBy);

    long getTotalStudents();
    
    long getTodayRegisteredStudents();

    void deleteStudent(Long id);
    
    Page<StudentDetailsResponse> getAllStudentsWithReceipts(int page, int size);

    StudentDetailsResponse getStudentWithReceipts(Long id);

}
