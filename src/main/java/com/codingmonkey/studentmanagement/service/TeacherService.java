package com.codingmonkey.studentmanagement.service;

import java.util.List;

import com.codingmonkey.studentmanagement.entity.Teacher;

public interface TeacherService {

  List<Teacher> findAll();

  Teacher findById(int teacherId);

  void save(Teacher teacher);

  void deleteById(int teacherId);
}
