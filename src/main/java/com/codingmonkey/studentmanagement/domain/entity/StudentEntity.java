package com.codingmonkey.studentmanagement.domain.entity;

import com.codingmonkey.studentmanagement.constant.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_details")
public class StudentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
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
  @Column(name = "gender")
  private Gender gender;

  //TODO: create a field for subjects object directly with @ManyToMany connection and also explore @JoinColumn annotation here
  // but before that redesign the database schema with system design
}
