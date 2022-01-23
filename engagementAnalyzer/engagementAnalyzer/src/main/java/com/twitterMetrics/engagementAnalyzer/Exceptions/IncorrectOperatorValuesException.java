package com.twitterMetrics.engagementAnalyzer.Exceptions;

/**
 * Exception specific for Operator values assegnation, 
 * raised if an invalid OperatorValues object is passed to an Operator object.
 */
public class IncorrectOperatorValuesException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Exception constructor
	 * 
	 * @param errorMessage message retrived with the exception
	 */
	public IncorrectOperatorValuesException(String errorMessage) {
		super(errorMessage);
	}
	
}
