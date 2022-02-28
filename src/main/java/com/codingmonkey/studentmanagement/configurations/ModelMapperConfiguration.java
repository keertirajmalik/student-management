/*
 * *
 * ***************************************************************************
 * *
 * *   This material is the confidential, proprietary, unpublished property
 * *   of Fair Isaac Corporation.  Receipt or possession of this material
 * *   does not convey rights to divulge, reproduce, use, or allow others
 * *   to use it without the specific written authorization of Fair Isaac
 * *   Corporation and use must conform strictly to the license agreement.
 * *
 * *   Copyright (c) 2021 Fair Isaac Corporation.  All rights reserved.
 * *
 * ***************************************************************************
 *
 */
package com.codingmonkey.studentmanagement.configurations;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codingmonkey.studentmanagement.dto.StudentDTO;
import com.codingmonkey.studentmanagement.entity.StudentEntity;

@Configuration
public class ModelMapperConfiguration {

  @Bean
  public ModelMapper modelMapper() {

    final ModelMapper modelMapper = new ModelMapper();

    PropertyMap<StudentDTO, StudentEntity> barRequestDtoEntityMap = new PropertyMap<StudentDTO, StudentEntity>() {
      protected void configure() {
        map().setFirst_name(source.getFirst_name());
        map().setLast_name(source.getLast_name());
        map().setEmail(source.getEmail());
        map().setClass_number(source.getClass_number());
        map().setRoll_number(source.getRoll_number());
        map().setMobile_number(source.getMobile_number());
      }
    };

    modelMapper.addMappings(barRequestDtoEntityMap);
    return modelMapper;
  }
}
