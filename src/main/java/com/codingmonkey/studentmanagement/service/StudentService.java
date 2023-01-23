package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.StudentDTO;

public interface StudentService {

  List<StudentDTO> findAll();

  ResponseEntity<StudentDTO> saveStudentDetails(StudentDTO studentDTO);

  void deleteById(int studentId);

  List<StudentDTO> findByFirstNameAndLastName(String firstName, String lastName);
}
