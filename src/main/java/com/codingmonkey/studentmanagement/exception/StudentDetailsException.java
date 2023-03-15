package com.codingmonkey.studentmanagement.exception;

import org.springframework.http.HttpStatus;

public class StudentDetailsException extends RuntimeException {

  public StudentDetailsException() {
  }

  public StudentDetailsException(final String message, final HttpStatus statusCode) {
    super(message);
  }
}
