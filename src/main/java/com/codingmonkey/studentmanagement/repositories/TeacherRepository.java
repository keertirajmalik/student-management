package com.codingmonkey.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingmonkey.studentmanagement.entity.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

  List<TeacherEntity> findByFirstNameAndLastName(String firstName, String lastName);
}