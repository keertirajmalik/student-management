package com.codingmonkey.studentmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
import com.codingmonkey.studentmanagement.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {
  private final StudentResponseDTO studentDTO = new StudentResponseDTO(1, "test", "test", 1, 8277272285L,
      "keerti@gmailcom", 10, List.of("Test"), Gender.MALE);

  private static final String URL = "/api/students";
  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @MockBean
  private StudentService studentService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void saveStudentDetail_inputValidStudentDetailRequest_verifiedStatusCodeStudentIdAndSubjects() throws Exception {
    when(studentService.saveStudentDetails(any(StudentRequestDTO.class))).thenReturn(ResponseEntity.ok(studentDTO));

    mockMvc.perform(post(URL).content(asJson(studentDTO)).contentType("application/json"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(asJson(studentDTO), true));
  }

  private String asJson(final Object object) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(object);
  }

  @Test
  void saveStudentDetails_inputNullNameForFirstName_verifiedStatusCode() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(new StudentRequestDTO(null, "test", 8277272285L, "keerti@gmailcom", 10, Gender.MALE)));
    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputNullNameForLastName_verifiedStatusCode() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(new StudentRequestDTO("test", null, 8277272285L, "keerti@gmailcom", 10, Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputNullNameForEmail_verifiedStatusCode() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(new StudentRequestDTO("test", "test", 8277272285L, null, 10, Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputClassNumberMoreThanAllowed_verifiedStatusCode() throws Exception {
    when(studentService.saveStudentDetails(any(StudentRequestDTO.class))).thenThrow(
        new StudentDetailsException("Class number cannot be greater than 10", HttpStatus.BAD_REQUEST));

    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(new StudentRequestDTO("test", "test", 8277272285L, "keerti@gmailcom", 10, Gender.MALE)));

    mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputMobileNumberWithMoreThanTenDigits_verifiedStatusCode() throws Exception {
    when(studentService.saveStudentDetails(any(StudentRequestDTO.class))).thenThrow(
        new StudentDetailsException("Mobile number should have only 10 digits", HttpStatus.BAD_REQUEST));

    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(new StudentRequestDTO("test", "test", 12345678900L, "keerti@gmailcom", 11, Gender.MALE)));

    mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputGenderAsNull_verifiedStatusCode() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(new StudentRequestDTO("test", "test", 12345678900L, "keerti@gmailcom", 11, null)));

    mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }
}