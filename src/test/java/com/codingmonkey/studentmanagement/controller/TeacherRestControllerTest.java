package com.codingmonkey.studentmanagement.controller;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.domain.dto.request.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.service.TeacherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeacherRestController.class)
class TeacherRestControllerTest {

  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String URL = "/api/teachers";
  @MockitoBean
  private TeacherService teacherService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void getAllTeachers_returnsAllTeachers() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getAllTeachers(any(Pageable.class))).thenReturn(new PageImpl<>(teachers));

    mockMvc.perform(get(URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  private String asJson(final Object object) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(object);
  }

  @Test
  void getTeacherByLastName_returnsTeachersWithMatchingLastName() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getTeacherByLastName(eq("Doe"), any(Pageable.class))).thenReturn(new PageImpl<>(teachers));

    mockMvc.perform(get(URL).param("lastName", "Doe"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  void getTeacherByFirstName_returnsTeachersWithMatchingFirstName() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getTeacherByFirstName(eq("John"), any(Pageable.class))).thenReturn(new PageImpl<>(teachers));

    mockMvc.perform(get(URL).param("firstName", "John"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  void getTeacherByFirstNameAndLastName_returnsTeachersWithMatchingFirstAndLastName() throws Exception {
    List<TeacherResponseDTO> teachers = Collections.emptyList();
    when(teacherService.getTeacherByFirstNameAndLastName(eq("John"), eq("Doe"), any(Pageable.class))).thenReturn(new PageImpl<>(teachers));

    mockMvc.perform(get(URL).param("firstName", "John").param("lastName", "Doe"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  void saveTeacherDetails_whenTeacherDetailsAreValid_createsAndReturnsTeacher() throws Exception {
    TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO("John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    TeacherResponseDTO teacherDTO = new TeacherResponseDTO(1, "John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    when(teacherService.saveTeacherDetails(any(TeacherRequestDTO.class))).thenReturn(teacherDTO);

    mockMvc.perform(
            post(URL).contentType(MediaType.APPLICATION_JSON).content(asJson(teacherRequestDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().json(asJson(teacherDTO), JsonCompareMode.STRICT));
  }

  @Test
  void updateTeacherDetails_whenTeacherExists_updatesAndReturnsTeacher() throws Exception {
    TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO("John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    TeacherResponseDTO teacherDTO = new TeacherResponseDTO(1, "John", "Doe", 8277272285L, "email@gmail.com",
        Gender.MALE, Collections.emptyList());
    when(teacherService.updateTeacherDetails(anyInt(), any(TeacherRequestDTO.class))).thenReturn(teacherDTO);

    mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON)
            .content(asJson(teacherRequestDTO)))
        .andExpect(status().isOk())
        .andExpect(content().json(asJson(teacherDTO), JsonCompareMode.STRICT));
  }

  @Test
  void deleteTeacher_whenTeacherExists_deletesTeacher() throws Exception {
    doNothing().when(teacherService).deleteById(1);

    mockMvc.perform(delete(URL + "/1")).andExpect(status().isNoContent());
  }
}