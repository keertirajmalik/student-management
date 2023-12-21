package com.codingmonkey.studentmanagement.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app-config")
public class ApplicationConfiguration {
  private int maxClassAllowed = 10;

  public int getMaxClassAllowed() {
    return maxClassAllowed;
  }

  public void setMaxClassAllowed(final int maxClassAllowed) {
    this.maxClassAllowed = maxClassAllowed;
  }
}
