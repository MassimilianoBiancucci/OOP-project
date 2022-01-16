package com.twitterMetrics.engagementAnalyzer.Exceptions;

public class UnexpectedRequestBodyFieldException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public UnexpectedRequestBodyFieldException(String errorMessage) {
		super(errorMessage);
	}
	
}
