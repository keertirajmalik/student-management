package com.codingmonkey.studentmanagement.exception;

import org.springframework.http.HttpStatus;

public class TeacherDetailsException extends RuntimeException {

  public TeacherDetailsException(final String message, final HttpStatus statusCode) {
    super(message);
  }
}
