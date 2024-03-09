package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;
import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
import com.codingmonkey.studentmanagement.mapper.StudentMapper;
import com.codingmonkey.studentmanagement.repositories.StudentRepository;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;

@Service
public class StudentServiceImpl implements StudentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
  private final StudentRepository studentRepository;
  private final SubjectRepository subjectRepository;
  private final ApplicationConfiguration applicationConfiguration;
  private final StudentMapper studentMapper;

  public StudentServiceImpl(final StudentRepository studentRepository,
                            @Autowired final SubjectRepository subjectRepository,
                            final ApplicationConfiguration applicationConfiguration,
                            @Autowired StudentMapper studentMapper) {
    this.studentRepository = studentRepository;
    this.subjectRepository = subjectRepository;
    this.applicationConfiguration = applicationConfiguration;
    this.studentMapper = studentMapper;
  }

  @Override
  public List<StudentResponseDTO> getAllStudents() {
    final List<StudentEntity> studentList = studentRepository.findAll();
    return studentList.stream().map(studentEntity -> {
      StudentResponseDTO studentResponseDTO = studentMapper.studentEntityToDto(studentEntity);
      studentResponseDTO.setSubjects(getSubjects(studentEntity));
      return studentResponseDTO;
    }).toList();
  }

  @Override
  public List<StudentResponseDTO> getStudentByFirstNameAndLastName(final String firstName, final String lastName) {
    List<StudentEntity> studentEntityList = studentRepository.findByFirstNameAndLastName(firstName, lastName);
    if (!studentEntityList.isEmpty()) {
      return studentEntityList.stream().map(studentEntity -> {
        StudentResponseDTO studentResponseDTO = studentMapper.studentEntityToDto(studentEntity);
        studentResponseDTO.setSubjects(getSubjects(studentEntity));
        return studentResponseDTO;
      }).toList();
    }
    throw new NotFoundException(
        String.format("Did not find student with first name %s  last name %s", firstName, lastName));
  }

  @Override
  public List<StudentResponseDTO> getStudentByFirstName(final String firstName) {
    List<StudentEntity> studentEntityList = studentRepository.findByFirstName(firstName);
    if (!studentEntityList.isEmpty()) {
      return studentEntityList.stream().map(studentEntity -> {
        StudentResponseDTO studentResponseDTO = studentMapper.studentEntityToDto(studentEntity);
        studentResponseDTO.setSubjects(getSubjects(studentEntity));
        return studentResponseDTO;
      }).toList();
    }
    throw new NotFoundException(String.format("Did not find student with first name %s", firstName));
  }

  @Override
  public List<StudentResponseDTO> getStudentByLastName(final String lastName) {
    List<StudentEntity> studentEntityList = studentRepository.findByLastName(lastName);
    if (!studentEntityList.isEmpty()) {
      return studentEntityList.stream().map(studentEntity -> {
        StudentResponseDTO studentResponseDTO = studentMapper.studentEntityToDto(studentEntity);
        studentResponseDTO.setSubjects(getSubjects(studentEntity));
        return studentResponseDTO;
      }).toList();
    }
    throw new NotFoundException(String.format("Did not find student with last name %s", lastName));
  }

  @Override
  public StudentResponseDTO saveStudentDetails(final StudentRequestDTO studentDTO) {
    String logPrefix = "#saveStudentDetails(): ";
    validateFieldsInRequestDto(studentDTO);
    LOGGER.info("{} Creating new record for student [{}] [{}]", logPrefix, studentDTO.getFirstName(),
        studentDTO.getLastName());
    return saveStudentDetailsToDB(studentDTO);
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

  private StudentResponseDTO saveStudentDetailsToDB(final StudentRequestDTO studentDTO) {
    StudentEntity studentEntity = studentMapper.studentDtoToEntity(studentDTO);
    studentEntity.setRollNumber(getRollNumber(studentDTO.getClassNumber()));
    studentRepository.save(studentEntity);
    StudentResponseDTO studentResponseDto = studentMapper.studentEntityToDto(studentEntity);
    studentResponseDto.setSubjects(getSubjects(studentEntity));
    return studentResponseDto;
  }

  private int getRollNumber(final int classNumber) {
    LOGGER.info("Getting max roll number of class [{}] new student belong to ", classNumber);
    final List<StudentEntity> studentEntityList = studentRepository.findByClassNumber(classNumber);
    return studentEntityList.stream().mapToInt(StudentEntity::getRollNumber).max().orElse(1);
  }

  private List<String> getSubjects(final StudentEntity studentEntity) {
    List<SubjectEntity> subjectEntities = subjectRepository.findSubjectsByClassNumber(studentEntity.getClassNumber());
    if (subjectEntities.isEmpty()) {
      throw new NotFoundException("Subjects list not found for studentEntity: " + studentEntity.getFirstName());
    }
    return subjectEntities.stream().map(SubjectEntity::getSubject).collect(Collectors.toList());
  }

  @Override
  public StudentResponseDTO updateStudentDetails(final int studentId, final StudentRequestDTO studentDTO) {
    String logPrefix = "#updateStudentDetails(): ";
    validateFieldsInRequestDto(studentDTO);
    LOGGER.info("{} Updating record of student [{}] [{}]", logPrefix, studentDTO.getFirstName(),
        studentDTO.getLastName());
    return updateStudentDetailsToDB(studentId, studentDTO);
  }

  private StudentResponseDTO updateStudentDetailsToDB(final int studentId, final StudentRequestDTO studentDTO) {
    StudentEntity studentEntity = studentRepository.findByFirstNameAndLastNameAndStudentId(studentDTO.getFirstName(),
        studentDTO.getLastName(), studentId);
    if (studentEntity == null) {
      throw new NotFoundException("Did not find student with first name " + studentDTO.getFirstName() + " last name "
          + studentDTO.getLastName());
    }
    StudentResponseDTO studentResponseDto = studentMapper.updateStudentEntityToDto(studentDTO, studentEntity);
    studentRepository.save(studentEntity);
    studentResponseDto.setSubjects(getSubjects(studentEntity));
    return studentResponseDto;
  }

  @Override
  public void deleteById(final int studentId) {
    studentRepository.deleteById(studentId);
  }
}
