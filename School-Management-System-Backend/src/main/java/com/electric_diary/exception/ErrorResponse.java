package com.electric_diary.exception;

public class ErrorResponse{
	private String field;
	private String message;
	private Object rejectedValue;
	
	public ErrorResponse() {
	}
	
	public ErrorResponse(String field, String message, Object rejectedValue) {
		super();
		this.field = field;
		this.message = message;
		this.rejectedValue = rejectedValue;
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getRejectedValue() {
		return rejectedValue;
	}
	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
	
	
}
