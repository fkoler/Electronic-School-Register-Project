package com.electric_diary.exception;

@SuppressWarnings("serial")
public class InvalidGradeException extends RuntimeException {
	public InvalidGradeException(String message) {
		super(message);
	}
}