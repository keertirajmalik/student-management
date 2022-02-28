package com.codingmonkey.studentmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.dto.TeacherDTO;

public interface TeacherService {

  List<TeacherDTO> findAll();

  TeacherDTO findById(int teacherId);

  void deleteById(int teacherId);

  ResponseEntity<TeacherDTO> saveTeacherDetails(TeacherDTO teacherDTO);
}
