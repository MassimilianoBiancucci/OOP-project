package com.twitterMetrics.engagementAnalyzer.Exceptions;

public class UnexpectedRequestBodyFieldValueException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UnexpectedRequestBodyFieldValueException(String errorMessage) {
		super(errorMessage);
	}
		
}
