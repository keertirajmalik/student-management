package com.codingmonkey.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingmonkey.studentmanagement.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

  List<StudentEntity> findByClassNumber(final int classNumber);

  List<StudentEntity> findByFirstNameAndLastName(String firstName, String lastName);

  StudentEntity findByFirstNameAndLastNameAndStudentId(String firstName, String lastName, int studentId);

  List<StudentEntity> findByFirstName(String firstName);

  List<StudentEntity> findByLastName(String lastName);
}