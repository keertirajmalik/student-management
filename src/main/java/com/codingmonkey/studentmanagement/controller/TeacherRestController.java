package com.codingmonkey.studentmanagement.controller;

import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.codingmonkey.studentmanagement.constant.AppConstants.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/teachers")
@Tag(name = "Teacher", description = "Teacher API")
class TeacherRestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherRestController.class);
  private final TeacherService teacherService;

  @Autowired
  TeacherRestController(final TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @Operation(summary = "Get teachers details")
  @GetMapping(produces = APPLICATION_JSON_VALUE)
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

  @Operation(summary = "Add new teacher details")
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  TeacherResponseDTO addTeacher(@Valid @RequestBody TeacherRequestDTO teacherDTO) {
    String logPrefix = "#addTeacherDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, teacherDTO);
    return teacherService.saveTeacherDetails(teacherDTO);
  }

  @Operation(summary = "Update teacher details")
  @PutMapping(value = "{teacherId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  TeacherResponseDTO updateTeacher(@PathVariable int teacherId, @RequestBody TeacherRequestDTO teacherDTO) {
    String logPrefix = "#updateTeacherDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, teacherDTO);
    return teacherService.updateTeacherDetails(teacherId, teacherDTO);
  }

  @Operation(summary = "Delete teacher details")
  @DeleteMapping(value = "{teacherId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteTeacher(@PathVariable int teacherId) {
    teacherService.deleteById(teacherId);
  }
}
