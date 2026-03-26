package com.codingmonkey.studentmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;
import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
import com.codingmonkey.studentmanagement.mapper.StudentMapper;
import com.codingmonkey.studentmanagement.repositories.StudentRepository;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

  @InjectMocks
  private StudentServiceImpl studentService;
  @Mock
  private StudentRepository studentRepository;
  @Mock
  private SubjectRepository subjectRepository;
  @Mock
  private ApplicationConfiguration applicationConfiguration;
  @Spy
  private StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

  @Test
  void getAllStudents_whenStudentsArePresent_expectAllStudentsDetails() {
    // Given
    List<StudentEntity> studentDetailsList = List.of(
        new StudentEntity(1, "John", "Doe", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));
    when(studentRepository.findAll()).thenReturn(studentDetailsList);
    when(subjectRepository.findSubjectsByClassNumber(studentDetailsList.get(0).getClassNumber())).thenReturn(
        List.of(new SubjectEntity(1, "Test", 10)));

    // When
    List<StudentResponseDTO> result = studentService.getAllStudents();

    // Then
    assertThat(result).hasSize(1);
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void getAllStudents_whenNoStudentsArePresent_expectEmptyList() {
    // Given
    when(studentRepository.findAll()).thenReturn(Collections.emptyList());

    // When
    List<StudentResponseDTO> result = studentService.getAllStudents();

    // Then
    assertTrue(result.isEmpty());
  }

  @Test
  void saveStudentDetails_whenStudentDetailsAreValid_expectStudentDetailsAreSaved() {
    // Given
    final StudentRequestDTO studentDTO = new StudentRequestDTO("John", "Doe", 8277272285L, "keerti@gmailcom", 10,
        Gender.MALE);
    final StudentResponseDTO expectedStudentResponseDTO = new StudentResponseDTO(0, "John", "Doe", 1,
        Long.valueOf("8277272285"), "keerti@gmailcom", 10, List.of("Test"), Gender.MALE);
    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);
    when(subjectRepository.findSubjectsByClassNumber(studentDTO.getClassNumber())).thenReturn(
        List.of(new SubjectEntity(1, "Test", 10)));

    // When
    StudentResponseDTO actualStudentResponseDTO = studentService.saveStudentDetails(studentDTO);

    // Then
    assertEquals(expectedStudentResponseDTO.getStudentId(), actualStudentResponseDTO.getStudentId());
    assertEquals(expectedStudentResponseDTO.getFirstName(), actualStudentResponseDTO.getFirstName());
    assertEquals(expectedStudentResponseDTO.getLastName(), actualStudentResponseDTO.getLastName());
    assertEquals(expectedStudentResponseDTO.getRollNumber(), actualStudentResponseDTO.getRollNumber());
    assertEquals(expectedStudentResponseDTO.getMobileNumber(), actualStudentResponseDTO.getMobileNumber());
    assertEquals(expectedStudentResponseDTO.getEmail(), actualStudentResponseDTO.getEmail());
    assertEquals(expectedStudentResponseDTO.getClassNumber(), actualStudentResponseDTO.getClassNumber());
    assertEquals(expectedStudentResponseDTO.getSubjects(), actualStudentResponseDTO.getSubjects());
    assertEquals(expectedStudentResponseDTO.getGender(), actualStudentResponseDTO.getGender());
  }

  @Test
  void saveStudentDetails_whenClassNumberIsAboveMaxAllowed_expectStudentDetailsException() {
    // Given
    final StudentRequestDTO studentDTO = new StudentRequestDTO("John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", 11, Gender.MALE);
    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    // When & Then
    assertThrows(StudentDetailsException.class, () -> studentService.saveStudentDetails(studentDTO));
  }

  @Test
  void saveStudentDetails_whenMobileNumberIsInvalid_expectStudentDetailsException() {
    // Given
    final StudentRequestDTO studentDTO = new StudentRequestDTO("John", "Doe", Long.valueOf("827727228512"),
        "keerti@gmailcom", 10, Gender.MALE);
    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    // When & Then
    assertThrows(StudentDetailsException.class, () -> studentService.saveStudentDetails(studentDTO));
  }

  @Test
  void saveStudentDetails_whenGenderTypeIsInvalid_expectStudentDetailsException() {
    // Given
    final StudentRequestDTO studentDTO = new StudentRequestDTO("John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", 10, null);

    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    // When & Then
    assertThrows(StudentDetailsException.class, () -> studentService.saveStudentDetails(studentDTO));
  }

  @Test
  void saveStudentDetails_whenSubjectsAreNotPresent_expectNotFoundException() {
    // Given
    final StudentRequestDTO studentDTO = new StudentRequestDTO("John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", 10, Gender.MALE);
    final StudentResponseDTO studentResponseDTO = new StudentResponseDTO(1, "John", "Doe", 1,
        Long.valueOf("8277272285"), "keerti@gmailcom", 10, List.of("Test"), Gender.MALE);
    final StudentEntity studentEntity = new StudentEntity(1, "John", "Doe", 1, Long.valueOf("8277272285"),
        "email@gmail.com", 10, Gender.MALE);

    when(subjectRepository.findSubjectsByClassNumber(studentEntity.getClassNumber())).thenReturn(
        Collections.emptyList());
    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    // When & Then
    assertThrows(NotFoundException.class, () -> studentService.saveStudentDetails(studentDTO));
  }
}