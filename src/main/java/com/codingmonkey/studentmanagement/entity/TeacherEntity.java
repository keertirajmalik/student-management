package com.codingmonkey.studentmanagement.entity;

import com.codingmonkey.studentmanagement.constant.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "teacher_details")
public class TeacherEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int teacherId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "mobile_number")
  private Long mobileNumber;

  @Column(name = "email")
  private String email;

  @Enumerated(EnumType.STRING)
  private Gender gender;
}
