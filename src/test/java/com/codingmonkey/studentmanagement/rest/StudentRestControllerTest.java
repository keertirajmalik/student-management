package com.codingmonkey.studentmanagement.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;
import com.codingmonkey.studentmanagement.repositories.StudentRepository;
import com.codingmonkey.studentmanagement.repositories.SubjectRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentRestControllerTest {

  private static final String HOST_NAME = "http://localhost:";
  private static final String CONTEXT_PATH = "/api";
  private static final String STUDENTS_DETAILS_PATH = "/students";
  private static final String URL = "/api/students";

  @LocalServerPort
  protected int port;
  @Autowired
  ModelMapper modelMapper;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private SubjectRepository subjectRepository;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private WebApplicationContext context;
  private MockMvc mockMvc;

  protected String createURLWithPort(final String uri) {
    return HOST_NAME + port + CONTEXT_PATH + uri;
  }

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    subjectRepository.deleteAll();
    studentRepository.deleteAll();
    studentRepository.saveAndFlush(
        new StudentEntity("Keertiraj", "Malik", 1, 8277272285L, "keerti@gmailcom", 10, Gender.MALE));
    subjectRepository.saveAndFlush(new SubjectEntity("Kannda", 10));
  }

  @Test
  void saveStudentDetails_savingOneStudentDetail_expectStatusCreatedAndVerifiedResponseAndStudentDetailsIsSavedInDb() {
    subjectRepository.saveAndFlush(new SubjectEntity("English", 10));

    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH), new HttpEntity(createStudentDetailsRequest()), StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(Objects.requireNonNull(studentResponseEntity.getBody()).getStudentId()).isEqualTo(2);
  }

  @Test
  public void getAllStudents_fetchAllStudentsFromDb_expectSuccessResponse() throws Exception {
    subjectRepository.deleteAll();
    studentRepository.deleteAll();

    studentRepository.saveAndFlush(
        new StudentEntity("Keertiraj", "Malik", 1, 8277272285L, "keerti@gmailcom", 10, Gender.MALE));
    subjectRepository.saveAndFlush(new SubjectEntity("Kannda", 10));

    mockMvc.perform(get(URL)).andExpect(status().isOk()).andReturn();
  }

  private StudentDTO createStudentDetailsRequest() {
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.setFirstName("Keertiraj");
    studentDTO.setLastName("Malik");
    studentDTO.setRollNumber(1);
    studentDTO.setMobileNumber(8277272285L);
    studentDTO.setClassNumber(10);
    studentDTO.setEmail("keerti@gmailcom");
    studentDTO.setGender(Gender.MALE);
    studentDTO.setSubjects(null);
    return studentDTO;
  }

}