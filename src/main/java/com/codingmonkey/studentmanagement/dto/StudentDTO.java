package com.codingmonkey.studentmanagement.dto;

import java.util.List;

public class StudentDTO {
  private int studentId;
  private String firstName;
  private String lastName;
  private int rollNumber;
  private Long mobileNumber;
  private String email;
  private int classNumber;
  private List<String> subjects;

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(final int studentId) {
    this.studentId = studentId;
  }

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

  public int getRollNumber() {
    return rollNumber;
  }

  public void setRollNumber(final int rollNumber) {
    this.rollNumber = rollNumber;
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

  @Override
  public String toString() {
    return "StudentDTO{" + "studentId=" + studentId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
        + '\'' + ", rollNumber=" + rollNumber + ", mobileNumber=" + mobileNumber + ", email='" + email + '\''
        + ", classNumber=" + classNumber + ", subjects=" + subjects + '}';
  }
}
