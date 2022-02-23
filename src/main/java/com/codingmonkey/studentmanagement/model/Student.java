package com.codingmonkey.studentmanagement.model;

public class Student {

  private int id;

  private String first_name;

  private String last_name;

  private String roll_number;

  private Long mobile_number;

  private String email;

  private int classNumber;

  public Student() {

  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

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

  public String getRoll_number() {
    return roll_number;
  }

  public void setRoll_number(final String roll_number) {
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
}





