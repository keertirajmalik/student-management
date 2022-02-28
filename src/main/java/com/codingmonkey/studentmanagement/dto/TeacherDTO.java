package com.codingmonkey.studentmanagement.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.codingmonkey.studentmanagement.entity.SubjectEntity;

public class TeacherDTO {
  private String firstName;
  private String lastName;
  private Long mobileNumber;
  private String email;
  private List<SubjectDTO> subjects;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public Long getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(final Long mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public List<SubjectDTO> getSubjects() {
    return subjects;
  }

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