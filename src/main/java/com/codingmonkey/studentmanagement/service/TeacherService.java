package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;

public interface TeacherService {

  List<TeacherResponseDTO> getAllTeachers();

  List<TeacherResponseDTO> getTeacherByFirstNameAndLastName(String firstName, String lastName);

  void deleteById(int teacherId);

  ResponseEntity<TeacherResponseDTO> saveTeacherDetails(TeacherRequestDTO teacherDTO);

  ResponseEntity<TeacherResponseDTO> updateTeacherDetails(int teacherId, TeacherRequestDTO teacherDTO);
}
