package com.codingmonkey.studentmanagement.dto;

import java.util.List;

import com.codingmonkey.studentmanagement.constant.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
  private int studentId;
  private String firstName;
  private String lastName;
  private int rollNumber;
  private Long mobileNumber;
  private String email;
  private int classNumber;
  private List<String> subjects;
  private Gender Gender;
}
