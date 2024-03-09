package com.codingmonkey.studentmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentRestController.class)
class TeacherRestControllerTest {
  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String URL = "/api/students";
  @MockBean
  private TeacherService teacherService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void getAllTeachers_returnsAllTeachers() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getAllTeachers()).thenReturn(teachers);

    mockMvc.perform(get(URL))
        .andExpect(status().isOk())
        .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(Map.of("teachers", teachers))));
  }

  @Test
  void getTeacherByLastName_returnsTeachersWithMatchingLastName() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getTeacherByLastName("Doe")).thenReturn(teachers);

    mockMvc.perform(get(URL).param("lastName", "Doe"))
        .andExpect(status().isOk())
        .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(Map.of("teachers", teachers))));
  }

  @Test
  void getTeacherByFirstName_returnsTeachersWithMatchingFirstName() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getTeacherByFirstName("John")).thenReturn(teachers);

    mockMvc.perform(get(URL).param("firstName", "John"))
        .andExpect(status().isOk())
        .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(Map.of("teachers", teachers))));
  }

  @Test
  void getTeacherByFirstNameAndLastName_returnsTeachersWithMatchingFirstAndLastName() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getTeacherByFirstNameAndLastName("John", "Doe")).thenReturn(teachers);

    mockMvc.perform(get(URL).param("firstName", "John").param("lastName", "Doe"))
        .andExpect(status().isOk())
        .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(Map.of("teachers", teachers))));
  }

  @Test
  void saveTeacherDetails_whenTeacherDetailsAreValid_createsAndReturnsTeacher() throws Exception {
    TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO("John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    TeacherResponseDTO teacherDTO = new TeacherResponseDTO(1, "John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    when(teacherService.saveTeacherDetails(any(TeacherRequestDTO.class))).thenReturn(teacherDTO);

    mockMvc.perform(
            post(URL).contentType(MediaType.APPLICATION_JSON).content(OBJECT_MAPPER.writeValueAsString(teacherRequestDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(teacherDTO)));
  }

  @Test
  void updateTeacherDetails_whenTeacherExists_updatesAndReturnsTeacher() throws Exception {
    TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO("John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    TeacherResponseDTO teacherDTO = new TeacherResponseDTO(1, "John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    when(teacherService.updateTeacherDetails(anyInt(), any(TeacherRequestDTO.class))).thenReturn(teacherDTO);

    mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(teacherRequestDTO)))
        .andExpect(status().isOk())
        .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(teacherDTO)));
  }

  @Test
  void deleteTeacher_whenTeacherExists_deletesTeacher() throws Exception {
    doNothing().when(teacherService).deleteById(1);

    mockMvc.perform(delete(URL + "/1")).andExpect(status().isNoContent());
  }
}