package com.twitterMetrics.engagementAnalyzer.Exceptions;

/**
 * Exception specific for parsing errors in the request,
 * specific for unexpected field values.
 */
public class UnexpectedRequestBodyFieldValueException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Exception constructor
	 * 
	 * @param errorMessage message retrived with the exception
	 */
	public UnexpectedRequestBodyFieldValueException(String errorMessage) {
		super(errorMessage);
	}
		
}
