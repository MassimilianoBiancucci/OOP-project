package com.twitterMetrics.engagementAnalyzer.Exceptions;

/**
 * Exception raised in case of call of not implemented methods.
 */
public class NotImplementedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Exception constructor
	 * 
	 * @param errorMessage message retrived with the exception
	 */
	public NotImplementedException(String errorMessage) {
		super(errorMessage);
	}
}
