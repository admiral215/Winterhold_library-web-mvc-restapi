package com.indocyber.winterhold.exceptions;


import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handlerEntityNotFoundException(EntityNotFoundException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorMessageDto<Object> dto = ErrorMessageDto.builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
//                .throwable(e)
                .errors(e)
                .build();
        return new ResponseEntity<>(dto,httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e){
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

        var errors = new HashMap<String, String >();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> errors.put(
                fieldError.getField(), fieldError.getDefaultMessage()
        ));

        ErrorMessageDto<Map<String, String>> dto = ErrorMessageDto.<Map<String, String>>builder()
                .httpStatus(httpStatus)
                .message("Input invalid")
//                .throwable(e)
                .errors(errors)
                .build();

        return new ResponseEntity<>(dto,httpStatus);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorMessageDto<String>> handleEntityExistException(
            EntityExistsException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ErrorMessageDto<String> dto = ErrorMessageDto.<String>builder()
                .httpStatus(httpStatus)
                .message(e.getMessage())
//                .throwable(e)
                .errors(e.getMessage())
                .throwable(e.getCause())
                .build();

        return new ResponseEntity<>(dto,httpStatus);
    }

}
