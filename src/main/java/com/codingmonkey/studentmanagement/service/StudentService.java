package com.codingmonkey.studentmanagement.service;

import java.util.List;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;

public interface StudentService {

  List<StudentDTO> findAll();

  StudentDTO findById(int studentId);

  void save(StudentEntity studentEntity);

  void deleteById(int studentId);
}
