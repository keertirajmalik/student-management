package com.codingmonkey.studentmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ApplicationUsers")
public class ApplicationUserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;

  @Column(columnDefinition = "VARCHAR(10)")
  private String username;

  @Column(columnDefinition = "VARCHAR(130)")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public Long getId() {
    return userId;
  }

  public void setId(Long userId) {
    this.userId = userId;
  }
}
