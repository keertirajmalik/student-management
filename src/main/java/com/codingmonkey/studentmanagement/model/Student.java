package com.codingmonkey.studentmanagement.model;

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
public class Student {

  private int id;

  private String firstName;

  private String lastName;

  private String rollNumber;

  private Long mobileNumber;

  private String email;

  private int classNumber;
}





