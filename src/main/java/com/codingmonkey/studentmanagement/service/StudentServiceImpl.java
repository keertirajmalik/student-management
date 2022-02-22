package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.entity.Student;
import com.codingmonkey.studentmanagement.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  public StudentServiceImpl(final StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  @Override
  public Student findById(final int studentId) {
    Optional<Student> student = studentRepository.findById(studentId);

    if (student.isPresent()) {
      return student.get();
    }
    throw new RuntimeException("Did not find student id: " + studentId);
  }

  @Override
  public void save(final Student student) {
    studentRepository.save(student);
  }

  @Override
  public void deleteById(final int studentId) {
    studentRepository.deleteById(studentId);
  }
}
