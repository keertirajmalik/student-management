package com.codingmonkey.studentmanagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

  @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.DETACH}, fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<SubjectEntity> subjects;

  @Enumerated(EnumType.STRING)
  private Gender gender;
}
