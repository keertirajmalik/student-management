package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
import com.codingmonkey.studentmanagement.repositories.StudentRepository;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;

@Service
public class StudentServiceImpl implements StudentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
  private final StudentRepository studentRepository;
  private final SubjectRepository subjectRepository;
  @Autowired
  ModelMapper modelMapper;

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
  public ResponseEntity<StudentDTO> saveStudentDetails(final StudentDTO studentDTO) {

    String logPrefix = "# " + " #saveStudentDetails(): ";
    LOGGER.info("{} Enter ", logPrefix);

    validateFieldsInRequestDto(studentDTO);

    return saveStudentDetailsToDB(studentDTO);
  }

  private ResponseEntity<StudentDTO> saveStudentDetailsToDB(final StudentDTO studentDTO) {
    StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);

    studentRepository.save(studentEntity);

    return new ResponseEntity(studentEntity, HttpStatus.CREATED);

  }

  private void validateFieldsInRequestDto(final StudentDTO studentDTO) {
    if (Optional.ofNullable(studentDTO.getFirst_name()).isEmpty()) {
      throw new StudentDetailsException("Student First Name cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getFirst_name().isEmpty()) {
      throw new StudentDetailsException("Student First Name cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(studentDTO.getLast_name()).isEmpty()) {
      throw new StudentDetailsException("Student Last Name cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getLast_name().isEmpty()) {
      throw new StudentDetailsException("Student Last Name cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(studentDTO.getEmail()).isEmpty()) {
      throw new StudentDetailsException("Student email cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getEmail().isEmpty()) {
      throw new StudentDetailsException("Student email cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(studentDTO.getMobile_number()).isEmpty()) {
      throw new StudentDetailsException("Student mobile number cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getClass_number() < 1) {
      throw new StudentDetailsException("Student class number cannot be less than 1", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getClass_number() > 11) {
      throw new StudentDetailsException("Student class number cannot be greater than 11", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getMobile_number().toString().length() != 10) {
      throw new StudentDetailsException("Student mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void deleteById(final int studentId) {
    studentRepository.deleteById(studentId);
  }
}
