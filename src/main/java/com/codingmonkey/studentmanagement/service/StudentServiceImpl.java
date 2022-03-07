package com.codingmonkey.studentmanagement.service;

import java.util.Comparator;
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
    studentDTO.setStudentId(studentEntity.getStudentId());
    studentDTO.setFirstName(studentEntity.getFirstName());
    studentDTO.setLastName(studentEntity.getLastName());
    studentDTO.setEmail(studentEntity.getEmail());
    studentDTO.setMobileNumber(studentEntity.getMobileNumber());
    studentDTO.setClassNumber(studentEntity.getClassNumber());
    studentDTO.setRollNumber(studentEntity.getRollNumber());

    List<SubjectEntity> subjectEntities = subjectRepository.findSubjectsByClassNumber(studentEntity.getClassNumber())
        .orElseThrow(
            () -> new NotFoundException("Subjects list not found for studentEntity: " + studentEntity.getFirstName()));

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

    studentEntity.setRollNumber(getRollNumber(studentDTO));
    studentRepository.save(studentEntity);

    return new ResponseEntity(studentEntity, HttpStatus.CREATED);

  }

  private int getRollNumber(final StudentDTO studentDTO) {
    String logPrefix = "# " + " #saveStudentDetails(): ";
    LOGGER.info("{} Getting max roll number of class [{}] new student belong to ", logPrefix,
        studentDTO.getClassNumber());

    final Optional<List<StudentEntity>> optionalStudentEntityList = studentRepository.findByClassNumber(
        studentDTO.getClassNumber());

    int rollNumber = 0;

    /**
     * Since when classNumber has no student Optional[[]] is returned which passes the optionalStudentEntityList.isPresent() check
     * So replaced with optionalStudentEntityList.get().size() > 0 check
     * Since we are just trying to get the list size shouldn't cause any exception.
     */

    if (optionalStudentEntityList.get().size() > 0) {
      List<StudentEntity> studentEntityList = optionalStudentEntityList.get();

      rollNumber = studentEntityList.stream()
          .max(Comparator.comparing(StudentEntity::getRollNumber))
          .get()
          .getRollNumber();
    }

    return rollNumber + 1;
  }

  private void validateFieldsInRequestDto(final StudentDTO studentDTO) {
    if (Optional.ofNullable(studentDTO.getFirstName()).isEmpty()) {
      throw new StudentDetailsException("Student First Name cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getFirstName().isEmpty()) {
      throw new StudentDetailsException("Student First Name cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(studentDTO.getLastName()).isEmpty()) {
      throw new StudentDetailsException("Student Last Name cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getLastName().isEmpty()) {
      throw new StudentDetailsException("Student Last Name cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(studentDTO.getEmail()).isEmpty()) {
      throw new StudentDetailsException("Student email cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getEmail().isEmpty()) {
      throw new StudentDetailsException("Student email cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(studentDTO.getMobileNumber()).isEmpty()) {
      throw new StudentDetailsException("Student mobile number cannot be null", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getClassNumber() < 1) {
      throw new StudentDetailsException("Student class number cannot be less than 1", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getClassNumber() > 11) {
      throw new StudentDetailsException("Student class number cannot be greater than 11", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getMobileNumber().toString().length() != 10) {
      throw new StudentDetailsException("Student mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void deleteById(final int studentId) {
    studentRepository.deleteById(studentId);
  }
}
