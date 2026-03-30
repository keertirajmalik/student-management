package com.codingmonkey.studentmanagement.repositories;

import com.codingmonkey.studentmanagement.domain.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

  List<SubjectEntity> findSubjectsByClassNumber(int classNumber);
}