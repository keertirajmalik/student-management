package com.codingmonkey.studentmanagement.configurations;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codingmonkey.studentmanagement.dto.StudentRequestDTO;
import com.codingmonkey.studentmanagement.dto.StudentResponseDTO;
import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;

@Configuration
public class ModelMapperConfiguration {

  @Bean
  public ModelMapper modelMapper() {

    final ModelMapper modelMapper = new ModelMapper();

    PropertyMap<StudentRequestDTO, StudentEntity> studentRequestDtoEntityMap = new PropertyMap<StudentRequestDTO, StudentEntity>() {
      protected void configure() {
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setEmail(source.getEmail());
        map().setClassNumber(source.getClassNumber());
        map().setMobileNumber(source.getMobileNumber());
      }
    };

    PropertyMap<StudentResponseDTO, StudentEntity> studentResponseDtoEntityMap = new PropertyMap<StudentResponseDTO, StudentEntity>() {
      protected void configure() {
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setEmail(source.getEmail());
        map().setClassNumber(source.getClassNumber());
        map().setMobileNumber(source.getMobileNumber());
        map().setRollNumber(source.getRollNumber());
      }
    };

    PropertyMap<TeacherDTO, TeacherEntity> teacherRequestDtoEntityMap = new PropertyMap<TeacherDTO, TeacherEntity>() {
      protected void configure() {
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setEmail(source.getEmail());
        map().setMobileNumber(source.getMobileNumber());
      }
    };

    modelMapper.addMappings(studentRequestDtoEntityMap);
    modelMapper.addMappings(studentResponseDtoEntityMap);
    modelMapper.addMappings(teacherRequestDtoEntityMap);
    return modelMapper;
  }
}
