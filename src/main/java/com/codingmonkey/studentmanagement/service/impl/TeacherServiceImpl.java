package com.codingmonkey.studentmanagement.service.impl;

import com.codingmonkey.studentmanagement.domain.dto.request.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.domain.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.domain.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.TeacherDetailsException;
import com.codingmonkey.studentmanagement.mapper.TeacherMapper;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;
import com.codingmonkey.studentmanagement.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
  private final TeacherRepository teacherRepository;
  private final SubjectRepository subjectRepository;
  private final TeacherMapper teacherMapper;

  public TeacherServiceImpl(final TeacherRepository teacherRepository,
                            final SubjectRepository subjectRepository,
                            final TeacherMapper teacherMapper) {
    this.teacherRepository = teacherRepository;
    this.subjectRepository = subjectRepository;
    this.teacherMapper = teacherMapper;
  }

  @Override
  public Page<TeacherResponseDTO> getAllTeachers(Pageable pageable) {
    return teacherRepository.findAll(pageable).map(teacherEntity -> {
      TeacherResponseDTO teacherResponseDTO = teacherMapper.teacherEntityToDto(teacherEntity);
      teacherResponseDTO.setSubjects(getSubjects(teacherEntity));
      return teacherResponseDTO;
    });
  }


  @Override
  public Page<TeacherResponseDTO> getTeacherByFirstNameAndLastName(String firstName, String lastName, Pageable pageable) {
    Page<TeacherEntity> teacherEntityList = teacherRepository.findByFirstNameAndLastName(firstName, lastName, pageable);
    if (!teacherEntityList.isEmpty()) {
      return teacherEntityList.map(teacherEntity -> {
        TeacherResponseDTO teacherResponseDTO = teacherMapper.teacherEntityToDto(teacherEntity);
        teacherResponseDTO.setSubjects(getSubjects(teacherEntity));
        return teacherResponseDTO;
      });
    }
    throw new NotFoundException("Did not find Teacher with first name " + firstName + " last name " + lastName);
  }

  @Override
  public TeacherResponseDTO saveTeacherDetails(final TeacherRequestDTO teacherDTO) {
    String logPrefix = "# " + " #saveTeacherDetails(): ";
    LOGGER.info("{} Enter ", logPrefix);
    validateFieldsInRequestDto(teacherDTO);
    LOGGER.info("{} Creating new record for teacher [{}] [{}]", logPrefix, teacherDTO.getFirstName(),
        teacherDTO.getLastName());
    return saveTeacherDetailsToDB(teacherDTO);
  }

  @Override
  public TeacherResponseDTO updateTeacherDetails(final int teacherId, final TeacherRequestDTO teacherDTO) {
    String logPrefix = "#updateTeacherDetails(): ";
    validateFieldsInRequestDto(teacherDTO);
    LOGGER.info("{} Updating record of teacher [{}] [{}]", logPrefix, teacherDTO.getFirstName(),
        teacherDTO.getLastName());
    return updateTeacherDetailsToDB(teacherId, teacherDTO);
  }

  private TeacherResponseDTO updateTeacherDetailsToDB(final int teacherId, final TeacherRequestDTO teacherDTO) {
    TeacherEntity teacherEntity = teacherRepository.findByFirstNameAndLastNameAndTeacherId(teacherDTO.getFirstName(),
        teacherDTO.getLastName(), teacherId);
    if (teacherEntity == null) {
      throw new NotFoundException("Did not find teacher with first name " + teacherDTO.getFirstName() + " last name "
          + teacherDTO.getLastName());
    }
    TeacherResponseDTO teacherResponseDTO = teacherMapper.updateTeacherEntityFromDto(teacherDTO, teacherEntity);
    teacherRepository.save(teacherEntity);

    teacherResponseDTO.setSubjects(getSubjects(teacherEntity));
    return teacherResponseDTO;
  }

  @Override
  public Page<TeacherResponseDTO> getTeacherByFirstName(final String firstName, Pageable pageable) {
    Page<TeacherEntity> teacherEntityList = teacherRepository.findByFirstName(firstName, pageable);
    if (!teacherEntityList.isEmpty()) {
      return teacherEntityList.map(teacherEntity -> {
        TeacherResponseDTO teacherResponseDTO = teacherMapper.teacherEntityToDto(teacherEntity);
        teacherResponseDTO.setSubjects(getSubjects(teacherEntity));
        return teacherResponseDTO;
      });
    }
    throw new NotFoundException("Did not find Teacher with first name " + firstName);
  }

  @Override
  public Page<TeacherResponseDTO> getTeacherByLastName(final String lastName, Pageable pageable) {
    Page<TeacherEntity> teacherEntityList = teacherRepository.findByLastName(lastName, pageable);
    if (!teacherEntityList.isEmpty()) {
      return teacherEntityList.map(teacherEntity -> {
        TeacherResponseDTO teacherResponseDTO = teacherMapper.teacherEntityToDto(teacherEntity);
        teacherResponseDTO.setSubjects(getSubjects(teacherEntity));
        return teacherResponseDTO;
      });
    }
    throw new NotFoundException("Did not find Teacher with last name " + lastName);
  }

  private TeacherResponseDTO saveTeacherDetailsToDB(final TeacherRequestDTO teacherDTO) {
    TeacherEntity teacherEntity = teacherMapper.teacherDtoToEntity(teacherDTO);

    teacherRepository.save(teacherEntity);
    TeacherResponseDTO teacherResponseDTO = teacherMapper.teacherEntityToDto(teacherEntity);
    teacherResponseDTO.setSubjects(getSubjects(teacherEntity));
    return teacherResponseDTO;
  }

  private void validateFieldsInRequestDto(final TeacherRequestDTO teacherDTO) {
    if (teacherDTO.getMobileNumber().toString().length() != 10) {
      throw new TeacherDetailsException("Mobile number should have only 10 digits", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(teacherDTO.getGender()).isEmpty()) {
      throw new TeacherDetailsException("Provide Teacher gender type", HttpStatus.BAD_REQUEST);
    } else if (Optional.ofNullable(teacherDTO.getSubjects()).isEmpty()) {
      throw new TeacherDetailsException("Provide Teacher subjects", HttpStatus.BAD_REQUEST);
    } else if (!new HashSet<>(getSubjects(teacherMapper.teacherDtoToEntity(teacherDTO))).containsAll(
        teacherDTO.getSubjects())) {
      throw new TeacherDetailsException("Provide proper subjects", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void deleteById(final int teacherId) {
    teacherRepository.deleteById(teacherId);
  }

  private List<String> getSubjects(final TeacherEntity teacherEntity) {
    List<SubjectEntity> subjectEntities = subjectRepository.findAll();
    if (subjectEntities.isEmpty()) {
      throw new NotFoundException("Subjects list not found for teacherEntity: " + teacherEntity.getFirstName());
    }
    return subjectEntities.stream().map(SubjectEntity::getSubject).collect(Collectors.toList());
  }
}
