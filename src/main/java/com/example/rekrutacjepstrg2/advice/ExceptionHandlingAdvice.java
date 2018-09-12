package com.example.rekrutacjepstrg2.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingAdvice {

	/**
	 * Called before {@link com.example.rekrutacjepstrg2.domain.Train Train} is persisted.
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Duplicates are not allowed.")
	public void handleDataIntegrityViolationExcpetion() {

	}
}