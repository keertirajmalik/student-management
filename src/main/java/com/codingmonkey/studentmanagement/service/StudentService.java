package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;

public interface StudentService {

  List<StudentDTO> getAllStudents();

  ResponseEntity<StudentDTO> saveStudentDetails(StudentDTO studentDTO);

  ResponseEntity<StudentDTO> updateStudentDetails(StudentDTO studentDTO);

  ResponseEntity<StudentEntity> deleteById(int studentId);

  List<StudentDTO> getStudentByFirstNameAndLastName(String firstName, String lastName);
}
