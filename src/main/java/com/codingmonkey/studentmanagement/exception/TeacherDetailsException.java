package com.codingmonkey.studentmanagement.exception;

import org.springframework.http.HttpStatus;

public class TeacherDetailsException extends RuntimeException {
  private final HttpStatus statusCode;

  public TeacherDetailsException(final String message, final HttpStatus statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }
}
