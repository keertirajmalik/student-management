package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;

public interface StudentService {

  List<StudentResponseDTO> getAllStudents();

  ResponseEntity<StudentResponseDTO> saveStudentDetails(StudentRequestDTO studentDTO);

  ResponseEntity<StudentResponseDTO> updateStudentDetails(int studentId, StudentRequestDTO studentDTO);

  void deleteById(int studentId);

  List<StudentResponseDTO> getStudentByFirstNameAndLastName(String firstName, String lastName);

  List<StudentResponseDTO> getStudentByFirstName(String firstName);

  List<StudentResponseDTO> getStudentByLastName(String lastName);
}
