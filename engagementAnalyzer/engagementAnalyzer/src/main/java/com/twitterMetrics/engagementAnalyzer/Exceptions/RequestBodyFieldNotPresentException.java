package com.twitterMetrics.engagementAnalyzer.Exceptions;

public class RequestBodyFieldNotPresentException extends Exception {
		
	private static final long serialVersionUID = 1L;

	public RequestBodyFieldNotPresentException(String errorMessage) {
		super(errorMessage);
	}
		
}
