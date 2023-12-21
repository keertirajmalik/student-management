package com.codingmonkey.studentmanagement.exception;

import org.springframework.http.HttpStatus;

public class StudentDetailsException extends RuntimeException {
  private final HttpStatus statusCode;

  public StudentDetailsException(final String message, final HttpStatus statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }
}
