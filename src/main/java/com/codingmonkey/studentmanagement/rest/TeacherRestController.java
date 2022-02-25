package com.codingmonkey.studentmanagement.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.service.TeacherService;

@RestController
@RequestMapping("/api")
public class TeacherRestController {

  private final TeacherService teacherService;

  @Autowired
  public TeacherRestController(final TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping("/teachers")
  public List<TeacherDTO> findAll() {
    return teacherService.findAll();
  }

  @GetMapping("/teachers/{teacherId}")
  public TeacherDTO getTeacher(@PathVariable int teacherId) {
    TeacherDTO teacher = teacherService.findById(teacherId);

    if (teacher == null) {
      throw new RuntimeException("Teacher not found with id: " + teacherId);
    }
    return teacher;
  }

  @PostMapping("/teachers")
  public TeacherEntity addTeacher(@RequestBody TeacherEntity teacherEntity) {
    teacherEntity.setTeacherId(0);

    teacherService.save(teacherEntity);

    return teacherEntity;
  }

  @PutMapping("/teachers")
  public TeacherEntity updateTeacher(@RequestBody TeacherEntity teacherEntity) {

    teacherService.save(teacherEntity);

    return teacherEntity;
  }

  @DeleteMapping("/teachers/{teacherId}")
  public String deleteTeacher(@PathVariable int teacherId) {

    teacherService.deleteById(teacherId);

    return "Teacher is successfully deleted";
  }
}
