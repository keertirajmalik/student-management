package com.codingmonkey.studentmanagement.service;

import java.util.List;

import com.codingmonkey.studentmanagement.entity.TeacherEntity;

public interface TeacherService {

  List<TeacherEntity> findAll();

  TeacherEntity findById(int teacherId);

  void save(TeacherEntity teacherEntity);

  void deleteById(int teacherId);
}
