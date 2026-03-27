package com.codingmonkey.studentmanagement.repositories;

import com.codingmonkey.studentmanagement.domain.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

  List<StudentEntity> findByClassNumber(final int classNumber);

  List<StudentEntity> findByFirstNameAndLastName(String firstName, String lastName);

  StudentEntity findByFirstNameAndLastNameAndStudentId(String firstName, String lastName, int studentId);

  List<StudentEntity> findByFirstName(String firstName);

  List<StudentEntity> findByLastName(String lastName);
}