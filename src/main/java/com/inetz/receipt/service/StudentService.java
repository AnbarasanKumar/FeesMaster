package com.inetz.receipt.service;

import java.util.List;
import com.inetz.receipt.model.*;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request, String createdBy);

    StudentResponse updateStudent(Long studentId, StudentUpdateRequest request);

    StudentResponse getStudentById(Long studentId);

    List<StudentResponse> getAllStudents();

    void deleteStudent(Long studentId);
}
