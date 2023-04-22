package com.codingmonkey.studentmanagement.controller;

import static com.codingmonkey.studentmanagement.constant.AppConstants.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.service.TeacherService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/teachers", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "Teacher", description = "Teacher API")
class TeacherRestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherRestController.class);
  private final TeacherService teacherService;

  @Autowired
  TeacherRestController(final TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  Map<String, List<TeacherResponseDTO>> getTeacher(@RequestParam(value = "firstName", required = false) String firstName,
                                                   @RequestParam(value = "lastName", required = false) String lastName) {
    List<TeacherResponseDTO> teachers;
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
    return Map.of("teachers", teachers);
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  TeacherResponseDTO addTeacher(@Valid @RequestBody TeacherRequestDTO teacherDTO) {
    String logPrefix = "#addTeacherDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, teacherDTO);
    return teacherService.saveTeacherDetails(teacherDTO);
  }

  @PutMapping("{teacherId}")
  @ResponseStatus(HttpStatus.OK)
  TeacherResponseDTO updateTeacher(@PathVariable int teacherId, @RequestBody TeacherRequestDTO teacherDTO) {
    String logPrefix = "#updateTeacherDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, teacherDTO);
    return teacherService.updateTeacherDetails(teacherId, teacherDTO);
  }

  @DeleteMapping("{teacherId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteTeacher(@PathVariable int teacherId) {
    teacherService.deleteById(teacherId);
  }
}
