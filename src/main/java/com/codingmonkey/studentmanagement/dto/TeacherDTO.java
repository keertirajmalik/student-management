package com.codingmonkey.studentmanagement.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;

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
public class TeacherDTO {

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
  private List<SubjectDTO> subjects;

  @NotNull(message = "Gender cannot be null")
  private Gender Gender;

  public void setSubjects(final List<SubjectEntity> subjects) {
    this.subjects = subjects.stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  private SubjectDTO convertEntityToDto(SubjectEntity subjectEntity) {
    SubjectDTO subjectDTO = new SubjectDTO();
    subjectDTO.setClassNumber(subjectEntity.getClassNumber());
    subjectDTO.setSubject(subjectEntity.getSubject());

    return subjectDTO;
  }
}