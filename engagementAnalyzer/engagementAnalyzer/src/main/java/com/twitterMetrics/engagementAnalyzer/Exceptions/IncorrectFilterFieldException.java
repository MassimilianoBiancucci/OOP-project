package com.twitterMetrics.engagementAnalyzer.Exceptions;

/**
 * Exception specific for filters parsing, raised in case of invalid
 * field passed iin the filters tree.
 */
public class IncorrectFilterFieldException extends Exception{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Exception constructor
	 * 
	 * @param errorMessage message retrived with the exception
	 */
	public IncorrectFilterFieldException(String errorMessage) {
		super(errorMessage);
	}
	
}
