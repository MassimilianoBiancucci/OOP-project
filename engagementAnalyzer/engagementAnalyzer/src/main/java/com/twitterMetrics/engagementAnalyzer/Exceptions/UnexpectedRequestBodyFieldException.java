package com.twitterMetrics.engagementAnalyzer.Exceptions;

/**
 * Exception specific for parsing errors in the request,
 * specific for unexpected fields.
 */
public class UnexpectedRequestBodyFieldException extends Exception {
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Exception constructor
	 * 
	 * @param errorMessage message retrived with the exception
	 */
	public UnexpectedRequestBodyFieldException(String errorMessage) {
		super(errorMessage);
	}
	
}
