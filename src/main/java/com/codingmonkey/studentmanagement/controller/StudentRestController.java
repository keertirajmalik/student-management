package com.codingmonkey.studentmanagement.controller;

import static com.codingmonkey.studentmanagement.constant.AppConstants.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.service.StudentService;

@RestController
@RequestMapping(value = "/api/students", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class StudentRestController {
  private static final Logger LOGGER = LoggerFactory.getLogger(StudentRestController.class);
  private final StudentService studentService;

  public StudentRestController(final StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping()
  public List<StudentResponseDTO> getStudent(@RequestParam(value = "firstName", required = false) String firstName,
                                             @RequestParam(value = "lastName", required = false) String lastName) {
    if (firstName == null && lastName == null) {
      LOGGER.info("Get all student details call received");
      return studentService.getAllStudents();
    }
    LOGGER.info("Get [{}] [{}] student details call received", firstName, lastName);
    return studentService.getStudentByFirstNameAndLastName(firstName, lastName);
  }

  @PostMapping()
  public ResponseEntity<StudentResponseDTO> addStudent(@Valid @RequestBody StudentRequestDTO studentDTO) {
    String logPrefix = "#saveStudentDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, studentDTO);
    return studentService.saveStudentDetails(studentDTO);
  }

  @PutMapping(value = "/{studentId}")
  public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable int studentId,
                                                          @Valid @RequestBody StudentRequestDTO studentDTO) {
    return studentService.updateStudentDetails(studentId, studentDTO);
  }

  @DeleteMapping("/{studentId}")
  public String deleteStudent(@PathVariable int studentId) {
    studentService.deleteById(studentId);

    return "Student is successfully deleted";
  }

}
