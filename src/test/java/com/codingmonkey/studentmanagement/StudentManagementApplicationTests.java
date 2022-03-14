package com.codingmonkey.studentmanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentManagementApplicationTests {
  private StudentManagementApplication studentManagementApplication;

  @BeforeEach
  public void setUp() {
    studentManagementApplication = new StudentManagementApplication();
  }

  @Test
  void verifyContextLoad() {
    StudentManagementApplication.main();
  }

}
