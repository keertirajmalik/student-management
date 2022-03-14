package com.codingmonkey.studentmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.codingmonkey.studentmanagement.constant.Gender;

@Entity
@Table(name = "student_details")
public class StudentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int studentId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "roll_number")
  private int rollNumber;

  @Column(name = "mobile_number")
  private Long mobileNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "class_number")
  private int classNumber;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  public StudentEntity(final String firstName,
                       final String lastName,
                       final int rollNumber,
                       final Long mobileNumber,
                       final String email,
                       final int classNumber,
                       final Gender gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.rollNumber = rollNumber;
    this.mobileNumber = mobileNumber;
    this.email = email;
    this.classNumber = classNumber;
    this.gender = gender;
  }

  public StudentEntity() {
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(final Gender gender) {
    this.gender = gender;
  }

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

  @Override
  public String toString() {
    return "StudentEntity{" + "studentId=" + studentId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
        + '\'' + ", rollNumber=" + rollNumber + ", mobileNumber=" + mobileNumber + ", email='" + email + '\''
        + ", classNumber=" + classNumber + ", gender=" + gender + '}';
  }
}
