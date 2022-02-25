package com.codingmonkey.studentmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingmonkey.studentmanagement.entity.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {
}