package com.codingmonkey.studentmanagement.repositories;

import com.codingmonkey.studentmanagement.domain.entity.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

  List<TeacherEntity> findByFirstNameAndLastName(String firstName, String lastName);

  Page<TeacherEntity> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

  List<TeacherEntity> findByFirstName(String firstName);

  Page<TeacherEntity> findByFirstName(String firstName, Pageable pageable);

  List<TeacherEntity> findByLastName(String lastName);

  Page<TeacherEntity> findByLastName(String lastName, Pageable pageable);

  TeacherEntity findByFirstNameAndLastNameAndTeacherId(String firstName, String lastName, int teacherId);
}