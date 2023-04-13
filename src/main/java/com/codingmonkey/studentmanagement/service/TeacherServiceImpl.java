package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.TeacherDetailsException;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
  private final TeacherRepository teacherRepository;
  private final SubjectRepository subjectRepository;
  private final ModelMapper modelMapper;

  public TeacherServiceImpl(final TeacherRepository teacherRepository,
                            @Autowired final SubjectRepository subjectRepository,
                            @Autowired final ModelMapper modelMapper) {
    this.teacherRepository = teacherRepository;
    this.subjectRepository = subjectRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<TeacherDTO> getAllTeachers() {
    return teacherRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  @Override
  public List<TeacherDTO> getTeacherByFirstNameAndLastName(String firstName, String lastName) {
    List<TeacherEntity> teacherEntityList = teacherRepository.findByFirstNameAndLastName(firstName, lastName);
    if (!teacherEntityList.isEmpty()) {
      return teacherEntityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    throw new NotFoundException("Did not find Teacher with first name " + firstName + " last name " + lastName);
  }

  @Override
  public TeacherDTO saveTeacherDetails(final TeacherDTO teacherDTO) {
    String logPrefix = "# " + " #saveTeacherDetails(): ";
    LOGGER.info("{} Enter ", logPrefix);
    validateFieldsInRequestDto(teacherDTO);
    return saveTeacherDetailsToDB(teacherDTO);
  }

  @Override
  public List<TeacherDTO> getTeacherByFirstName(final String firstName) {
    List<TeacherEntity> teacherEntityList = teacherRepository.findByFirstName(firstName);
    if (!teacherEntityList.isEmpty()) {
      return teacherEntityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    throw new NotFoundException("Did not find Teacher with first name " + firstName);
  }

  @Override
  public List<TeacherDTO> getTeacherByLastName(final String lastName) {
    List<TeacherEntity> teacherEntityList = teacherRepository.findByLastName(lastName);
    if (!teacherEntityList.isEmpty()) {
      return teacherEntityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    throw new NotFoundException("Did not find Teacher with last name " + lastName);
  }

  private TeacherDTO saveTeacherDetailsToDB(final TeacherDTO teacherDTO) {
    TeacherEntity teacherEntity = modelMapper.map(teacherDTO, TeacherEntity.class);
    teacherRepository.save(teacherEntity);
    TeacherDTO teacherResponseDTO = modelMapper.map(teacherEntity, TeacherDTO.class);
    final List<SubjectEntity> subjectEntityList = subjectRepository.findSubjectEntitiesByTeacherTeacherId(
        teacherEntity.getTeacherId());
    if (subjectEntityList.isEmpty()) {
      throw new NotFoundException("Subjects list not found for teacherEntity: " + teacherEntity.getFirstName());
    }
    teacherResponseDTO.setSubjects(
        subjectEntityList.stream().map(SubjectEntity::getSubject).collect(Collectors.toList()));
    return teacherResponseDTO;
  }

  private void validateFieldsInRequestDto(final TeacherDTO teacherDTO) {
    if (teacherDTO.getMobileNumber().toString().length() != 10) {
      throw new TeacherDetailsException("Mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(teacherDTO.getGender()).isEmpty()) {
      throw new TeacherDetailsException("Provide Teacher gender type", HttpStatus.BAD_REQUEST);
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
    teacherDTO.setGender(teacherEntity.getGender());
    final List<SubjectEntity> subjectEntityList = subjectRepository.findSubjectEntitiesByTeacherTeacherId(
        teacherEntity.getTeacherId());
    if (subjectEntityList.isEmpty()) {
      throw new NotFoundException("Subject list not found for teacherEntity: " + teacherEntity.getFirstName());
    }
    teacherDTO.setSubjects(subjectEntityList.stream().map(SubjectEntity::getSubject).collect(Collectors.toList()));
    return teacherDTO;
  }
}
