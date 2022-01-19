package com.twitterMetrics.engagementAnalyzer.Parser;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
	
	// inner date field
	private LocalDateTime date;
	private LocalTime time;
	
	// Date time format consider 'Z' character for time with zero offset from Greenwich or Zulu time.
	private static DateTimeFormatter twitterFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static DateTimeFormatter simpleFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	// selected format flag
	private boolean isTimeFlag = false;
	
	// Empty constructor
	public DateParser() {
		this.date = LocalDateTime.now();
	}

	// Constructor for the date argument
	public DateParser(String date) {
		this.setDate(date);
	}
	
	// constructor for the date local date argument
	public DateParser(LocalDateTime date) {
		this.date = date;
	}
	
	public DateParser setDate(String date) throws DateTimeParseException {
		
		try {
			// parse the string with the twitter format
			LocalDateTime parsedDate = LocalDateTime.parse(date, twitterFormat);
			this.date =  parsedDate;
		}catch(DateTimeParseException e) {
			// if the twitter format don't work try the simple format
			try {
				// parse the string with the simple format
				LocalDateTime parsedDate = LocalDateTime.parse(date, simpleFormat);
				this.date =  parsedDate;
			}catch(DateTimeParseException ex) {
				// if the simple format don't work try the time format
				try {
					// parse the string with the time format
					// if the timeFormat is selected rise the corresponding flag
					LocalTime parsedTime = LocalTime.parse(date, timeFormat);
					this.time =  parsedTime;
					isTimeFlag = true;
				}catch(DateTimeParseException exx) {
					// if neither the simple format work the format isn't accepted
					// so initialize the LocalDateTime to now and rise the error flag
					// then throw the exception
					LocalDateTime parsedDate = LocalDateTime.now();
					this.date =  parsedDate;
					throw exx;
				}
			}
		}
		
		
		return this;
	}
	
	public DateParser setDate(LocalDateTime date) {
		this.date = date;
		return this;
	}
	
	public LocalDateTime getDate() throws Exception {
		isDate();
		return date;
	}
	
	public DateParser setTime(LocalTime time) {
		this.time = time;
		return this;
	}
	
	public LocalTime getTime() throws Exception {
		isTime();
		return time;
	}
	
	public String getTwitterDate() {
		return date.format(twitterFormat);
	}
	
	public String getSimpleDate() {
		return date.format(simpleFormat);
	}
	
	public String getStrTime() {
		return time.format(timeFormat);
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
