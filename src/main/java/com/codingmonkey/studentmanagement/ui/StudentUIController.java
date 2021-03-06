package com.codingmonkey.studentmanagement.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentUIController {

  private final StudentService studentService;

  @Autowired
  public StudentUIController(final StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/list")
  public String listCustomers(Model model) {
    List<StudentDTO> studentList = studentService.findAll();
    model.addAttribute("student", studentList);
    return "list-students";
  }

  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model theModel) {

    // create model attribute to bind form data
    StudentEntity studentEntity = new StudentEntity();

    theModel.addAttribute("student", studentEntity);

    return "student-form";
  }

  @PostMapping("/saveStudent")
  public String saveStudent(@ModelAttribute("student") StudentEntity studentEntity) {

    // save the customer using our service
    //    studentService.save(studentEntity);

    return "redirect:/student/list";
  }

  @GetMapping("/showFormForUpdate")
  public String showFormForUpdate(@RequestParam("studentId") int id, Model theModel) {

    // get the customer from our service
    //    StudentDTO student = studentService.findById(id);

    // set customer as a model attribute to pre-populate the form
    //    theModel.addAttribute("student", student);

    // send over to our form
    return "student-form";
  }

  @GetMapping("/delete")
  public String deleteStudent(@RequestParam("studentId") int studentId) {

    studentService.deleteById(studentId);

    return "redirect:/student/list";
  }
}
