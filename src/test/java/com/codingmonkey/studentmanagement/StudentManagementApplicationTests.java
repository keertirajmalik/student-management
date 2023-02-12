package com.codingmonkey.studentmanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;

@SpringBootTest
@EnableConfigurationProperties(ApplicationConfiguration.class)
class StudentManagementApplicationTests {

  @BeforeEach
  public void setUp() {
    new StudentManagementApplication();
  }

  @Test
  void verifyContextLoad() {
    StudentManagementApplication.main();
  }

}
