package com.codingmonkey.studentmanagement.dto;

import java.io.Serializable;
import java.util.Objects;

public class ApplicationUserEntityDto implements Serializable {
  private final Long userId;
  private final String username;
  private final String password;

  public ApplicationUserEntityDto(Long userId, String username, String password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }

  public Long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationUserEntityDto entity = (ApplicationUserEntityDto) o;
    return Objects.equals(this.userId, entity.userId) && Objects.equals(this.username, entity.username)
        && Objects.equals(this.password, entity.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, username, password);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + "userId = " + userId + ", " + "username = " + username + ", "
        + "password = " + password + ")";
  }
}
