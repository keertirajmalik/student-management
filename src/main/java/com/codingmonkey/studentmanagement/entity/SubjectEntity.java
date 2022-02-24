package com.codingmonkey.studentmanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subject_details")
public class SubjectEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer subject_id;

  @Column(name = "subject")
  private String subject;

  @Column(name = "class_number")
  private int class_number;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinColumn(name = "teacher_id")
  private TeacherEntity teacher;

  public TeacherEntity getTeacher() {
    return teacher;
  }

  public void setTeacher(TeacherEntity teacher) {
    this.teacher = teacher;
  }

  public Integer getSubjectId() {
    return subject_id;
  }

  public void setSubjectId(final Integer id) {
    this.subject_id = id;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(final String subject) {
    this.subject = subject;
  }

  public int getClass_number() {
    return class_number;
  }

  public void setClass_number(final int class_number) {
    this.class_number = class_number;
  }

  @Override
  public String toString() {
    return "SubjectEntity{" + "subject_id=" + subject_id + ", subject='" + subject + '\'' + ", class_number="
        + class_number + '}';
  }
}
