package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.entity.Teacher;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepository;

  public TeacherServiceImpl(final TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  @Override
  public List<Teacher> findAll() {
    return teacherRepository.findAll();
  }

  @Override
  public Teacher findById(final int teacherId) {
    Optional<Teacher> teacher = teacherRepository.findById(teacherId);

    if (teacher.isPresent()) {
      return teacher.get();
    }
    throw new RuntimeException("Did not find teacher with id: " + teacherId);
  }

  @Override
  public void save(final Teacher teacher) {
    teacherRepository.save(teacher);
  }

  @Override
  public void deleteById(final int teacherId) {
    teacherRepository.deleteById(teacherId);
  }
}
