package com.codingmonkey.studentmanagement.dto;

import java.util.List;

import com.codingmonkey.studentmanagement.constant.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class StudentResponseDTO {

  @NotNull(message = "Student Id cannot be null")
  private Integer studentId;

  @NotNull(message = "First Name cannot be null")
  @NotBlank(message = "First Name cannot be empty")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "First name should contain only [a-z, A-Z]")
  private String firstName;

  @NotNull(message = "First Name cannot be null")
  @NotBlank(message = "First Name cannot be empty")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name should contain only [a-z, A-Z]")
  private String lastName;

  @Min(value = 1, message = "Roll number cannot be less than 1")
  private int rollNumber;

  @NotNull(message = "Mobile number cannot be null")
  private Long mobileNumber;

  @NotNull(message = "Email cannot be null")
  @NotBlank(message = "Email cannot be empty")
  @Email
  private String email;

  @Min(value = 1, message = "Class number cannot be less than 1")
  private int classNumber;

  private List<String> subjects;

  @NotNull(message = "Gender cannot be null")
  private Gender gender;
}
