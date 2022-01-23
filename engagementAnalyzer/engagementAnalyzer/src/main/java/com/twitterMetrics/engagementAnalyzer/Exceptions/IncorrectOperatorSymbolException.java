package com.twitterMetrics.engagementAnalyzer.Exceptions;

/**
 * Exception specific for Operator sybol assegnation, 
 * raised if an invalid symbol  is passed to an Operator object.
 */
public class IncorrectOperatorSymbolException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Exception constructor
	 * 
	 * @param errorMessage message retrived with the exception
	 */
	public IncorrectOperatorSymbolException(String errorMessage) {
		super(errorMessage);
	}
	
}
