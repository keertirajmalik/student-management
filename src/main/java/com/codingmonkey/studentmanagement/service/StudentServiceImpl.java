package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.Student;
import com.codingmonkey.studentmanagement.entity.Subject;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.repositories.StudentRepository;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final SubjectRepository subjectRepository;

  public StudentServiceImpl(final StudentRepository studentRepository, final SubjectRepository subjectRepository) {
    this.studentRepository = studentRepository;
    this.subjectRepository = subjectRepository;
  }

  @Override
  public List<StudentDTO> findAll() {
    return studentRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  private StudentDTO convertEntityToDto(Student student) {
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.setFirst_name(student.getFirst_name());
    studentDTO.setLast_name(student.getLast_name());
    studentDTO.setEmail(student.getEmail());
    studentDTO.setMobile_number(student.getMobile_number());
    studentDTO.setClassNumber(student.getClassNumber());

    List<Subject> subjects = subjectRepository.findSubjectsByClass_number(student.getClassNumber())
        .orElseThrow(() -> new NotFoundException("Subjects list not found for student: " + student.getFirst_name()));

    studentDTO.setSubjects(subjects.stream().map(Subject::getSubject).collect(Collectors.toList()));

    return studentDTO;
  }

  @Override
  public StudentDTO findById(final int studentId) {
    Optional<Student> student = studentRepository.findById(studentId);

    if (student.isPresent()) {
      return convertEntityToDto(student.get());
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
