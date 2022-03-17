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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.codingmonkey.studentmanagement.exception.ErrorResponse;
import com.codingmonkey.studentmanagement.exception.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(NotFoundException exception) {
    LOGGER.error("Error message :: {}", exception.getMessage());
    return ResponseEntity.status(NOT_FOUND)
        .body(new ErrorResponse(NOT_FOUND.value(), exception.getMessage(), Instant.now().toString()));
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException ex,
                                                                    final HttpHeaders headers,
                                                                    final HttpStatus status,
                                                                    final WebRequest request) {
    final String errorMessage = ex.getMessage();
    LOGGER.error("Unsupported media type exception :: Status Code:" + status + ", Error Message : " + errorMessage);
    return ResponseEntity.status(status)
        .body(new ErrorResponse(NOT_ACCEPTABLE.value(), errorMessage, Instant.now().toString()));
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception) {
    LOGGER.error("Unsupported media type exception :: Error Message :" + exception.getMessage());

    return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE)
        .body(new ErrorResponse(UNSUPPORTED_MEDIA_TYPE.value(), "exception.getMessage()", Instant.now().toString()));
  }

  @ExceptionHandler
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

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
    LOGGER.error("Error message :: {}", exception.getMessage());
    return ResponseEntity.status(BAD_REQUEST)
        .body(new ErrorResponse(BAD_REQUEST.value(), exception.getLocalizedMessage(), Instant.now().toString()));
  }
}
