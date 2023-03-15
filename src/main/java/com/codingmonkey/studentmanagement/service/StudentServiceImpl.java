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
  private final ModelMapper modelMapper;

  public StudentServiceImpl(final StudentRepository studentRepository,
                            @Autowired final SubjectRepository subjectRepository,
                            final ApplicationConfiguration applicationConfiguration,
                            @Autowired final ModelMapper modelMapper) {
    this.studentRepository = studentRepository;
    this.subjectRepository = subjectRepository;
    this.applicationConfiguration = applicationConfiguration;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<StudentDTO> getAllStudents() {
    final List<StudentEntity> studentList = studentRepository.findAll();
    return studentList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  @Override
  public List<StudentDTO> getStudentByFirstNameAndLastName(final String firstName, final String lastName) {
    List<StudentEntity> studentEntityList = studentRepository.findByFirstNameAndLastName(firstName, lastName);
    if (!studentEntityList.isEmpty()) {
      return studentEntityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    throw new NotFoundException(
        String.format("Did not find student with first name %s  last name %s", firstName, lastName));
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
  public ResponseEntity<StudentDTO> updateStudentDetails(final StudentDTO studentDTO) {
    String logPrefix = "#updateStudentDetails(): ";
    LOGGER.info("{} Enter ", logPrefix);
    validateFieldsInRequestDto(studentDTO);
    LOGGER.info("{} Updating record of student [{}] [{}]", logPrefix, studentDTO.getFirstName(),
        studentDTO.getLastName());
    return updateStudentDetailsToDB(studentDTO);
  }

  @Override
  public ResponseEntity<StudentEntity> deleteById(final int studentId) {
    studentRepository.deleteById(studentId);
    return ResponseEntity.status(HttpStatus.OK).body(studentRepository.getById(studentId));
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

  private ResponseEntity<StudentDTO> updateStudentDetailsToDB(final StudentDTO studentDTO) {
    StudentEntity studentEntity = studentRepository.findByFirstNameAndLastNameAndStudentId(studentDTO.getFirstName(),
        studentDTO.getLastName(), studentDTO.getStudentId());
    if (studentEntity == null) {
      throw new NotFoundException("Did not find student with first name " + studentDTO.getFirstName() + " last name "
          + studentDTO.getLastName());
    }
    studentEntity.setRollNumber(getRollNumber(studentDTO));
    studentRepository.save(studentEntity);
    return ResponseEntity.status(HttpStatus.OK).body(studentDTO);
  }

  private int getRollNumber(final StudentDTO studentDTO) {
    String logPrefix = "# " + " #saveStudentDetails(): ";
    LOGGER.info("{} Getting max roll number of class [{}] new student belong to ", logPrefix,
        studentDTO.getClassNumber());
    final List<StudentEntity> studentEntityList = studentRepository.findByClassNumber(studentDTO.getClassNumber());
    int rollNumber = 0;
    if (!studentEntityList.isEmpty()) {
      rollNumber = studentEntityList.stream().mapToInt(StudentEntity::getRollNumber).max().orElse(0);
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
    } else if (Optional.ofNullable(studentDTO.getGender()).isEmpty()) {
      throw new StudentDetailsException("Provide Teacher gender type", HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getRollNumber() < 0) {
      throw new StudentDetailsException("Roll number should be more than 0", HttpStatus.BAD_REQUEST);
    }
  }
}
