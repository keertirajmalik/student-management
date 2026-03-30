package com.codingmonkey.studentmanagement.repositories;

import com.codingmonkey.studentmanagement.domain.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

  List<StudentEntity> findByClassNumber(final int classNumber);

  Page<StudentEntity> findByFirstNameAndLastName(String firstName, String lastName,
                                                 Pageable pageable);

  StudentEntity findByFirstNameAndLastNameAndStudentId(String firstName, String lastName, int studentId);

  Page<StudentEntity> findByFirstName(String firstName, Pageable pageable);

  Page<StudentEntity> findByLastName(String lastName, Pageable pageable);
}