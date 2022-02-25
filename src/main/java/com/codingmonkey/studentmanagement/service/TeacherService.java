package com.codingmonkey.studentmanagement.service;

import java.util.List;

import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;

public interface TeacherService {

  List<TeacherDTO> findAll();

  TeacherDTO findById(int teacherId);

  void save(TeacherEntity teacherEntity);

  void deleteById(int teacherId);
}
