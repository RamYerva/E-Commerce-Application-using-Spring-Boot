package com.springtutorials.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AdminCreationEcxception.class)
	public ResponseEntity<String> handleAdminCreationException(AdminCreationEcxception exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}
	
	@ExceptionHandler(ResourseNotFoundException.class)
	public ResponseEntity<String> resourseNotFoundException(ResourseNotFoundException exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}
}
