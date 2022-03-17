package com.codingmonkey.studentmanagement.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;
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
  private ApplicationConfiguration applicationConfiguration;

  private int studentId;

  @NotNull(message = "Student First Name cannot be null")
  @NotBlank(message = "Student First Name cannot be empty")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "First name should contain only [a-z, A-Z]")
  private String firstName;

  @NotNull(message = "Student First Name cannot be null")
  @NotBlank(message = "Student First Name cannot be empty")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name should contain only [a-z, A-Z]")
  private String lastName;
  private int rollNumber;

  @NotNull(message = "Student mobile number cannot be null")
  @NotBlank(message = "Student mobile number cannot be empty")
  private Long mobileNumber;

  @NotNull(message = "Student email cannot be null")
  @NotBlank(message = "Student email cannot be empty")
  @Email(message = "Provide valid email address")
  private String email;

  @Min(value = 1, message = "Student class number cannot be less than 1")
  private int classNumber;
  private List<String> subjects;

  @NotNull(message = "Student gender cannot be null")
  @NotBlank(message = "Student gender cannot be empty")
  private Gender Gender;
}
