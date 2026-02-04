package com.jannesh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> itemNotFoundExceptionHandler(ItemNotFoundException e) {

        Map<String, Object> errorObj = new LinkedHashMap<>();
        errorObj.put("statusCode", 404);
        errorObj.put("errorMessage", e.getMessage());
        errorObj.put("errorInformation", "Kindly enter valid itemId");

        return new ResponseEntity<>(errorObj, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {

        Map<String, Object> errorObj = new LinkedHashMap<>();
        errorObj.put("statusCode", 400);
        errorObj.put("errorMessage", "Bad Request");
        errorObj.put("errorInformation", e.getMessage());

        return new ResponseEntity<>(errorObj, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerExceptionHandler(NullPointerException e) {

        Map<String, Object> errorObj = new LinkedHashMap<>();
        errorObj.put("statusCode", 400);
        errorObj.put("errorMessage", "Bad Request");
        errorObj.put("errorInformation", e.getMessage());

        return new ResponseEntity<>(errorObj, HttpStatus.BAD_REQUEST);
    }
}
