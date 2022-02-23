package com.codingmonkey.studentmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_details")
public class Student {
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

  @Column(name = "class")
  private int classNumber;

  public int getStudentId() {
    return student_id;
  }

  public void setStudentId(int id) {
    this.student_id = id;
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
    return classNumber;
  }

  public void setClassNumber(final int classNumber) {
    this.classNumber = classNumber;
  }

  //  public List<Subject> getSubjects() {
  //    return subjects;
  //  }
  //
  //  public void setSubjects(final List<Subject> subjects) {
  //    this.subjects = subjects;
  //  }

  @Override
  public String toString() {
    return "Student{" + "id=" + student_id + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\''
        + ", roll_number=" + roll_number + ", mobile_number=" + mobile_number + ", email='" + email + '\''
        + ", classNumber=" + classNumber + '}';
  }
}
