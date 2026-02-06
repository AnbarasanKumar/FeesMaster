package com.inetz.receipt.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetz.receipt.entity.Student;
import com.inetz.receipt.model.*;
import com.inetz.receipt.repositroy.StudentRepository;
import com.inetz.receipt.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentResponse createStudent(StudentRequest request, String createdBy) {

        Student student =
                modelMapper.map(request, Student.class);

        student.setCreatedBy(createdBy);

        Student savedStudent = studentRepository.save(student);

        return modelMapper.map(savedStudent, StudentResponse.class);
    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentUpdateRequest request) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        modelMapper.map(request, student); 

        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentResponse.class);
    }

    @Override
    public StudentResponse getStudentById(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return modelMapper.map(student, StudentResponse.class);
    }

    @Override
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentRepository.delete(student);
    }
}
