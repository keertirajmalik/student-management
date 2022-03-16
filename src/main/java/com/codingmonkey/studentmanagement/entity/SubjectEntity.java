package com.codingmonkey.studentmanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "subject_details")
public class SubjectEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer subject_id;

  @Column(name = "subject")
  private String subject;

  @Column(name = "class_number")
  private int classNumber;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.DETACH}, fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id")
  @JsonIgnore
  @ToString.Exclude
  private TeacherEntity teacher;

  public SubjectEntity(final String subject, final int classNumber) {
    this.subject = subject;
    this.classNumber = classNumber;
  }
}