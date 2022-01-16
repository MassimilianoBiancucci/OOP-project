package com.twitterMetrics.engagementAnalyzer.Exceptions;

public class IncorrectFilterFieldException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public IncorrectFilterFieldException(String errorMessage) {
		super(errorMessage);
	}
	
}
