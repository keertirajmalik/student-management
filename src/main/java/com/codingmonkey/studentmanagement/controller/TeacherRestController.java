package com.codingmonkey.studentmanagement.controller;

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

import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;
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
  public List<TeacherResponseDTO> getStudent(@RequestParam(value = "firstName", required = false) String firstName,
                                             @RequestParam(value = "lastName", required = false) String lastName) {

    if (firstName == null && lastName == null) {
      LOGGER.info("Get all Teacher details call received");
      return teacherService.getAllTeachers();
    }

    LOGGER.info("Get [{}] [{}] Teacher details call received", firstName, lastName);
    return teacherService.getTeacherByFirstNameAndLastName(firstName, lastName);
  }

  @PostMapping()
  public ResponseEntity<TeacherResponseDTO> addTeacher(@Valid @RequestBody TeacherRequestDTO teacherDTO) {
    String logPrefix = "#addTeacherDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, teacherDTO);
    return teacherService.saveTeacherDetails(teacherDTO);
  }

  @PutMapping(value = "/{teacherId}")
  public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable int teacherId,
                                                          @RequestBody TeacherRequestDTO teacherDTO) {
    String logPrefix = "#updateTeacherDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, teacherDTO);
    return teacherService.updateTeacherDetails(teacherId, teacherDTO);
  }

  @DeleteMapping("{teacherId}")
  public String deleteTeacher(@PathVariable int teacherId) {
    teacherService.deleteById(teacherId);
    return "Teacher is successfully deleted";
  }
}
