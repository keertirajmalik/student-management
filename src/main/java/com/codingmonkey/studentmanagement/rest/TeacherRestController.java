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

import com.codingmonkey.studentmanagement.entity.Teacher;
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
  public List<Teacher> findAll() {
    return teacherService.findAll();
  }

  @GetMapping("/teachers/{teacherId}")
  public Teacher getTeacher(@PathVariable int teacherId) {
    Teacher teacher = teacherService.findById(teacherId);

    if (teacher == null) {
      throw new RuntimeException("Student not found with id: " + teacherId);
    }
    return teacher;
  }

  @PostMapping("/teachers")
  public Teacher addTeacher(@RequestBody Teacher teacher) {
    teacher.setId(0);

    teacherService.save(teacher);

    return teacher;
  }

  @PutMapping("/teachers")
  public Teacher updateTeacher(@RequestBody Teacher teacher) {

    teacherService.save(teacher);

    return teacher;
  }

  @DeleteMapping("/teachers/{teacherId}")
  public String deleteTeacher(@PathVariable int teacherId) {

    teacherService.deleteById(teacherId);

    return "Student is successfully deleted";
  }
}
