package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.dto.StudentDTO;

public interface StudentService {

  List<StudentDTO> findAll();

  StudentDTO findById(int studentId);

  ResponseEntity<StudentDTO> saveStudentDetails(StudentDTO studentDTO);

  void deleteById(int studentId);
}
