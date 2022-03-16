package com.codingmonkey.studentmanagement.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.codingmonkey.studentmanagement.constant.Gender;
import com.codingmonkey.studentmanagement.entity.SubjectEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
  private String firstName;
  private String lastName;
  private Long mobileNumber;
  private String email;
  private List<SubjectDTO> subjects;
  private Gender Gender;

  public void setSubjects(final List<SubjectEntity> subjects) {
    this.subjects = subjects.stream().map(this::convertEntityToDto).collect(Collectors.toList());
  }

  private SubjectDTO convertEntityToDto(SubjectEntity subjectEntity) {
    SubjectDTO subjectDTO = new SubjectDTO();
    subjectDTO.setClassNumber(subjectEntity.getClassNumber());
    subjectDTO.setSubject(subjectEntity.getSubject());

    return subjectDTO;
  }
}