package com.codingmonkey.studentmanagement.controller;

import com.codingmonkey.studentmanagement.domain.dto.request.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.domain.dto.response.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
  Page<TeacherResponseDTO> getTeacher(@RequestParam(value = "firstName", required = false) String firstName,
                                      @RequestParam(value = "lastName", required = false) String lastName, Pageable pageable) {

    LOGGER.info("Get [{}] [{}] student details call received", firstName, lastName);
    if (firstName == null && lastName == null) {
      return teacherService.getAllTeachers(pageable);
    } else if (firstName == null) {
      return teacherService.getTeacherByLastName(lastName, pageable);
    } else if (lastName == null) {
      return teacherService.getTeacherByFirstName(firstName, pageable);
    } else {
      return teacherService.getTeacherByFirstNameAndLastName(firstName, lastName, pageable);
    }
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
  @PutMapping(value = "/{teacherId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  TeacherResponseDTO updateTeacher(@PathVariable int teacherId, @Valid @RequestBody TeacherRequestDTO teacherDTO) {
    String logPrefix = "#updateTeacherDetails(): ";
    LOGGER.info("{} Request Received as {} ", logPrefix, teacherDTO);
    return teacherService.updateTeacherDetails(teacherId, teacherDTO);
  }

  @Operation(summary = "Delete teacher details")
  @DeleteMapping(value = "/{teacherId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteTeacher(@PathVariable int teacherId) {
    teacherService.deleteById(teacherId);
  }
}
