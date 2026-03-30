package com.codingmonkey.studentmanagement.service;

import com.codingmonkey.studentmanagement.domain.dto.request.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.TeacherResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {

  Page<TeacherResponseDTO> getAllTeachers(Pageable pageable);

  Page<TeacherResponseDTO> getTeacherByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

  void deleteById(int teacherId);

  TeacherResponseDTO saveTeacherDetails(TeacherRequestDTO teacherDTO);

  TeacherResponseDTO updateTeacherDetails(int teacherId, TeacherRequestDTO teacherDTO);

  Page<TeacherResponseDTO> getTeacherByFirstName(String firstName, Pageable pageable);

  Page<TeacherResponseDTO> getTeacherByLastName(String lastName, Pageable pageable);
}
