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

import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
  private final TeacherRepository teacherRepository;

  @Autowired
  ModelMapper modelMapper;

  public TeacherServiceImpl(final TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  @Override
  public List<TeacherDTO> findAll() {
    return teacherRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  @Override
  public TeacherDTO findById(final int teacherId) {
    Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);

    if (teacher.isPresent()) {
      return convertEntityToDto(teacher.get());
    }
    throw new RuntimeException("Did not find teacher with id: " + teacherId);
  }

  @Override
  public ResponseEntity<TeacherDTO> saveTeacherDetails(final TeacherDTO teacherDTO) {

    String logPrefix = "# " + " #saveTeacherDetails(): ";
    LOGGER.info("{} Enter ", logPrefix);

    validateFieldsInRequestDto(teacherDTO);

    //    Optional<StudentEntity> studentEntityOptional = teacherRepository.find(studentDTO.getStudentId());

    return saveStudentDetailsToDB(teacherDTO);
  }

  private ResponseEntity<TeacherDTO> saveStudentDetailsToDB(final TeacherDTO teacherDTO) {

    TeacherEntity teacherEntity = modelMapper.map(teacherDTO, TeacherEntity.class);

    teacherRepository.save(teacherEntity);

    return new ResponseEntity(teacherEntity, HttpStatus.CREATED);

  }

  private void validateFieldsInRequestDto(final TeacherDTO teacherDTO) {
    if (Optional.ofNullable(teacherDTO.getFirst_name()).isEmpty()) {
      throw new StudentDetailsException("Teacher First Name cannot be null", HttpStatus.BAD_REQUEST);
    } else if (teacherDTO.getFirst_name().isEmpty()) {
      throw new StudentDetailsException("Teacher First Name cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(teacherDTO.getLast_name()).isEmpty()) {
      throw new StudentDetailsException("Teacher Last Name cannot be null", HttpStatus.BAD_REQUEST);
    } else if (teacherDTO.getLast_name().isEmpty()) {
      throw new StudentDetailsException("Teacher Last Name cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(teacherDTO.getEmail()).isEmpty()) {
      throw new StudentDetailsException("Teacher email cannot be null", HttpStatus.BAD_REQUEST);
    } else if (teacherDTO.getEmail().isEmpty()) {
      throw new StudentDetailsException("Teacher email cannot be empty", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(teacherDTO.getMobile_number()).isEmpty()) {
      throw new StudentDetailsException("Teacher mobile number cannot be null", HttpStatus.BAD_REQUEST);
    }
    //    else if (teacherDTO.get() < 1) {
    //      throw new StudentDetailsException("Student class number cannot be less than 1", HttpStatus.BAD_REQUEST);
    //    } else if (studentDTO.getClassNumber() > 11) {
    //      throw new StudentDetailsException("Student class number cannot be greater than 11", HttpStatus.BAD_REQUEST);
    //    } else if (studentDTO.getMobileNumber().toString().length() != 10) {
    //      throw new StudentDetailsException("Student mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    //    }
  }

  @Override
  public void deleteById(final int teacherId) {
    teacherRepository.deleteById(teacherId);
  }

  private TeacherDTO convertEntityToDto(TeacherEntity teacherEntity) {
    TeacherDTO teacherDTO = new TeacherDTO();
    teacherDTO.setFirst_name(teacherEntity.getFirstName());
    teacherDTO.setLast_name(teacherEntity.getLastName());
    teacherDTO.setEmail(teacherEntity.getEmail());
    teacherDTO.setMobile_number(teacherEntity.getMobileNumber());
    teacherDTO.setSubjects(teacherEntity.getSubjects());
    return teacherDTO;
  }
}
