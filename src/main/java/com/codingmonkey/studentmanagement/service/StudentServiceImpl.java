package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
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

  private StudentDTO convertEntityToDto(StudentEntity studentEntity) {
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.setFirst_name(studentEntity.getFirst_name());
    studentDTO.setLast_name(studentEntity.getLast_name());
    studentDTO.setEmail(studentEntity.getEmail());
    studentDTO.setMobile_number(studentEntity.getMobile_number());
    studentDTO.setClassNumber(studentEntity.getClass_number());

    List<SubjectEntity> subjectEntities = subjectRepository.findSubjectsByClass_number(studentEntity.getClass_number())
        .orElseThrow(
            () -> new NotFoundException("Subjects list not found for studentEntity: " + studentEntity.getFirst_name()));

    studentDTO.setSubjects(subjectEntities.stream().map(SubjectEntity::getSubject).collect(Collectors.toList()));

    return studentDTO;
  }

  @Override
  public StudentDTO findById(final int studentId) {
    Optional<StudentEntity> student = studentRepository.findById(studentId);

    if (student.isPresent()) {
      return convertEntityToDto(student.get());
    }
    throw new RuntimeException("Did not find student id: " + studentId);
  }

  @Override
  public void save(final StudentEntity studentEntity) {
    studentRepository.save(studentEntity);
  }

  @Override
  public void deleteById(final int studentId) {
    studentRepository.deleteById(studentId);
  }
}
