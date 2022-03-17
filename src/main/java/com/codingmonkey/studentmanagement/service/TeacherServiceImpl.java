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
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
  private final TeacherRepository teacherRepository;
  private final SubjectRepository subjectRepository;

  @Autowired
  ModelMapper modelMapper;

  public TeacherServiceImpl(final TeacherRepository teacherRepository, final SubjectRepository subjectRepository) {
    this.teacherRepository = teacherRepository;
    this.subjectRepository = subjectRepository;
  }

  @Override
  public List<TeacherDTO> findAll() {
    return teacherRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  @Override
  public List<TeacherDTO> findByFirstNameAndLastName(String firstName, String lastName) {

    List<TeacherEntity> teacherEntityList = teacherRepository.findByFirstNameAndLastName(firstName, lastName);

    if (!teacherEntityList.isEmpty()) {
      return teacherEntityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    throw new RuntimeException("Did not find Teacher with first name " + firstName + " last name " + lastName);
  }

  @Override
  public ResponseEntity<TeacherDTO> saveTeacherDetails(final TeacherDTO teacherDTO) {

    String logPrefix = "# " + " #saveTeacherDetails(): ";
    LOGGER.info("{} Enter ", logPrefix);

    validateFieldsInRequestDto(teacherDTO);

    return saveStudentDetailsToDB(teacherDTO);
  }

  private ResponseEntity<TeacherDTO> saveStudentDetailsToDB(final TeacherDTO teacherDTO) {

    TeacherEntity teacherEntity = modelMapper.map(teacherDTO, TeacherEntity.class);

    teacherRepository.save(teacherEntity);

    final List<SubjectEntity> subjectEntityList = subjectRepository.findSubjectByTeacher_TeacherId(
        teacherEntity.getTeacherId());
    teacherEntity.setSubjects(subjectEntityList);

    return ResponseEntity.status(HttpStatus.CREATED).body(convertEntityToDto(teacherEntity));
  }

  private void validateFieldsInRequestDto(final TeacherDTO teacherDTO) {
    if (teacherDTO.getMobileNumber().toString().length() != 10) {
      throw new StudentDetailsException("Mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(teacherDTO.getGender()).isEmpty()) {
      throw new StudentDetailsException("Provide Teacher gender type", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void deleteById(final int teacherId) {
    teacherRepository.deleteById(teacherId);
  }

  private TeacherDTO convertEntityToDto(TeacherEntity teacherEntity) {
    TeacherDTO teacherDTO = new TeacherDTO();
    teacherDTO.setFirstName(teacherEntity.getFirstName());
    teacherDTO.setLastName(teacherEntity.getLastName());
    teacherDTO.setEmail(teacherEntity.getEmail());
    teacherDTO.setMobileNumber(teacherEntity.getMobileNumber());
    teacherDTO.setSubjects(teacherEntity.getSubjects());
    teacherDTO.setGender(teacherEntity.getGender());

    return teacherDTO;
  }
}
