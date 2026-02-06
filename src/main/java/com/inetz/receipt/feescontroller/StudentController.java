package com.inetz.receipt.feescontroller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.inetz.receipt.model.*;
import com.inetz.receipt.response.ApiResponse;
import com.inetz.receipt.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping
    public ApiResponse<StudentResponse> createStudent(
            @Valid @RequestBody StudentRequest request,
            Authentication authentication) {

        String createdBy = authentication.getName();

        return new ApiResponse<>(
                "Student created successfully",
                studentService.createStudent(request, createdBy),
                true
        );
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateRequest request) {

        return new ApiResponse<>(
                "Student updated successfully",
                studentService.updateStudent(id, request),
                true
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getStudent(
            @PathVariable Long id) {

        return new ApiResponse<>(
                "Student fetched successfully",
                studentService.getStudentById(id),
                true
        );
    }

    @GetMapping
    public ApiResponse<List<StudentResponse>> getAllStudents() {

        return new ApiResponse<>(
                "Students fetched successfully",
                studentService.getAllStudents(),
                true
        );
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return new ApiResponse<>(
                "Student deleted successfully",
                null,
                true
        );
    }
}
