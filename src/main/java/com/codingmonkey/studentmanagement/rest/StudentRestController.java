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

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.Student;
import com.codingmonkey.studentmanagement.exception.NotFoundException;
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
  public List<StudentDTO> findAll() {
    return studentService.findAll();
  }

  @GetMapping("/students/{studentId}")
  public Student getStudent(@PathVariable int studentId) {
    Student student = studentService.findById(studentId);

    if (student == null) {
      throw new NotFoundException("Student not found with id: " + studentId);
    }
    return student;
  }

  @PostMapping("/students")
  public Student addStudent(@RequestBody Student student) {
    student.setStudent_id(0);

    studentService.save(student);

    return student;
  }

  @PutMapping("/students")
  public Student updateStudent(@RequestBody Student student) {

    studentService.save(student);

    return student;
  }

  @DeleteMapping("/students/{studentId}")
  public String deleteStudent(@PathVariable int studentId) {

    studentService.deleteById(studentId);

    return "Student is successfully deleted";
  }

}
