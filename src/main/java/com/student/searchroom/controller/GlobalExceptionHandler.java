package com.student.searchroom.controller;

import com.student.searchroom.exception.BannedUserException;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.model.response.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(SearchRoomException.class)
    public ResponseEntity<ErrorResponse> handleEnglishException(SearchRoomException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e));
    }

    @ExceptionHandler(BannedUserException.class)
    public ResponseEntity<ErrorResponse> handleBannedUserException(BannedUserException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(ErrorCode.BANNED_PERFORM_ACTION.httpStatus()).body(new ErrorResponse(ErrorCode.BANNED_PERFORM_ACTION));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(ErrorCode.ACCESS_DENIED.httpStatus()).body(new ErrorResponse(ErrorCode.ACCESS_DENIED));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        log.info(message);
        ErrorResponse response = new ErrorResponse();
        response.setCode("APP-002");
        response.setDescription(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleBannedUserException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.INTERNAL_SERVER));
    }


    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleBannedUserException(InternalAuthenticationServiceException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.BANNED_PERFORM_ACTION));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode("APP-101");
        errorResponse.setDescription(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

