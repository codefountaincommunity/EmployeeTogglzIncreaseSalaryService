package com.code.fountain.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GloableExceptionHandler {

    @Autowired
       public HttpServletRequest request;

    @ExceptionHandler(ResourceExistException.class)
    public ProblemDetail resourceExistException(ResourceExistException ex){
       ProblemDetail pr= ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,ex.getMessage());
       pr.setTitle("email already exists");
       pr.setType(URI.create("Conflict"));
        return pr;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail resourceNotFound(ResourceNotFoundException ex){
        ProblemDetail pr= ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
        pr.setTitle("id does not exists");
        pr.setType(URI.create("NotFound"));
        return pr;
    }
}
