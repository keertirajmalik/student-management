package com.codingmonkey.studentmanagement.service;

import java.util.List;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.Student;

public interface StudentService {

  List<StudentDTO> findAll();

  Student findById(int studentId);

  void save(Student student);

  void deleteById(int studentId);
}
