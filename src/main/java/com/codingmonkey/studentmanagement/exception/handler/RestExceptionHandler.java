package com.codingmonkey.studentmanagement.exception.handler;

import java.time.Instant;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codingmonkey.studentmanagement.exception.ErrorResponse;
import com.codingmonkey.studentmanagement.exception.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ErrorResponse> handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception) {
    LOGGER.error("Unsupported media type exception :: Status Code: 406 , Error Message : {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage(), Instant.now().toString()));
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception) {
    LOGGER.error("Unsupported media type exception :: Status Code: 415 , Error Message :{}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body(new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), exception.getMessage(),
            Instant.now().toString()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    LOGGER.error("Method argument not valid exception :: {}", exception.getMessage());
    String errorMessage = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList())
        .toString();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage, Instant.now().toString()));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      HttpRequestMethodNotSupportedException exception) {
    LOGGER.error("Method argument not valid exception :: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(
            new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), exception.getMessage(), Instant.now().toString()));
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
    LOGGER.error("Error message :: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage(),
            Instant.now().toString()));
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ErrorResponse> handleException(NotFoundException exception) {
    LOGGER.error("Error message :: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), Instant.now().toString()));
  }
}
