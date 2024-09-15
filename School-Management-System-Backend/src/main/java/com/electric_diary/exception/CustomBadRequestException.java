package com.electric_diary.exception;

@SuppressWarnings("serial")
public class CustomBadRequestException extends RuntimeException {
	private final Object result;

	public CustomBadRequestException(Object result) {
		this.result = result;
	}

	public Object getResult() {
		return result;
	}
}
