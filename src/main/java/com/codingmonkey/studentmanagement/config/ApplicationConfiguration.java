package com.codingmonkey.studentmanagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "app-config")
public class ApplicationConfiguration {
  private int maxClassAllowed = 10;

}
