package com.codingmonkey.studentmanagement.rest;

import static com.codingmonkey.studentmanagement.constant.AppConstants.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.service.StudentService;
import com.codingmonkey.studentmanagement.service.StudentServiceImpl;

@RestController
@RequestMapping(value = "/api/students")
public class StudentRestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
  private final StudentService studentService;

  @Autowired
  public StudentRestController(final StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping()
  public List<StudentDTO> getStudent(@RequestParam(value = "firstName", required = false) String firstName,
                                     @RequestParam(value = "lastName", required = false) String lastName) {

    if (firstName == null && lastName == null) {
      LOGGER.info("Get all student details call received");
      return studentService.findAll();
    }

    LOGGER.info("Get [{}] [{}] student details call received", firstName, lastName);
    return studentService.findByFirstNameAndLastName(firstName, lastName);
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<StudentDTO> addStudent(@Valid @RequestBody StudentDTO studentDTO) {

    String logPrefix = "#saveStudentDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, studentDTO.toString());
    return studentService.saveStudentDetails(studentDTO);
  }

  @PutMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<StudentDTO> updateStudent(@Valid @RequestBody StudentDTO studentDTO) {

    return studentService.saveStudentDetails(studentDTO);
  }

  @DeleteMapping("/{studentId}")
  public String deleteStudent(@PathVariable int studentId) {

    studentService.deleteById(studentId);

    return "Student is successfully deleted";
  }

}
