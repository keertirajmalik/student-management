package com.codingmonkey.studentmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingmonkey.studentmanagement.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}