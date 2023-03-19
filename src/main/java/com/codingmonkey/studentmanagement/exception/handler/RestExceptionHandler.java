package com.codingmonkey.studentmanagement.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.time.Instant;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codingmonkey.studentmanagement.exception.ErrorResponse;
import com.codingmonkey.studentmanagement.exception.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<ErrorResponse> handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception) {
    LOGGER.error("Unsupported media type exception :: Status Code: 406 , Error Message : {}", exception.getMessage());
    return ResponseEntity.status(NOT_ACCEPTABLE)
        .body(new ErrorResponse(NOT_ACCEPTABLE.value(), exception.getMessage(), Instant.now().toString()));
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception) {
    LOGGER.error("Unsupported media type exception :: Status Code: 415 , Error Message :{}", exception.getMessage());
    return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE)
        .body(new ErrorResponse(UNSUPPORTED_MEDIA_TYPE.value(), exception.getMessage(), Instant.now().toString()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    LOGGER.error("Method argument not valid exception :: {}", exception.getMessage());

    String errorMessage = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList())
        .toString();

    return ResponseEntity.status(BAD_REQUEST)
        .body(new ErrorResponse(BAD_REQUEST.value(), errorMessage, Instant.now().toString()));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      HttpRequestMethodNotSupportedException exception) {
    LOGGER.error("Method argument not valid exception :: {}", exception.getMessage());
    return ResponseEntity.status(BAD_REQUEST)
        .body(new ErrorResponse(BAD_REQUEST.value(), exception.getMessage(), Instant.now().toString()));
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
    LOGGER.error("Error message :: {}", exception.getMessage());
    return ResponseEntity.status(BAD_REQUEST)
        .body(new ErrorResponse(BAD_REQUEST.value(), exception.getLocalizedMessage(), Instant.now().toString()));
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(NotFoundException exception) {
    LOGGER.error("Error message :: {}", exception.getMessage());
    return ResponseEntity.status(NOT_FOUND)
        .body(new ErrorResponse(NOT_FOUND.value(), exception.getMessage(), Instant.now().toString()));
  }
}
