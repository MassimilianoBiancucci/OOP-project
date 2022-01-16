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
	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	// selected format flag
	private boolean isTimeFlag = false;
	
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
				// if the simple format don't work try the time format
				try {
					// parse the string with the time format
					// if the timeFormat is selected rise the corresponding flag
					parsedDate = LocalDateTime.parse(date, timeFormat);
					isTimeFlag = true;
				}catch(DateTimeParseException exx) {
					// if neither the simple format work the format isn't accepted
					// so initialize the LocalDateTime to now and rise the error flag
					// then throw the exception
					parsedDate = LocalDateTime.now();
					throw exx;
				}
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
	
	public String getTime() {
		return date.format(timeFormat);
	}
	
	// method that check if the date-time string passed has only the time, if it doesn't happen throw an exception.
	public void isTime() throws Exception{
		if(!isTimeFlag) {
			throw new Exception("The DateParser object expect a time string without date, instead the string contain also the date values.");
		}
	}
	
	// method that check if the date-time string passed has date and time,  if it doesn't happen throw an exception.
	public void isDate() throws Exception{
		if(isTimeFlag) {
			throw new Exception("The DateParser object expect a date-time string, instead the string contain only the time values.");
		}
	}
	
	// method that check if the date-time string passed has only the time.
	public boolean isTimeBool(){
		if(!isTimeFlag) {
			return false;
		}
		return true;
	}
	
	// method that check if the date-time string passed has date and time.
	public boolean isDateBool(){
		if(isTimeFlag) {
			return false;
		}
		return true;
	}
}
