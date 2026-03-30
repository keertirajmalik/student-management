package com.codingmonkey.studentmanagement.service;

import com.codingmonkey.studentmanagement.domain.dto.request.StudentRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.StudentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

  List<StudentResponseDTO> getAllStudents();

  Page<StudentResponseDTO> getAllStudents(Pageable pageable);

  StudentResponseDTO saveStudentDetails(StudentRequestDTO studentDTO);

  StudentResponseDTO updateStudentDetails(int studentId, StudentRequestDTO studentDTO);

  void deleteById(int studentId);

  Page<StudentResponseDTO> getStudentByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

  Page<StudentResponseDTO> getStudentByFirstName(String firstName, Pageable pageable);

  Page<StudentResponseDTO> getStudentByLastName(String lastName, Pageable pageable);
}
