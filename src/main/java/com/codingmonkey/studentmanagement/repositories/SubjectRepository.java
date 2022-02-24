package com.codingmonkey.studentmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codingmonkey.studentmanagement.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

  @Query("select s from SubjectEntity s where s.class_number = ?1")
  Optional<List<SubjectEntity>> findSubjectsByClass_number(int class_number);
}