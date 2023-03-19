package com.codingmonkey.studentmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private int responseCode;
  private String message;
  private String timeStamp;
}
