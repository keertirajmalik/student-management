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
    subjectRepository.saveAndFlush(new SubjectEntity("English", 10));
  }

  @Test
  void saveStudentDetail_inputValidStudentDetailRequest_verifiedStatusCodeStudentIdAndSubjects() {
    studentRepository.deleteAll();
    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH), new HttpEntity<>(
            createStudentDetailsRequest("test", "test", 1, 8277272285L, 10, "keerti@gmailcom", Gender.MALE)),
        StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(Objects.requireNonNull(studentResponseEntity.getBody()).getStudentId()).isEqualTo(10);
    assertThat(studentResponseEntity.getBody().getSubjects()).hasSize(2);
  }

  @Test
  void saveStudentDetails_inputNullNameForFirstName_verifiedStatusCode() {
    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH),
        new HttpEntity<>(createStudentDetailsRequest(null, "test", 1, 8277272285L, 10, "keerti@gmailcom", Gender.MALE)),
        StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void saveStudentDetails_inputNullNameForLastName_verifiedStatusCode() {
    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH),
        new HttpEntity<>(createStudentDetailsRequest("test", null, 1, 8277272285L, 10, "keerti@gmailcom", Gender.MALE)),
        StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void saveStudentDetails_inputNullNameForEmail_verifiedStatusCode() {
    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH),
        new HttpEntity<>(createStudentDetailsRequest("test", "test", 1, 8277272285L, 10, null, Gender.MALE)),
        StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void saveStudentDetails_inputClassNumberMoreThanAllowed_verifiedStatusCode() {
    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH), new HttpEntity<>(
            createStudentDetailsRequest("test", "test", 1, 8277272285L, 11, "keerti@gmailcom", Gender.MALE)),
        StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void saveStudentDetails_inputMobileNumberWithMoreThanTenDigits_verifiedStatusCode() {
    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH), new HttpEntity<>(
            createStudentDetailsRequest("test", "test", 1, 12345678900L, 11, "keerti@gmailcom", Gender.MALE)),
        StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void saveStudentDetails_inputGenderAsNull_verifiedStatusCode() {
    final ResponseEntity<StudentDTO> studentResponseEntity = restTemplate.postForEntity(
        createURLWithPort(STUDENTS_DETAILS_PATH),
        new HttpEntity<>(createStudentDetailsRequest("test", "test", 1, 12345678900L, 11, "keerti@gmailcom", null)),
        StudentDTO.class);
    assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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
    studentDTO.setSubjects(null);
    return studentDTO;
  }

}