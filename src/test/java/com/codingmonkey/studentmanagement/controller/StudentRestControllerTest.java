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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.exception.StudentDetailsException;
import com.codingmonkey.studentmanagement.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {
  private final StudentDTO studentDTO = createStudentDetailsRequest("test", "test", 1, 8277272285L, 10,
      "keerti@gmailcom", Gender.MALE);

  private static final String URL = "/api/students";
  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @MockBean
  private StudentService studentService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  void saveStudentDetail_inputValidStudentDetailRequest_verifiedStatusCodeStudentIdAndSubjects() throws Exception {
    when(studentService.saveStudentDetails(any(StudentDTO.class))).thenReturn(ResponseEntity.ok(studentDTO));

    this.mockMvc.perform(post(URL).content(asJson(studentDTO)).contentType("application/json"))
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
        .content(asJson(createStudentDetailsRequest(null, "test", 1, 8277272285L, 10, "keerti@gmailcom", Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputNullNameForLastName_verifiedStatusCode() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(createStudentDetailsRequest("test", null, 1, 8277272285L, 10, "keerti@gmailcom", Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputNullNameForEmail_verifiedStatusCode() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(createStudentDetailsRequest("test", "test", 1, 8277272285L, 10, null, Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputClassNumberMoreThanAllowed_verifiedStatusCode() throws Exception {
    when(studentService.saveStudentDetails(any(StudentDTO.class))).thenThrow(new StudentDetailsException());

    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(
            asJson(createStudentDetailsRequest("test", "test", 1, 8277272285L, 11, "keerti@gmailcom", Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputNegativeRollNumber_verifiedStatusCode() throws Exception {
    when(studentService.saveStudentDetails(any(StudentDTO.class))).thenThrow(new StudentDetailsException());

    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(
            asJson(createStudentDetailsRequest("test", "test", -10, 8277272285L, 11, "keerti@gmailcom", Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputMobileNumberWithMoreThanTenDigits_verifiedStatusCode() throws Exception {
    when(studentService.saveStudentDetails(any(StudentDTO.class))).thenThrow(new StudentDetailsException());

    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(
            asJson(createStudentDetailsRequest("test", "test", 1, 12345678900L, 11, "keerti@gmailcom", Gender.MALE)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  void saveStudentDetails_inputGenderAsNull_verifiedStatusCode() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = post(URL).contentType("application/json")
        .content(asJson(createStudentDetailsRequest("test", "test", 1, 12345678900L, 11, "keerti@gmailcom", null)));

    this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest());
  }

  private StudentDTO createStudentDetailsRequest(String firstName,
                                                 String lastName,
                                                 int rollNumber,
                                                 long mobileNumber,
                                                 int classNumber,
                                                 String email,
                                                 Gender gender) {
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.setFirstName(firstName);
    studentDTO.setLastName(lastName);
    studentDTO.setRollNumber(rollNumber);
    studentDTO.setMobileNumber(mobileNumber);
    studentDTO.setClassNumber(classNumber);
    studentDTO.setEmail(email);
    studentDTO.setGender(gender);
    studentDTO.setSubjects(List.of("test"));
    return studentDTO;
  }

}