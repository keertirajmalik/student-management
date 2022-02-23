package com.codingmonkey.studentmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject_details")
public class Subject {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer subject_id;

  @Column(name = "subject")
  private String subject;

  @Column(name = "class")
  private int class_number;

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

