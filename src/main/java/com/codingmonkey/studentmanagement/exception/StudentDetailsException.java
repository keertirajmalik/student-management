package com.codingmonkey.studentmanagement.exception;

import org.springframework.http.HttpStatus;

public class StudentDetailsException extends RuntimeException {
  private HttpStatus statusCode = HttpStatus.BAD_REQUEST;

  public StudentDetailsException() {
  }

  public StudentDetailsException(final String message, final HttpStatus statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public StudentDetailsException(final String message, final Throwable cause, final HttpStatus statusCode) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  public StudentDetailsException(final Throwable cause, final HttpStatus statusCode) {
    super(cause);
    this.statusCode = statusCode;
  }

  public StudentDetailsException(final String message,
                                 final Throwable cause,
                                 final boolean enableSuppression,
                                 final boolean writableStackTrace,
                                 final HttpStatus statusCode) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.statusCode = statusCode;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }
}
