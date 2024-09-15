package com.electric_diary.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
		String message = exception.getEntityType() + " with ID " + exception.getEntityId() + " not found.";
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler({ NumberFormatException.class })
	public ResponseEntity<Object> handleNumberFormatException(NumberFormatException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format.");
	}

	@ExceptionHandler({ CustomBadRequestException.class })
	public ResponseEntity<Object> handleCustomBadRequestException(CustomBadRequestException exception) {
		String errorMessage = ErrorMessageUtil.createErrorMessage(exception.getResult());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	@ExceptionHandler({ InvalidGradingTypeException.class })
	public ResponseEntity<Object> handleInvalidGradingTypeException(InvalidGradingTypeException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid grading type.");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
	    List<ErrorResponse> errors = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(err -> new ErrorResponse(err.getField(), err.getDefaultMessage(), err.getRejectedValue()))
	            .collect(Collectors.toList());
	    return ResponseEntity.badRequest().body(errors);
	}
}
