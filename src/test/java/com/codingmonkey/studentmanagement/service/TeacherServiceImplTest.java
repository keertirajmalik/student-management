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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.TeacherDTO;
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
    when(subjectRepository.findSubjectEntitiesByTeacherTeacherId(teacherDetailsList.get(0).getTeacherId())).thenReturn(
        List.of(new SubjectEntity("Test", 10)));
    List<TeacherDTO> result = teacherService.getAllTeachers();

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
    when(subjectRepository.findSubjectEntitiesByTeacherTeacherId(teacherDetailsList.get(0).getTeacherId())).thenReturn(
        List.of(new SubjectEntity("Test", teacherDetailsList.get(0).getTeacherId())));
    List<TeacherDTO> result = teacherService.getTeacherByFirstNameAndLastName(teacherDetailsList.get(0).getFirstName(),
        teacherDetailsList.get(0).getLastName());

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
    final TeacherDTO teacherDTO = new TeacherDTO(1, "John", "Doe", Long.valueOf("8277272285"), "keerti@gmailcom",
        List.of("Test"), Gender.MALE);
    final TeacherEntity teacherEntity = new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"),
        "email@gmail.com", Gender.MALE);

    when(modelMapper.map(teacherDTO, TeacherEntity.class)).thenReturn(teacherEntity);
    when(subjectRepository.findSubjectEntitiesByTeacherTeacherId(teacherEntity.getTeacherId())).thenReturn(
        List.of(new SubjectEntity("Test", 10)));
    when(modelMapper.map(teacherEntity, TeacherDTO.class)).thenReturn(teacherDTO);
    ResponseEntity<TeacherDTO> response = teacherService.saveTeacherDetails(teacherDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(teacherDTO, response.getBody());
  }

  @Test
  void saveTeacherDetails_whenTeacherMobileNumberIsInvalid_expect_TeacherDetailsExceptionIsThrown() {
    final TeacherDTO teacherDTO = new TeacherDTO(1, "John", "Doe", Long.valueOf("827727228545"), "keerti@gmailcom",
        List.of("Test"), Gender.MALE);

    assertThrows(TeacherDetailsException.class, () -> teacherService.saveTeacherDetails(teacherDTO));
  }

  @Test
  void saveTeacherDetails_whenTeacherGenderTypeIsInvalid_expect_TeacherDetailsExceptionIsThrown() {
    final TeacherDTO teacherDTO = new TeacherDTO(1, "John", "Doe", Long.valueOf("8277272285"), "keerti@gmailcom",
        List.of("Test"), null);

    assertThrows(TeacherDetailsException.class, () -> teacherService.saveTeacherDetails(teacherDTO));
  }

  @Test
  void saveTeacherDetails_whenSubjectsAreNotPresent_expectNotFoundExceptionIsThrown() {
    final TeacherDTO teacherDTO = new TeacherDTO(1, "John", "Doe", Long.valueOf("8277272285"), "email@gmail.com", null,
        Gender.MALE);
    final TeacherEntity teacherEntity = new TeacherEntity(1, "John", "Doe", Long.valueOf("8277272285"),
        "email@gmail.com", Gender.MALE);

    when(modelMapper.map(teacherDTO, TeacherEntity.class)).thenReturn(teacherEntity);
    when(subjectRepository.findSubjectEntitiesByTeacherTeacherId(teacherEntity.getTeacherId())).thenReturn(
        Collections.emptyList());
    when(modelMapper.map(teacherEntity, TeacherDTO.class)).thenReturn(teacherDTO);

    assertThrows(NotFoundException.class, () -> teacherService.saveTeacherDetails(teacherDTO));
  }
}