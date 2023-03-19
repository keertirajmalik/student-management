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
import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
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
  public List<StudentResponseDTO> getAllStudents() {
    final List<StudentEntity> studentList = studentRepository.findAll();
    return studentList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  @Override
  public List<StudentResponseDTO> getStudentByFirstNameAndLastName(final String firstName, final String lastName) {
    List<StudentEntity> studentEntityList = studentRepository.findByFirstNameAndLastName(firstName, lastName);
    if (!studentEntityList.isEmpty()) {
      return studentEntityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    throw new NotFoundException(
        String.format("Did not find student with first name %s  last name %s", firstName, lastName));
  }

  @Override
  public ResponseEntity<StudentResponseDTO> saveStudentDetails(final StudentRequestDTO studentDTO) {
    String logPrefix = "#saveStudentDetails(): ";
    validateFieldsInRequestDto(studentDTO);
    LOGGER.info("{} Creating new record for student [{}] [{}]", logPrefix, studentDTO.getFirstName(),
        studentDTO.getLastName());
    return saveStudentDetailsToDB(studentDTO);
  }

  @Override
  public ResponseEntity<StudentResponseDTO> updateStudentDetails(final StudentRequestDTO studentDTO) {
    String logPrefix = "#updateStudentDetails(): ";
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

  private StudentResponseDTO convertEntityToDto(StudentEntity studentEntity) {
    StudentResponseDTO studentDTO = new StudentResponseDTO();
    studentDTO.setStudentId(studentEntity.getStudentId());
    studentDTO.setFirstName(studentEntity.getFirstName());
    studentDTO.setLastName(studentEntity.getLastName());
    studentDTO.setEmail(studentEntity.getEmail());
    studentDTO.setMobileNumber(studentEntity.getMobileNumber());
    studentDTO.setClassNumber(studentEntity.getClassNumber());
    studentDTO.setRollNumber(studentEntity.getRollNumber());
    studentDTO.setGender(studentEntity.getGender());
    return getSubjects(studentEntity, studentDTO);
  }

  private StudentResponseDTO getSubjects(final StudentEntity studentEntity, final StudentResponseDTO studentDTO) {
    List<SubjectEntity> subjectEntities = subjectRepository.findSubjectsByClassNumber(studentEntity.getClassNumber());
    if (subjectEntities.isEmpty()) {
      throw new NotFoundException("Subjects list not found for studentEntity: " + studentEntity.getFirstName());
    }
    studentDTO.setSubjects(subjectEntities.stream().map(SubjectEntity::getSubject).collect(Collectors.toList()));
    return studentDTO;
  }

  private ResponseEntity<StudentResponseDTO> saveStudentDetailsToDB(final StudentRequestDTO studentDTO) {
    StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);
    studentEntity.setRollNumber(getRollNumber(studentDTO.getClassNumber()));
    studentRepository.save(studentEntity);
    StudentResponseDTO studentResponseDto = modelMapper.map(studentEntity, StudentResponseDTO.class);
    getSubjects(studentEntity, studentResponseDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(studentResponseDto);
  }

  private ResponseEntity<StudentResponseDTO> updateStudentDetailsToDB(final StudentRequestDTO studentDTO) {
    StudentEntity studentEntity = studentRepository.findByFirstNameAndLastNameAndStudentId(studentDTO.getFirstName(),
        studentDTO.getLastName(), studentDTO.getStudentId());
    if (studentEntity == null) {
      throw new NotFoundException("Did not find student with first name " + studentDTO.getFirstName() + " last name "
          + studentDTO.getLastName());
    }
    modelMapper.map(studentDTO, studentEntity);
    studentRepository.save(studentEntity);
    StudentResponseDTO studentResponseDto = modelMapper.map(studentEntity, StudentResponseDTO.class);
    getSubjects(studentEntity, studentResponseDto);
    return ResponseEntity.status(HttpStatus.OK).body(studentResponseDto);
  }

  private int getRollNumber(final int classNumber) {
    LOGGER.info("Getting max roll number of class [{}] new student belong to ", classNumber);
    final List<StudentEntity> studentEntityList = studentRepository.findByClassNumber(classNumber);
    int rollNumber = 0;
    if (!studentEntityList.isEmpty()) {
      rollNumber = studentEntityList.stream().mapToInt(StudentEntity::getRollNumber).max().orElse(0);
    }
    return rollNumber + 1;
  }

  private void validateFieldsInRequestDto(final StudentRequestDTO studentDTO) {
    if (studentDTO.getClassNumber() > applicationConfiguration.getMaxClassAllowed()) {
      throw new StudentDetailsException(
          "Class number cannot be greater than " + applicationConfiguration.getMaxClassAllowed(),
          HttpStatus.BAD_REQUEST);
    } else if (studentDTO.getMobileNumber().toString().length() != 10) {
      throw new StudentDetailsException("Mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(studentDTO.getGender()).isEmpty()) {
      throw new StudentDetailsException("Provide Teacher gender type", HttpStatus.BAD_REQUEST);
    }
  }
}
