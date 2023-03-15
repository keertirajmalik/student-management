package com.codingmonkey.studentmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingmonkey.studentmanagement.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

  Optional<StudentEntity> findByStudentId(Integer integer);

  List<StudentEntity> findByClassNumber(final int classNumber);

  List<StudentEntity> findByFirstNameAndLastName(String firstName, String lastName);

  StudentEntity findByFirstNameAndLastNameAndStudentId(String firstName, String lastName, int studentId);
}