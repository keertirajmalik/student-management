package com.codingmonkey.studentmanagement.service;

import java.util.List;

import com.codingmonkey.studentmanagement.domain.dto.request.StudentRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.StudentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

  List<StudentResponseDTO> getAllStudents();

  Page<StudentResponseDTO> getAllStudents(Pageable pageable);

  StudentResponseDTO saveStudentDetails(StudentRequestDTO studentDTO);

  StudentResponseDTO updateStudentDetails(int studentId, StudentRequestDTO studentDTO);

  void deleteById(int studentId);

  List<StudentResponseDTO> getStudentByFirstNameAndLastName(String firstName, String lastName);

  List<StudentResponseDTO> getStudentByFirstName(String firstName);

  List<StudentResponseDTO> getStudentByLastName(String lastName);
}
