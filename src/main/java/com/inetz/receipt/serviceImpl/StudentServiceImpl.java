package com.inetz.receipt.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import com.inetz.receipt.entity.*;
import com.inetz.receipt.model.*;
import com.inetz.receipt.repositroy.FeeStructureRepository;
import com.inetz.receipt.repositroy.StudentRepository;
import com.inetz.receipt.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FeeStructureRepository feeStructureRepository;
    private final ModelMapper modelMapper;

    @Override
    public Student createStudent(StudentRequest request, String createdBy) {

        Student student = modelMapper.map(request, Student.class);
        student.setCreatedBy(createdBy);

        FeeStructure fee = new FeeStructure();
        fee.setStudent(student);
        fee.setTotalFees(request.getTotalFees());
        fee.setPaidAmount(request.getPaidAmount());
        fee.setPendingAmount(
                request.getTotalFees() - request.getPaidAmount()
        );

        student.setFeeStructure(fee);

        return studentRepository.save(student);
    }

    @Override
    public Page<Student> getAllStudents(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public Student updateStudent(Long id, StudentRequest request, String modifiedBy) {

        Student student = getStudentById(id);

        student.setStudentName(request.getStudentName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setCourseType(request.getCourseType());
        student.setCourseDomain(request.getCourseDomain());
        student.setBatchJoinDate(request.getBatchJoinDate());
        student.setModifiedBy(modifiedBy);

        FeeStructure fee = student.getFeeStructure();
        if (fee == null) {
            fee = new FeeStructure();
            fee.setStudent(student);
            student.setFeeStructure(fee);
        }
        fee.setTotalFees(request.getTotalFees());
        fee.setPaidAmount(request.getPaidAmount());
        fee.setPendingAmount(Math.max(0, fee.getTotalFees() - fee.getPaidAmount()));

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public long getTotalStudents() {
        return studentRepository.count();
    }

    @Override
    public long getTodayRegisteredStudents() {
        LocalDate today = LocalDate.now();
        return studentRepository.countStudentsRegisteredToday(
                today.atStartOfDay(),
                today.atTime(23, 59, 59)
        );
    }

    @Override
    public Page<StudentDetailsResponse> getAllStudentsWithReceipts(int page, int size) {

        return studentRepository.findAll(PageRequest.of(page, size))
                .map(this::mapStudentDetails);
    }

    @Override
    public StudentDetailsResponse getStudentWithReceipts(Long id) {
        return mapStudentDetails(getStudentById(id));
    }

    private StudentDetailsResponse mapStudentDetails(Student student) {

        FeeStructure fee = student.getFeeStructure();

        StudentDetailsResponse response =
                modelMapper.map(student, StudentDetailsResponse.class);

        if (fee != null) {
            response.setTotalFees(fee.getTotalFees());
            response.setPaidAmount(fee.getPaidAmount());
            response.setPendingAmount(fee.getPendingAmount());
            if (fee.getPayments() != null) {
                response.setReceipts(
                        fee.getPayments().stream()
                                .map(p -> modelMapper.map(p, ReceiptDetailsResponse.class))
                                .toList()
                );
            }
        }

        return response;
    }
}
