package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepository;

  public TeacherServiceImpl(final TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  @Override
  public List<TeacherEntity> findAll() {
    return teacherRepository.findAll();
  }

  @Override
  public TeacherEntity findById(final int teacherId) {
    Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);

    if (teacher.isPresent()) {
      return teacher.get();
    }
    throw new RuntimeException("Did not find teacher with id: " + teacherId);
  }

  @Override
  public void save(final TeacherEntity teacherEntity) {
    teacherRepository.save(teacherEntity);
  }

  @Override
  public void deleteById(final int teacherId) {
    teacherRepository.deleteById(teacherId);
  }
}
