package com.codingmonkey.studentmanagement.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.codingmonkey.studentmanagement.entity.SubjectEntity;

public class TeacherDTO {
  private String first_name;
  private String last_name;
  private Long mobile_number;
  private String email;
  private List<SubjectDTO> subjects;

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(final String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(final String last_name) {
    this.last_name = last_name;
  }

  public Long getMobile_number() {
    return mobile_number;
  }

  public void setMobile_number(final Long mobile_number) {
    this.mobile_number = mobile_number;
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
    subjectDTO.setClass_number(subjectEntity.getClass_number());
    subjectDTO.setSubject(subjectEntity.getSubject());

    return subjectDTO;
  }

}