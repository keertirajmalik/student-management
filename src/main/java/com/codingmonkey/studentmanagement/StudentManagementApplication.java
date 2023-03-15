package com.codingmonkey.studentmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class StudentManagementApplication extends SpringBootServletInitializer {

  private static final Logger log = LoggerFactory.getLogger(StudentManagementApplication.class);

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(StudentManagementApplication.class);
  }

  public static void main(String... args) {
    log.info("Started Application with configurations :: {}", args);
    SpringApplication.run(StudentManagementApplication.class, args);
  }

}
