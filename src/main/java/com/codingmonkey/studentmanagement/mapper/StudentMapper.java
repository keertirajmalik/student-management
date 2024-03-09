package com.codingmonkey.studentmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  @Mapping(source = "firstName", target = "firstName")
  @Mapping(source = "lastName", target = "lastName")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "classNumber", target = "classNumber")
  @Mapping(source = "mobileNumber", target = "mobileNumber")
  StudentEntity studentDtoToEntity(StudentRequestDTO student);

  @Mapping(source = "studentId", target = "studentId")
  @Mapping(source = "firstName", target = "firstName")
  @Mapping(source = "lastName", target = "lastName")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "classNumber", target = "classNumber")
  @Mapping(source = "mobileNumber", target = "mobileNumber")
  @Mapping(source = "rollNumber", target = "rollNumber")
  @Mapping(source = "gender", target = "gender")
  StudentResponseDTO studentEntityToDto(StudentEntity studentEntity);

  @Mapping(source = "studentDTO.firstName", target = "firstName")
  @Mapping(source = "studentDTO.lastName", target = "lastName")
  @Mapping(source = "studentDTO.email", target = "email")
  @Mapping(source = "studentDTO.classNumber", target = "classNumber")
  @Mapping(source = "studentDTO.mobileNumber", target = "mobileNumber")
  @Mapping(source = "studentDTO.gender", target = "gender")
  StudentResponseDTO updateStudentEntityToDto(StudentRequestDTO studentDTO, StudentEntity studentEntity);
}