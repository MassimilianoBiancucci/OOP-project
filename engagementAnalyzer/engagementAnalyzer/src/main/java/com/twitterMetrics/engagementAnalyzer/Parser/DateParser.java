package com.twitterMetrics.engagementAnalyzer.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
	
	// inner date field
	private LocalDateTime date;
	
	// Date time format consider 'Z' character for time with zero offset or Zulu time.
	private static DateTimeFormatter twitterFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static DateTimeFormatter simpleFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
	
	// errors
	private boolean parseError = false;
	
	// Constructor for the date argument
	public DateParser(String date) {
		this.setDate(date);
	}
	
	// constructor for the date local date argument
	public DateParser(LocalDateTime date) {
		this.date = date;
	}
	
	public DateParser setDate(String date) throws DateTimeParseException {
		
		LocalDateTime parsedDate;
		
		try {
			// parse the string with the twitter format
			parsedDate = LocalDateTime.parse(date, twitterFormat);
			
		}catch(DateTimeParseException e) {
			// if the twitter format don't work try the simple format
			
			try {
				// parse the string with the simple format
				parsedDate = LocalDateTime.parse(date, simpleFormat);
				
			}catch(DateTimeParseException ex) {
				// if neither the simple format work the format isn't accepted
				// so initialize the LocalDateTime to now and rise the error flag
				// then throw the exception
				parsedDate = LocalDateTime.now();
				parseError = true;
				throw ex;
			}
		}
		
		this.date =  parsedDate;
		return this;
	}
	
	public DateParser setDate(LocalDateTime date) {
		this.date = date;
		return this;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public String getTwitterDate() {
		
		return date.format(twitterFormat);
	}
	
	public String getSimpleDate() {
		return date.format(simpleFormat);
	}
	
	public boolean getStatus() {
		return this.parseError;
	}
}
