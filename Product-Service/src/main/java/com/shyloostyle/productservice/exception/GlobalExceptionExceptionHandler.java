package com.shyloostyle.productservice.exception;

import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionExceptionHandler {
    // Handle specific exceptions here
    // For example, handle ProductNotFoundException, ProductEmptyException, etc.

    // Example of a method to handle ProductNotFoundException
     @ExceptionHandler({ProductNotFoundException.class,
             ProductIdException.class})
     public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {

         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
     }


}
