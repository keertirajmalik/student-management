package com.codingmonkey.studentmanagement.dto;

import java.util.List;

import com.codingmonkey.studentmanagement.constant.Gender;

import jakarta.validation.constraints.Email;
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
public class TeacherResponseDTO {
  @NotNull(message = "Teacher Id cannot be null")
  private Integer teacherId;

  @NotNull(message = "First name cannot be null")
  @NotBlank(message = "First name cannot be empty")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "First name should contain only [a-z, A-Z]")
  private String firstName;

  @NotNull(message = "Last name cannot be null")
  @NotBlank(message = "Last name cannot be empty")
  @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name should contain only [a-z, A-Z]")
  private String lastName;

  @NotNull(message = "Mobile number cannot be null")
  private Long mobileNumber;

  @NotNull(message = "Email cannot be null")
  @NotBlank(message = "Email cannot be empty")
  @Email
  private String email;

  @NotNull(message = "Gender cannot be null")
  private Gender gender;

  private List<String> subjects;
}