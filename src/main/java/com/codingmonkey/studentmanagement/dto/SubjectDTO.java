package com.codingmonkey.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SubjectDTO {

  private String subject;
  private int classNumber;

}
