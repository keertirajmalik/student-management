package com.codingmonkey.studentmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.codingmonkey.studentmanagement.domain.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.domain.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.domain.dto.request.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.TeacherDetailsException;
import com.codingmonkey.studentmanagement.mapper.TeacherMapper;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
  @InjectMocks
  private TeacherServiceImpl teacherService;
  @Mock
  private TeacherRepository teacherRepository;
  @Mock
  private SubjectRepository subjectRepository;
  @Spy
  private TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

  private final Pageable pageable = PageRequest.of(0, 10);

  @Test
  void getAllTeachers_whenTeachersArePresent_expectAllTeachersDetails() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));

    when(teacherRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(teacherDetailsList));
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));
    Page<TeacherResponseDTO> result = teacherService.getAllTeachers(pageable);

    assertThat(result).hasSize(1);
    assertEquals("John", result.getContent().get(0).getFirstName());
  }

  @Test
  void getAllTeachers_whenNoTeachersArePresent_expectAllTeachersDetails() {
    when(teacherRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

    Page<TeacherResponseDTO> result = teacherService.getAllTeachers(pageable);

    assertTrue(result.isEmpty());
  }

  @Test
  void getAllTeachers_whenSubjectsAreNotPresent_expectNotFoundExceptionIsThrown() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));
    when(teacherRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(teacherDetailsList));
    assertThrows(NotFoundException.class, () -> teacherService.getAllTeachers(pageable));
  }

  @Test
  void getTeacherByFirstNameAndLastName_whenTeacherIsPresent_expectTeacherDetails() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));
    when(teacherRepository.findByFirstNameAndLastName(eq(teacherDetailsList.get(0).getFirstName()),
        eq(teacherDetailsList.get(0).getLastName()), any(Pageable.class)))
        .thenReturn(new PageImpl<>(teacherDetailsList));
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));
    Page<TeacherResponseDTO> result = teacherService.getTeacherByFirstNameAndLastName(
        teacherDetailsList.get(0).getFirstName(), teacherDetailsList.get(0).getLastName(), pageable);

    assertThat(result).hasSize(1);
    assertEquals("John", result.getContent().get(0).getFirstName());
  }

  @Test
  void getTeacherByFirstNameAndLastName_whenTeacherIsNotPresent_expectNotFoundExceptionIsThrown() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));
    final String firstName = teacherDetailsList.get(0).getFirstName();
    final String lastName = teacherDetailsList.get(0).getLastName();
    when(teacherRepository.findByFirstNameAndLastName(eq(firstName), eq(lastName), any(Pageable.class)))
        .thenReturn(Page.empty());

    assertThrows(NotFoundException.class,
        () -> teacherService.getTeacherByFirstNameAndLastName(firstName, lastName, pageable));
  }

  @Test
  void saveTeacherDetails_whenTeachersArePresent_expectTeachersDetailsAreSaved() {
    final TeacherRequestDTO teacherDTO = new TeacherRequestDTO("John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", Gender.MALE, List.of("Test"));
    final TeacherResponseDTO teacherResponseDTO = new TeacherResponseDTO(1, "John", "Doe", Long.valueOf("8277272285"),
        "email@gmail.com", Gender.MALE, List.of("Test"));
    final TeacherEntity teacherEntity = new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"),
        "email@gmail.com", Gender.MALE);

    when(teacherMapper.teacherDtoToEntity(teacherDTO)).thenReturn(teacherEntity);
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));
    when(teacherMapper.teacherEntityToDto(teacherEntity)).thenReturn(teacherResponseDTO);
    TeacherResponseDTO teacher = teacherService.saveTeacherDetails(teacherDTO);

    assertEquals(teacherResponseDTO, teacher);
  }

  @Test
  void saveTeacherDetails_whenTeacherMobileNumberIsInvalid_expect_TeacherDetailsExceptionIsThrown() {
    final TeacherRequestDTO teacherDTO = new TeacherRequestDTO("John", "Doe", Long.valueOf("827727228545"),
        "keerti@gmailcom", Gender.MALE, List.of("Test"));

    assertThrows(TeacherDetailsException.class, () -> teacherService.saveTeacherDetails(teacherDTO));
  }

  @Test
  void saveTeacherDetails_whenTeacherGenderTypeIsInvalid_expect_TeacherDetailsExceptionIsThrown() {
    final TeacherRequestDTO teacherDTO = new TeacherRequestDTO("John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", null, List.of("Test"));

    assertThrows(TeacherDetailsException.class, () -> teacherService.saveTeacherDetails(teacherDTO));
  }

  @Test
  void saveTeacherDetails_whenSubjectsAreNotPresent_expectNotFoundExceptionIsThrown() {
    final TeacherRequestDTO teacherDTO = new TeacherRequestDTO("John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", Gender.MALE, List.of("Test"));
    final TeacherEntity teacherEntity = new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"),
        "email@gmail.com", Gender.MALE);

    when(teacherMapper.teacherDtoToEntity(teacherDTO)).thenReturn(teacherEntity);
    when(subjectRepository.findAll()).thenReturn(Collections.emptyList());

    assertThrows(NotFoundException.class, () -> teacherService.saveTeacherDetails(teacherDTO));
  }

  @Test
  void updateTeacherDetails_whenTeacherDetailsAreValid_expectTeacherDetailsAreUpdated() {
    // Given
    final TeacherRequestDTO teacherDTO = new TeacherRequestDTO("John", "Doe", 8277272285L, "keerti@gmailcom",
        Gender.MALE, List.of("Test"));
    final TeacherResponseDTO expectedTeacherResponseDTO = new TeacherResponseDTO(1, "John", "Doe", 8277272285L,
        "keerti@gmailcom", Gender.MALE, List.of("Test"));
    final TeacherEntity teacherEntity = new TeacherEntity(1, "John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE);

    when(teacherRepository.findByFirstNameAndLastNameAndTeacherId(teacherDTO.getFirstName(), teacherDTO.getLastName(),
        1)).thenReturn(teacherEntity);
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));

    // When
    TeacherResponseDTO actualTeacherResponseDTO = teacherService.updateTeacherDetails(1, teacherDTO);

    // Then
    assertEquals(expectedTeacherResponseDTO.getTeacherId(), actualTeacherResponseDTO.getTeacherId());
    assertEquals(expectedTeacherResponseDTO.getFirstName(), actualTeacherResponseDTO.getFirstName());
    assertEquals(expectedTeacherResponseDTO.getLastName(), actualTeacherResponseDTO.getLastName());
    assertEquals(expectedTeacherResponseDTO.getMobileNumber(), actualTeacherResponseDTO.getMobileNumber());
    assertEquals(expectedTeacherResponseDTO.getEmail(), actualTeacherResponseDTO.getEmail());
    assertEquals(expectedTeacherResponseDTO.getSubjects(), actualTeacherResponseDTO.getSubjects());
    assertEquals(expectedTeacherResponseDTO.getGender(), actualTeacherResponseDTO.getGender());
  }

  @Test
  void updateTeacherDetails_whenTeacherDoesNotExist_expectNotFoundException() {
    // Given
    final TeacherRequestDTO teacherDTO = new TeacherRequestDTO("John", "Doe", 8277272285L, "keerti@gmailcom",
        Gender.MALE, List.of("Test"));

    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));

    // When & Then
    assertThrows(NotFoundException.class, () -> teacherService.updateTeacherDetails(1, teacherDTO));
  }

  @Test
  void getTeacherByFirstName_whenTeacherIsPresent_expectTeacherDetails() {
    // Given
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", 8277272285L, "email@gmail.com", Gender.MALE));

    when(teacherRepository.findByFirstName(eq("John"), any(Pageable.class)))
        .thenReturn(new PageImpl<>(teacherDetailsList));
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));

    // When
    Page<TeacherResponseDTO> result = teacherService.getTeacherByFirstName("John", pageable);

    // Then
    assertThat(result).hasSize(1);
    assertEquals("John", result.getContent().get(0).getFirstName());
  }

  @Test
  void getTeacherByFirstName_whenTeacherIsNotPresent_expectNotFoundException() {
    // Given
    when(teacherRepository.findByFirstName(eq("John"), any(Pageable.class))).thenReturn(Page.empty());

    // When & Then
    assertThrows(NotFoundException.class, () -> teacherService.getTeacherByFirstName("John", pageable));
  }

  @Test
  void getTeacherByLastName_whenTeacherIsPresent_expectTeacherDetails() {
    // Given
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", 8277272285L, "email@gmail.com", Gender.MALE));

    when(teacherRepository.findByLastName(eq("Doe"), any(Pageable.class)))
        .thenReturn(new PageImpl<>(teacherDetailsList));
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));

    // When
    Page<TeacherResponseDTO> result = teacherService.getTeacherByLastName("Doe", pageable);

    // Then
    assertThat(result).hasSize(1);
    assertEquals("Doe", result.getContent().get(0).getLastName());
  }

  @Test
  void getTeacherByLastName_whenTeacherIsNotPresent_expectNotFoundException() {
    // Given
    when(teacherRepository.findByLastName(eq("Doe"), any(Pageable.class))).thenReturn(Page.empty());

    // When & Then
    assertThrows(NotFoundException.class, () -> teacherService.getTeacherByLastName("Doe", pageable));
  }
}