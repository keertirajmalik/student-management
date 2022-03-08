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

@Entity
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
  private List<SubjectEntity> subjects;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  public Gender getGender() {
    return gender;
  }

  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  public int getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(final int teacherId) {
    this.teacherId = teacherId;
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

  public List<SubjectEntity> getSubjects() {
    return subjects;
  }

  public void setSubjects(final List<SubjectEntity> subjects) {
    this.subjects = subjects;
  }

  @Override
  public String toString() {
    return "TeacherEntity{" + "teacherId=" + teacherId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
        + '\'' + ", mobileNumber=" + mobileNumber + ", email='" + email + '\'' + ", subjects=" + subjects + ", gender="
        + gender + '}';
  }
}
