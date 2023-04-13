package com.codingmonkey.studentmanagement.service;

import java.util.List;

import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;

public interface TeacherService {

  List<TeacherResponseDTO> getAllTeachers();

  List<TeacherResponseDTO> getTeacherByFirstNameAndLastName(String firstName, String lastName);

  void deleteById(int teacherId);

  TeacherResponseDTO saveTeacherDetails(TeacherRequestDTO teacherDTO);

  TeacherResponseDTO updateTeacherDetails(int teacherId, TeacherRequestDTO teacherDTO);

  List<TeacherResponseDTO> getTeacherByFirstName(String firstName);

  List<TeacherResponseDTO> getTeacherByLastName(String lastName);
}
