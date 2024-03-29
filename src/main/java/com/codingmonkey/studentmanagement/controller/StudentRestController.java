package com.codingmonkey.studentmanagement.controller;

import static com.codingmonkey.studentmanagement.constant.AppConstants.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/students")
@Tag(name = "Student", description = "Student API")
class StudentRestController {
  private static final Logger LOGGER = LoggerFactory.getLogger(StudentRestController.class);
  private final StudentService studentService;

  public StudentRestController(final StudentService studentService) {
    this.studentService = studentService;
  }

  @Operation(summary = "Get students details")
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  Map<String, List<StudentResponseDTO>> getStudent(@RequestParam(value = "firstName", required = false) String firstName,
                                                   @RequestParam(value = "lastName", required = false) String lastName) {
    List<StudentResponseDTO> students;
    if (firstName == null && lastName == null) {
      LOGGER.info("Get all student details call received");
      students = studentService.getAllStudents();
    } else if (firstName == null) {
      LOGGER.info("Get [{}] student details call received", lastName);
      students = studentService.getStudentByLastName(lastName);
    } else if (lastName == null) {
      LOGGER.info("Get [{}] student details call received", firstName);
      students = studentService.getStudentByFirstName(firstName);

    } else {
      LOGGER.info("Get [{}] [{}] student details call received", firstName, lastName);
      students = studentService.getStudentByFirstNameAndLastName(firstName, lastName);
    }
    return Map.of("students", students);
  }

  @Operation(summary = "Add new student details")
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  StudentResponseDTO addStudent(@Valid @RequestBody StudentRequestDTO studentDTO) {
    String logPrefix = "#saveStudentDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, studentDTO);
    return studentService.saveStudentDetails(studentDTO);
  }

  @Operation(summary = "Update student details")
  @PutMapping(value = "/{studentId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  StudentResponseDTO updateStudent(@PathVariable int studentId, @Valid @RequestBody StudentRequestDTO studentDTO) {
    String logPrefix = "#updateStudentDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, studentDTO);
    return studentService.updateStudentDetails(studentId, studentDTO);
  }

  @Operation(summary = "Delete student details")
  @DeleteMapping("/{studentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteStudent(@PathVariable int studentId) {
    studentService.deleteById(studentId);
  }
}
