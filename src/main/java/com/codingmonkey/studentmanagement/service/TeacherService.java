package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.dto.TeacherDTO;

public interface TeacherService {

  List<TeacherDTO> findAll();

  List<TeacherDTO> findByFirstNameAndLastName(String firstName, String lastName);

  void deleteById(int teacherId);

  ResponseEntity<TeacherDTO> saveTeacherDetails(TeacherDTO teacherDTO);
}
