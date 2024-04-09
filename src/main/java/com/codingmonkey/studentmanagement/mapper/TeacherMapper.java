package com.codingmonkey.studentmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codingmonkey.studentmanagement.dto.TeacherRequestDTO;
import com.codingmonkey.studentmanagement.dto.TeacherResponseDTO;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

  @Mapping(source = "firstName", target = "firstName")
  @Mapping(source = "lastName", target = "lastName")
  @Mapping(source = "mobileNumber", target = "mobileNumber")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "gender", target = "gender")
  TeacherEntity teacherDtoToEntity(TeacherRequestDTO teacherDTO);

  @Mapping(source = "firstName", target = "firstName")
  @Mapping(source = "lastName", target = "lastName")
  @Mapping(source = "mobileNumber", target = "mobileNumber")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "gender", target = "gender")
  TeacherResponseDTO teacherEntityToDto(TeacherEntity teacherEntity);

  @Mapping(source = "teacherDTO.firstName", target = "firstName")
  @Mapping(source = "teacherDTO.lastName", target = "lastName")
  @Mapping(source = "teacherDTO.mobileNumber", target = "mobileNumber")
  @Mapping(source = "teacherDTO.email", target = "email")
  @Mapping(source = "teacherDTO.gender", target = "gender")
  TeacherResponseDTO updateTeacherEntityFromDto(TeacherRequestDTO teacherDTO, TeacherEntity teacherEntity);
}
