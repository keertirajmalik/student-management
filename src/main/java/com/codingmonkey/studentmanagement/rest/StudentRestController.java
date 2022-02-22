package com.codingmonkey.studentmanagement.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingmonkey.studentmanagement.entity.Student;
import com.codingmonkey.studentmanagement.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentRestController {

  private final StudentService studentService;

  @Autowired
  public StudentRestController(final StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/students")
  public List<Student> findAll() {
    return studentService.findAll();
  }
}
