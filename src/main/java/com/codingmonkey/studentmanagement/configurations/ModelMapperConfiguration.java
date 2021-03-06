package com.codingmonkey.studentmanagement.configurations;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;

@Configuration
public class ModelMapperConfiguration {

  @Bean
  public ModelMapper modelMapper() {

    final ModelMapper modelMapper = new ModelMapper();

    PropertyMap<StudentDTO, StudentEntity> studentRequestDtoEntityMap = new PropertyMap<StudentDTO, StudentEntity>() {
      protected void configure() {
        map().setFirstName(source.getFirstName());
        map().setLastName(source.getLastName());
        map().setEmail(source.getEmail());
        map().setClassNumber(source.getClassNumber());
        map().setRollNumber(source.getRollNumber());
        map().setMobileNumber(source.getMobileNumber());
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
    modelMapper.addMappings(teacherRequestDtoEntityMap);
    return modelMapper;
  }
}
