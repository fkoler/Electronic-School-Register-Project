package com.electric_diary.exception;

public class ErrorMessageUtil {
	public static String createErrorMessage(Object result) {
		return "Error message based on result: " + result.toString();
	}
}
