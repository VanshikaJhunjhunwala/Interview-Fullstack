package com.example.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException ex){

        ErrorResponse res=new ErrorResponse();

        res.setMessage(ex.getMessage());
        res.setStatus(404);
        res.setTimestamp(System.currentTimeMillis());

        return res;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleValidationException(
            MethodArgumentNotValidException ex){

        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();
    }

    @ExceptionHandler(
            org.springframework.security.authorization.AuthorizationDeniedException.class
    )
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(
            org.springframework.security.authorization.AuthorizationDeniedException ex){

        return "Access Denied";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneric(Exception ex){

        return ex.getMessage();
    }
}