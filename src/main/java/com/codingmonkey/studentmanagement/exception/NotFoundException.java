package com.codingmonkey.studentmanagement.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException() {
  }

  public NotFoundException(final String message) {
    super(message);
  }

  public NotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(final Throwable cause) {
    super(cause);
  }

  public NotFoundException(final String message,
                           final Throwable cause,
                           final boolean enableSuppression,
                           final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
