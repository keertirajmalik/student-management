package com.codingmonkey.studentmanagement.repositories;

import com.codingmonkey.studentmanagement.domain.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

  List<TeacherEntity> findByFirstNameAndLastName(String firstName, String lastName);

  List<TeacherEntity> findByFirstName(String firstName);

  List<TeacherEntity> findByLastName(String lastName);

  TeacherEntity findByFirstNameAndLastNameAndTeacherId(String firstName, String lastName, int teacherId);
}