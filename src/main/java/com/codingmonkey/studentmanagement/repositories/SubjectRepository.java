package com.codingmonkey.studentmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codingmonkey.studentmanagement.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

  @Query("select s from Subject s where s.class_number = ?1")
  Optional<List<Subject>> findSubjectsByClass_number(int class_number);
}