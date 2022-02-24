package com.codingmonkey.studentmanagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teacher_details")
public class TeacherEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int teacherId;

  @Column(name = "first_name")
  private String first_name;

  @Column(name = "last_name")
  private String last_name;

  @Column(name = "mobile_number")
  private Long mobile_number;

  @Column(name = "email")
  private String email;

  @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.DETACH})
  private List<SubjectEntity> subjects;

  public int getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(final int teacherId) {
    this.teacherId = teacherId;
  }

  public List<SubjectEntity> getSubjects() {
    return subjects;
  }

  public void setSubjects(final List<SubjectEntity> subjects) {
    this.subjects = subjects;
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

  @Override
  public String toString() {
    return "TeacherEntity{" + "teacherId=" + teacherId + ", first_name='" + first_name + '\'' + ", last_name='"
        + last_name + '\'' + '}';
  }
}
