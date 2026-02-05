package com.inetz.receipt.feescontroller;

import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.inetz.receipt.constant.Constant;
import com.inetz.receipt.entity.Student;
import com.inetz.receipt.model.*;
import com.inetz.receipt.response.ApiResponse;
import com.inetz.receipt.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/student")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Student>> createStudent(
            @Valid @RequestBody StudentRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        Student student = studentService.createStudent(request, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        Constant.STUDENT_CREATED_SUCCESS,
                        student,
                        true
                ));
    }


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        Constant.STUDENT_UPDATED_SUCCESS,
                        studentService.updateStudent(id, request, username),
                        true
                )
        );
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok(
                new ApiResponse<>(Constant.STUDENT_DELETED_SUCCESS, "Deleted", true));
    }
    /*
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getTotalStudents() {
        return ResponseEntity.ok(
            new ApiResponse<>("Total students fetched",
                    studentService.getTotalStudents(),
                    true)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/count/today")
    public ResponseEntity<ApiResponse<Long>> todayStudents() {
        return ResponseEntity.ok(
            new ApiResponse<>("Today students fetched",
                    studentService.getTodayRegisteredStudents(),
                    true)
        );
    }
	*/
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/getAll/details")
    public ResponseEntity<ApiResponse<PaginationResponse<StudentDetailsResponse>>> getAllStudentsWithReceipts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<StudentDetailsResponse> pageData =
                studentService.getAllStudentsWithReceipts(page, size);

        PaginationResponse<StudentDetailsResponse> response =
                new PaginationResponse<>(
                        pageData.getContent(),
                        pageData.getTotalElements(),
                        pageData.getTotalPages(),
                        pageData.getNumber(),
                        pageData.getSize(),
                        pageData.isFirst(),
                        pageData.isLast()
                );

        return ResponseEntity.ok(
                new ApiResponse<>("Students with receipt details fetched", response, true)
        );
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<StudentDetailsResponse>> getStudentWithReceipts(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>("Student details fetched",
                        studentService.getStudentWithReceipts(id),
                        true)
        );
    }


}
