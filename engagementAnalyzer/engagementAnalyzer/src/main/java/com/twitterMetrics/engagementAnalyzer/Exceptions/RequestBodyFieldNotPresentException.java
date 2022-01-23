package com.twitterMetrics.engagementAnalyzer.Exceptions;

/**
 * Exception specific for parsing errors in the request,
 * specific for not present mandatory fields.
 */
public class RequestBodyFieldNotPresentException extends Exception {
		
	private static final long serialVersionUID = 1L;
	
	/**
	 * Exception constructor
	 * 
	 * @param errorMessage message retrived with the exception
	 */
	public RequestBodyFieldNotPresentException(String errorMessage) {
		super(errorMessage);
	}
		
}
