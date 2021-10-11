package org.spring.we_care.excetion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> noSuchEntity(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.OK);
    } 

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorMessage> internalError(InternalErrorException ex) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.OK);
    } 
}