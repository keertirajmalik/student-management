package com.codingmonkey.studentmanagement.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject_details")
public class SubjectEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer subjectId;

  @Column(name = "subject")
  private String subject;

  @Column(name = "class_number")
  private int classNumber;
}