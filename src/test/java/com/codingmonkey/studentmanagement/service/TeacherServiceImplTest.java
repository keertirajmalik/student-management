package com.codingmonkey.studentmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.TeacherDetailsException;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
  @InjectMocks
  private TeacherServiceImpl teacherService;
  @Mock
  private TeacherRepository teacherRepository;
  @Mock
  private SubjectRepository subjectRepository;
  @Mock
  private ModelMapper modelMapper;

  @Test
  void getAllTeachers_whenTeachersArePresent_expectAllTeachersDetails() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));

    when(teacherRepository.findAll()).thenReturn(teacherDetailsList);
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));
    List<TeacherResponseDTO> result = teacherService.getAllTeachers();

    assertThat(result).hasSize(1);
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void getAllTeachers_whenNoTeachersArePresent_expectAllTeachersDetails() {
    given(teacherRepository.findAll()).willReturn(Collections.emptyList());

    assertEquals(Collections.emptyList(), teacherService.getAllTeachers());
  }

  @Test
  void getAllTeachers_whenSubjectsAreNotPresent_expectNotFoundExceptionIsThrown() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));
    when(teacherRepository.findAll()).thenReturn(teacherDetailsList);
    assertThrows(NotFoundException.class, () -> teacherService.getAllTeachers());
  }

  @Test
  void getTeacherByFirstNameAndLastName_whenTeacherIsPresent_expectTeacherDetails() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));
    when(teacherRepository.findByFirstNameAndLastName(teacherDetailsList.get(0).getFirstName(),
        teacherDetailsList.get(0).getLastName())).thenReturn(teacherDetailsList);
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));
    List<TeacherResponseDTO> result = teacherService.getTeacherByFirstNameAndLastName(
        teacherDetailsList.get(0).getFirstName(), teacherDetailsList.get(0).getLastName());

    //    assertEquals(result, studentDetailsListDto); Unable to compare two lists
    assertThat(result).hasSize(1);
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void getTeacherByFirstNameAndLastName_whenTeacherIsNotPresent_expectNotFoundExceptionIsThrown() {
    List<TeacherEntity> teacherDetailsList = List.of(
        new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", Gender.MALE));
    final String firstName = teacherDetailsList.get(0).getFirstName();
    final String lastName = teacherDetailsList.get(0).getLastName();
    when(teacherRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Collections.emptyList());

    assertThrows(NotFoundException.class, () -> teacherService.getTeacherByFirstNameAndLastName(firstName, lastName));
  }

  @Test
  void saveTeacherDetails_whenTeachersArePresent_expectTeachersDetailsAreSaved() {
    final TeacherRequestDTO teacherDTO = new TeacherRequestDTO("John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", Gender.MALE, List.of("Test"));
    final TeacherResponseDTO teacherResponseDTO = new TeacherResponseDTO(1, "John", "Doe", Long.valueOf("8277272285"),
        "email@gmail.com", Gender.MALE, List.of("Test"));
    final TeacherEntity teacherEntity = new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"),
        "email@gmail.com", Gender.MALE);

    when(modelMapper.map(teacherDTO, TeacherEntity.class)).thenReturn(teacherEntity);
    when(subjectRepository.findAll()).thenReturn(List.of(new SubjectEntity(1, "Test", 10)));
    when(modelMapper.map(teacherEntity, TeacherResponseDTO.class)).thenReturn(teacherResponseDTO);
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

    when(modelMapper.map(teacherDTO, TeacherEntity.class)).thenReturn(teacherEntity);
    when(subjectRepository.findAll()).thenReturn(Collections.emptyList());

    assertThrows(NotFoundException.class, () -> teacherService.saveTeacherDetails(teacherDTO));
  }
}