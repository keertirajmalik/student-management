package com.codingmonkey.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingmonkey.studentmanagement.entity.ApplicationUserEntity;

public interface ApplicationUserEntityRepository extends JpaRepository<ApplicationUserEntity, Long> {

  Optional<ApplicationUserEntity> findByUsername(String username);
}