package com.codingmonkey.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingmonkey.studentmanagement.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

  List<SubjectEntity> findSubjectsByClassNumber(int classNumber);
}