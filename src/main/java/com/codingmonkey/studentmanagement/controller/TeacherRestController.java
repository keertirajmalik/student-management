package com.codingmonkey.studentmanagement.controller;

import static com.codingmonkey.studentmanagement.constant.AppConstants.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.service.TeacherService;

@RestController
@RequestMapping(value = "/api/teachers", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class TeacherRestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherRestController.class);
  private final TeacherService teacherService;

  @Autowired
  public TeacherRestController(final TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping()
  public ResponseEntity<Map<String, List<TeacherDTO>>> getTeacher(@RequestParam(value = "firstName", required = false) String firstName,
                                                                  @RequestParam(value = "lastName", required = false) String lastName) {
    List<TeacherDTO> teachers;
    if (firstName == null && lastName == null) {
      LOGGER.info("Get all teachers details call received");
      teachers = teacherService.getAllTeachers();
    } else if (firstName == null) {
      LOGGER.info("Get [{}] teacher details call received", lastName);
      teachers = teacherService.getTeacherByLastName(lastName);
    } else if (lastName == null) {
      LOGGER.info("Get [{}] teacher details call received", firstName);
      teachers = teacherService.getTeacherByFirstName(firstName);
    } else {
      LOGGER.info("Get [{}] [{}] teacher details call received", firstName, lastName);
      teachers = teacherService.getTeacherByFirstNameAndLastName(firstName, lastName);
    }
    Map<String, List<TeacherDTO>> response = Map.of("teachers", teachers);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<TeacherDTO> addTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.saveTeacherDetails(teacherDTO));
  }

  @PutMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<TeacherDTO> updateTeacher(@RequestBody TeacherDTO teacherDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.saveTeacherDetails(teacherDTO));
  }

  @DeleteMapping("{teacherId}")
  public ResponseEntity<Void> deleteTeacher(@PathVariable int teacherId) {
    teacherService.deleteById(teacherId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
