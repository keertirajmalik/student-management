package com.codingmonkey.studentmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingmonkey.studentmanagement.dto.TeacherDTO;
import com.codingmonkey.studentmanagement.entity.TeacherEntity;
import com.codingmonkey.studentmanagement.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepository;

  public TeacherServiceImpl(final TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  @Override
  public List<TeacherEntity> findAll() {
    return teacherRepository.findAll();
  }

  @Override
  public TeacherDTO findById(final int teacherId) {
    Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);

    if (teacher.isPresent()) {
      return convertEntityToDto(teacher.get());
    }
    throw new RuntimeException("Did not find teacher with id: " + teacherId);
  }

  @Override
  public void save(final TeacherEntity teacherEntity) {
    teacherRepository.save(teacherEntity);
  }

  @Override
  public void deleteById(final int teacherId) {
    teacherRepository.deleteById(teacherId);
  }

  private TeacherDTO convertEntityToDto(TeacherEntity teacherEntity) {
    TeacherDTO teacherDTO = new TeacherDTO();
    teacherDTO.setFirst_name(teacherEntity.getFirst_name());
    teacherDTO.setLast_name(teacherEntity.getLast_name());
    teacherDTO.setEmail(teacherEntity.getEmail());
    teacherDTO.setMobile_number(teacherEntity.getMobile_number());
    teacherDTO.setSubjects(teacherEntity.getSubjects());
    return teacherDTO;
  }
}
