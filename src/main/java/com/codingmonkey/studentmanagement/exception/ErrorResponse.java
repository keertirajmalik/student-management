package com.codingmonkey.studentmanagement.exception;

public class ErrorResponse {

  private int code;
  private String message;
  private String timeStamp;

  public ErrorResponse() {
  }

  public ErrorResponse(final int code, final String message, final String timeStamp) {
    this.code = code;
    this.message = message;
    this.timeStamp = timeStamp;
  }

  public int getCode() {
    return code;
  }

  public void setCode(final int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(final String timeStamp) {
    this.timeStamp = timeStamp;
  }
}
