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

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;
import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
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
  @Mock
  private ModelMapper modelMapper;

  @Test
  void getAllStudents_whenStudentsArePresent_expectAllStudentsDetails() {
    List<StudentEntity> studentDetailsList = List.of(
        new StudentEntity(1, "John", "Doe", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));

    when(studentRepository.findAll()).thenReturn(studentDetailsList);
    when(subjectRepository.findSubjectsByClassNumber(studentDetailsList.get(0).getClassNumber())).thenReturn(
        List.of(new SubjectEntity("Test", studentDetailsList.get(0).getClassNumber())));
    List<StudentResponseDTO> result = studentService.getAllStudents();

    //    assertEquals(result, studentDetailsListDto); Unable to compare two lists
    assertThat(result).hasSize(1);
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void getAllStudents_whenNoStudentsArePresent_expectAllStudentsDetails() {
    given(studentRepository.findAll()).willReturn(Collections.emptyList());

    assertEquals(Collections.emptyList(), studentService.getAllStudents());
  }

  @Test
  void getAllStudents_whenSubjectsAreNotPresent_expectNotFoundExceptionIsThrown() {
    List<StudentEntity> studentDetailsList = List.of(
        new StudentEntity(1, "test", "test", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));
    when(studentRepository.findAll()).thenReturn(studentDetailsList);
    assertThrows(NotFoundException.class, () -> studentService.getAllStudents());
  }

  @Test
  void getStudentByFirstNameAndLastName_whenStudentIsPresent_expectStudentDetails() {
    List<StudentEntity> studentDetailsList = List.of(
        new StudentEntity(1, "John", "Doe", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));
    when(studentRepository.findByFirstNameAndLastName(studentDetailsList.get(0).getFirstName(),
        studentDetailsList.get(0).getLastName())).thenReturn(studentDetailsList);
    when(subjectRepository.findSubjectsByClassNumber(studentDetailsList.get(0).getClassNumber())).thenReturn(
        List.of(new SubjectEntity("Test", studentDetailsList.get(0).getClassNumber())));
    List<StudentResponseDTO> result = studentService.getStudentByFirstNameAndLastName(
        studentDetailsList.get(0).getFirstName(), studentDetailsList.get(0).getLastName());

    //    assertEquals(result, studentDetailsListDto); Unable to compare two lists
    assertThat(result).hasSize(1);
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void getStudentByFirstNameAndLastName_whenStudentIsNotPresent_expectNotFoundExceptionIsThrown() {
    List<StudentEntity> studentDetailsList = List.of(
        new StudentEntity(1, "test", "test", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));
    final String firstName = studentDetailsList.get(0).getFirstName();
    final String lastName = studentDetailsList.get(0).getLastName();
    when(studentRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Collections.emptyList());

    assertThrows(NotFoundException.class, () -> studentService.getStudentByFirstNameAndLastName(firstName, lastName));
  }

  @Test
  void saveStudentDetails_whenStudentsArePresent_expectStudentsDetailsAreSaved() {
    final StudentRequestDTO studentDTO = new StudentRequestDTO( "John", "Doe", 8277272285L, "keerti@gmailcom", 10,
        Gender.MALE);
    final StudentResponseDTO studentResponseDTO = new StudentResponseDTO(1, "John", "Doe", 1,
        Long.valueOf("8277272285"), "keerti@gmailcom", 10, List.of("Test"), Gender.MALE);
    final StudentEntity studentEntity = new StudentEntity(1, "John", "Doe", 1, Long.valueOf("8277272285"),
        "email@gmail.com", 10, Gender.MALE);

    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);
    when(modelMapper.map(studentDTO, StudentEntity.class)).thenReturn(studentEntity);
    when(subjectRepository.findSubjectsByClassNumber(studentDTO.getClassNumber())).thenReturn(
        List.of(new SubjectEntity("Test", studentDTO.getClassNumber())));
    when(modelMapper.map(studentEntity, StudentResponseDTO.class)).thenReturn(studentResponseDTO);
    StudentResponseDTO student = studentService.saveStudentDetails(studentDTO);

    assertEquals(studentResponseDTO, student);
  }

  @Test
  void saveStudentDetails_whenStudentsClassIsAboveMaxAllowed_expect_studentDetailsExceptionIsThrown() {
    final StudentRequestDTO studentDTO = new StudentRequestDTO( "John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", 11, Gender.MALE);

    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    assertThrows(StudentDetailsException.class, () -> studentService.saveStudentDetails(studentDTO));
  }

  @Test
  void saveStudentDetails_whenStudentsMobileNumberIsInvalid_expect_studentDetailsExceptionIsThrown() {
    final StudentRequestDTO studentDTO = new StudentRequestDTO( "John", "Doe", Long.valueOf("827727228512"),
        "keerti@gmailcom", 10, Gender.MALE);

    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    assertThrows(StudentDetailsException.class, () -> studentService.saveStudentDetails(studentDTO));
  }

  @Test
  void saveStudentDetails_whenStudentsGenderTypeIsInvalid_expect_studentDetailsExceptionIsThrown() {
    final StudentRequestDTO studentDTO = new StudentRequestDTO( "John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", 10, null);

    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    assertThrows(StudentDetailsException.class, () -> studentService.saveStudentDetails(studentDTO));
  }

  @Test
  void saveStudentDetails_whenSubjectsAreNotPresent_expectNotFoundExceptionIsThrown() {
    final StudentRequestDTO studentDTO = new StudentRequestDTO( "John", "Doe", Long.valueOf("8277272285"),
        "keerti@gmailcom", 10, Gender.MALE);
    final StudentResponseDTO studentResponseDTO = new StudentResponseDTO(1, "John", "Doe", 1,
        Long.valueOf("8277272285"), "keerti@gmailcom", 10, List.of("Test"), Gender.MALE);
    final StudentEntity studentEntity = new StudentEntity(1, "John", "Doe", 1, Long.valueOf("8277272285"),
        "email@gmail.com", 10, Gender.MALE);

    when(modelMapper.map(studentDTO, StudentEntity.class)).thenReturn(studentEntity);
    when(subjectRepository.findSubjectsByClassNumber(studentEntity.getClassNumber())).thenReturn(
        Collections.emptyList());
    when(modelMapper.map(studentEntity, StudentResponseDTO.class)).thenReturn(studentResponseDTO);
    when(applicationConfiguration.getMaxClassAllowed()).thenReturn(10);

    assertThrows(NotFoundException.class, () -> studentService.saveStudentDetails(studentDTO));
  }
}