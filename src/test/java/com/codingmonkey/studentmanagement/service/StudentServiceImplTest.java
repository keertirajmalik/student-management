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

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
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

  @Test
  void getAllStudents_whenStudentsArePresent_expectAllStudentsDetails() {
    List<StudentEntity> studentDetailsList = List.of(
        new StudentEntity(1, "test", "test", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));

    when(studentRepository.findAll()).thenReturn(studentDetailsList);
    when(subjectRepository.findSubjectsByClassNumber(studentDetailsList.get(0).getClassNumber())).thenReturn(
        List.of(new SubjectEntity("Test", studentDetailsList.get(0).getClassNumber())));
    List<StudentDTO> result = studentService.getAllStudents();

    //    assertEquals(result, studentDetailsListDto); Unable to compare two lists
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  void getAllStudents_whenNoStudentsArePresent_expectAllStudentsDetails() {
    given(studentRepository.findAll()).willReturn(Collections.emptyList());

    assertEquals(studentService.getAllStudents(), Collections.emptyList());
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
        new StudentEntity(1, "test", "test", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));
    when(studentRepository.findByFirstNameAndLastName(studentDetailsList.get(0).getFirstName(),
        studentDetailsList.get(0).getLastName())).thenReturn(studentDetailsList);
    when(subjectRepository.findSubjectsByClassNumber(studentDetailsList.get(0).getClassNumber())).thenReturn(
        List.of(new SubjectEntity("Test", studentDetailsList.get(0).getClassNumber())));
    List<StudentDTO> result = studentService.getStudentByFirstNameAndLastName(studentDetailsList.get(0).getFirstName(),
        studentDetailsList.get(0).getLastName());

    //    assertEquals(result, studentDetailsListDto); Unable to compare two lists
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  void getStudentByFirstNameAndLastName_whenStudentIsNotPresent_expectNotFoundExceptionIsThrown() {
    List<StudentEntity> studentDetailsList = List.of(
        new StudentEntity(1, "test", "test", 10, Long.valueOf("8277272285"), "email@gmail.com", 1, Gender.MALE));
    when(studentRepository.findByFirstNameAndLastName(studentDetailsList.get(0).getFirstName(),
        studentDetailsList.get(0).getLastName())).thenReturn(Collections.emptyList());

    assertThrows(NotFoundException.class,
        () -> studentService.getStudentByFirstNameAndLastName(studentDetailsList.get(0).getFirstName(),
            studentDetailsList.get(0).getLastName()));
  }
}