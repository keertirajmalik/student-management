package com.codingmonkey.studentmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_details")
public class StudentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int student_id;

  @Column(name = "first_name")
  private String first_name;

  @Column(name = "last_name")
  private String last_name;

  @Column(name = "roll_number")
  private int roll_number;

  @Column(name = "mobile_number")
  private Long mobile_number;

  @Column(name = "email")
  private String email;

  @Column(name = "class_number")
  private int class_number;

  public int getStudent_id() {
    return student_id;
  }

  public void setStudent_id(final int student_id) {
    this.student_id = student_id;
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
    return class_number;
  }

  public void setClassNumber(final int classNumber) {
    this.class_number = classNumber;
  }

  @Override
  public String toString() {
    return "StudentEntity{" + "id=" + student_id + ", first_name='" + first_name + '\'' + ", last_name='" + last_name
        + '\'' + ", roll_number=" + roll_number + ", mobile_number=" + mobile_number + ", email='" + email + '\''
        + ", class_number=" + class_number + '}';
  }
}
