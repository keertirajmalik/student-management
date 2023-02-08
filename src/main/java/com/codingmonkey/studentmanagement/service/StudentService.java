package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.dto.StudentDTO;

public interface StudentService {

  List<StudentDTO> getAllStudents();

  ResponseEntity<StudentDTO> saveStudentDetails(StudentDTO studentDTO);

  void deleteById(int studentId);

  List<StudentDTO> getStudentByFirstNameAndLastName(String firstName, String lastName);
}
