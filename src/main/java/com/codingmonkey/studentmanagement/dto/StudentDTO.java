package com.codingmonkey.studentmanagement.dto;

import java.util.List;

public class StudentDTO {
  private String first_name;
  private String last_name;
  private int roll_number;
  private Long mobile_number;
  private String email;
  private int classNumber;
  private List<String> subjects;

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

  public int getRoll_number() {
    return roll_number;
  }

  public void setRoll_number(final int roll_number) {
    this.roll_number = roll_number;
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

  public int getClassNumber() {
    return classNumber;
  }

  public void setClassNumber(final int classNumber) {
    this.classNumber = classNumber;
  }

  public List<String> getSubjects() {
    return subjects;
  }

  public void setSubjects(final List<String> subjects) {
    this.subjects = subjects;
  }
}
