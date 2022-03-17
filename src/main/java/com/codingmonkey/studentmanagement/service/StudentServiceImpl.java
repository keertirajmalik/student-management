package com.codingmonkey.studentmanagement.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;
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
  private final ApplicationConfiguration applicationConfiguration;

  @Autowired
  ModelMapper modelMapper;

  public StudentServiceImpl(final StudentRepository studentRepository,
                            final SubjectRepository subjectRepository,
                            final ApplicationConfiguration applicationConfiguration) {
    this.studentRepository = studentRepository;
    this.subjectRepository = subjectRepository;
    this.applicationConfiguration = applicationConfiguration;
  }

  @Override
  public List<StudentDTO> findAll() {
    return studentRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  @Override
  public List<StudentDTO> findByFirstNameAndLastName(final String firstName, final String lastName) {
    List<StudentEntity> studentEntityList = studentRepository.findByFirstNameAndLastName(firstName, lastName);

    if (!studentEntityList.isEmpty()) {
      return studentEntityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    throw new RuntimeException("Did not find student with first name " + firstName + " last name " + lastName);
  }

  @Override
  public ResponseEntity<StudentDTO> saveStudentDetails(final StudentDTO studentDTO) {

    String logPrefix = "#saveStudentDetails(): ";
    LOGGER.info("{} Enter ", logPrefix);

    validateFieldsInRequestDto(studentDTO);

    LOGGER.info("{} Creating new record for student [{}] [{}]", logPrefix, studentDTO.getFirstName(),
        studentDTO.getLastName());
    return saveStudentDetailsToDB(studentDTO);
  }

  @Override
  public void deleteById(final int studentId) {
    studentRepository.deleteById(studentId);
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
    studentDTO.setGender(studentEntity.getGender());

    List<SubjectEntity> subjectEntities = subjectRepository.findSubjectsByClassNumber(studentEntity.getClassNumber());

    if (subjectEntities.isEmpty()) {
      throw new NotFoundException("Subjects list not found for studentEntity: " + studentEntity.getFirstName());
    }

    studentDTO.setSubjects(subjectEntities.stream().map(SubjectEntity::getSubject).collect(Collectors.toList()));

    return studentDTO;
  }

  private ResponseEntity<StudentDTO> saveStudentDetailsToDB(final StudentDTO studentDTO) {
    StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);

    studentEntity.setRollNumber(getRollNumber(studentDTO));
    studentRepository.save(studentEntity);

    StudentDTO studentResponseDto = modelMapper.map(studentEntity, StudentDTO.class);
    List<SubjectEntity> subjectEntities = subjectRepository.findSubjectsByClassNumber(studentEntity.getClassNumber());

    if (subjectEntities.isEmpty()) {
      throw new NotFoundException("Subjects list not found for studentEntity: " + studentEntity.getFirstName());
    }
    studentResponseDto.setSubjects(
        subjectEntities.stream().map(SubjectEntity::getSubject).collect(Collectors.toList()));

    return ResponseEntity.status(HttpStatus.CREATED).body(studentResponseDto);
  }

  private int getRollNumber(final StudentDTO studentDTO) {
    String logPrefix = "# " + " #saveStudentDetails(): ";
    LOGGER.info("{} Getting max roll number of class [{}] new student belong to ", logPrefix,
        studentDTO.getClassNumber());

    final List<StudentEntity> studentEntityList = studentRepository.findByClassNumber(studentDTO.getClassNumber());

    int rollNumber = 0;

    /*
      Since when classNumber has no student Optional[[]] is returned which passes the optionalStudentEntityList.isPresent() check
      So replaced with optionalStudentEntityList.get().size() > 0 check
      Since we are just trying to get the list size shouldn't cause any exception.
     */

    if (!studentEntityList.isEmpty()) {
      rollNumber = studentEntityList.stream()
          .max(Comparator.comparing(StudentEntity::getRollNumber))
          .get()
          .getRollNumber();
    }

    return rollNumber + 1;
  }

  private void validateFieldsInRequestDto(final StudentDTO studentDTO) {
    if (studentDTO.getClassNumber() > applicationConfiguration.getMaxClassAllowed()) {
      throw new StudentDetailsException(
          "Class number cannot be greater than " + applicationConfiguration.getMaxClassAllowed(),
          HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getMobileNumber().toString().length() != 10) {
      throw new StudentDetailsException("Mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    }
  }
}
