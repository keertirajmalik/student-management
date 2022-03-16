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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
}
