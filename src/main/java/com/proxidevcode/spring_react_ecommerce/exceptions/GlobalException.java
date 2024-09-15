package com.proxidevcode.spring_react_ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.proxidevcode.spring_react_ecommerce.dtos.ErrorResponseApi;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponseApi> handleException(MethodArgumentNotValidException e, WebRequest request ){
      ErrorResponseApi errorResponseApi = new ErrorResponseApi();
      Map<String, String> errors = new HashMap<>();
      e.getBindingResult().getAllErrors().forEach((error)->{
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);

      });
      errorResponseApi.setErrors(errors);
      return new ResponseEntity<>(errorResponseApi, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponseApi> handleException(ResourceNotFoundException e, WebRequest request ){
      ErrorResponseApi errorResponseApi = new ErrorResponseApi().builder()
      .message(e.getMessage())
      .timestamp(LocalDateTime.now())
      .errorCode(HttpStatus.NOT_FOUND.value())
      .error(request.getDescription(false))
      .build();
    
      return new ResponseEntity<>(errorResponseApi, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ErrorResponseApi> handleException (BadRequestException e, WebRequest request ){
      ErrorResponseApi errorResponseApi = new ErrorResponseApi().builder()
      .message(e.getMessage())
      .timestamp(LocalDateTime.now())
      .errorCode(HttpStatus.BAD_REQUEST.value())
      .error(request.getDescription(false))
      .build();
    
      return ResponseEntity.badRequest().body(errorResponseApi);


    }
    
    
}
