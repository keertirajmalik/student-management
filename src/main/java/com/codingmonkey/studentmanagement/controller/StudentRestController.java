package com.codingmonkey.studentmanagement.controller;

import com.codingmonkey.studentmanagement.domain.dto.request.StudentRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.StudentResponseDTO;
import com.codingmonkey.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.codingmonkey.studentmanagement.constant.AppConstants.APPLICATION_JSON_VALUE;

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
  Page<StudentResponseDTO> getStudent(@RequestParam(value = "firstName", required = false) String firstName,
                                      @RequestParam(value = "lastName", required = false) String lastName, Pageable pageable) {

    LOGGER.info("Get [{}] [{}] student details call received", firstName, lastName);
    if (firstName == null && lastName == null) {
      return studentService.getAllStudents(pageable);
    } else if (firstName == null) {
      return studentService.getStudentByLastName(lastName, pageable);
    } else if (lastName == null) {
      return studentService.getStudentByFirstName(firstName, pageable);
    } else {
      return studentService.getStudentByFirstNameAndLastName(firstName, lastName, pageable);
    }
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
