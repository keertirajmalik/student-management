package com.codingmonkey.studentmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.codingmonkey.studentmanagement.configurations.ApplicationConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@OpenAPIDefinition
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

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().components(new Components())
        .info(new Info().title("Student Management API")
            .version("1.0")
            .license(new License().name("Apache 2.0")
                .url("https://github.com/keertirajmalik/student-management/blob/develop/LICENSE"))
            .contact(new Contact().email("keertirajmalik@icloud.com"))
            .description("This is a swagger documentation of Student Management API using Spring Boot and Swagger"));
  }
}
