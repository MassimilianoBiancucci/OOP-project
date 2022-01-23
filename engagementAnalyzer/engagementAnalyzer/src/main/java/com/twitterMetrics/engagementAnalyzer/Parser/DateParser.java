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
	
	
	/**
	 * DateParser constructor withiout arguments.
	 * Create a DateParser with the field date setted at the current date and time.
	 */
	public DateParser() {
		this.date = LocalDateTime.now();
	}
	
	
	/**
	 * DateParser Constructor for the date property
	 * 
	 * @param date The date to set in String format
	 */
	public DateParser(String date) {
		this.setDate(date);
	}
	
	
	/**
	 * DateParser constructor for the date property
	 * 
	 * @param date date date to set in LocaDateTime format.
	 */
	public DateParser(LocalDateTime date) {
		this.date = date;
	}
	
	
	/**
	 * Setter method for the date property
	 * This method try to parse the string passed in 3 different ways:
	 * 	<ol>
		  <li>With the Twitter String date format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z' es: '2022-01-01T14:07:02.000Z'.</li>
		  <li>With a simple String date format: yyyy-MM-dd HH:mm:ss es: 2022-01-01 14:07:02.</li>
		  <li>With the Twitter String date format: HH:mm:ss es: 14:07:02.</li>
		</ol>
	 * If all the formats rise a parsing exception catched, than another persing exception will be raised
	 * without a catch.
	 * If the the passed stringis a date plus time object this pareser act like a dateTime Object and rise an exception if
	 * from the extern the getTime() object is called.
	 * If the the passed stringis a time only object this pareser act like a time Object and rise an exception if
	 * from the extern the getDate() object is called.
	 * 
	 * @param date a String in one of the supperted formats.
	 * @return A reference to this object.
	 * @throws DateTimeParseException An exception will be raised if the passed string don't match any of the supported formats.
	 */
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
	
	
	/**
	 * Setter method for the date property in LocalDateTime fomrat
	 * 
	 * @param date the date in LocalDateTime fomrat
	 * @return A reference to this object.
	 */
	public DateParser setDate(LocalDateTime date) {
		this.date = date;
		return this;
	}
	
	
	/**
	 * Getter method for the date property in LocalDateTime format
	 * 
	 * @return the current stored date property in LocalDateTime format
	 * @throws Exception An exception will be raised if the current stored property is time and the date property is empty.
	 */
	public LocalDateTime getDate() throws Exception {
		isDate();
		return date;
	}
	
	
	/**
	 * Setter method for the time property
	 * 
	 * @param time 
	 * @return A reference to this object.
	 */
	public DateParser setTime(LocalTime time) {
		this.time = time;
		return this;
	}
	
	
	/**
	 * Setter method for the time property
	 * 
	 * @return the time in LocalTime fomrat
	 * @throws Exception An exception will be raised if the current stored property is date and the time property is empty.
	 */
	public LocalTime getTime() throws Exception {
		isTime();
		return time;
	}
	
	
	/**
	 * Getter method for the date property in the TwitterAPI string format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z' es: '2022-01-01T14:07:02.000Z'.
	 * 
	 * @return the date property in the TwitterAPI string format.
	 */
	public String getTwitterDate() {
		return date.format(twitterFormat);
	}
	
	
	/**
	 * Getter method for the date property in simple String format: yyyy-MM-dd HH:mm:ss es: '2022-01-01 14:07:02'.
	 * 
	 * @return the date property in simple String format.
	 */
	public String getSimpleDate() {
		return date.format(simpleFormat);
	}
	
	
	/**
	 * Getter method for the time property in String format: HH:mm:ss es: '14:07:02'.
	 * 
	 * @return the time property in String format.
	 */
	public String getStrTime() {
		return time.format(timeFormat);
	}
	
	
	/**
	 * Method that check if the date-time string passed has only the time, if it doesn't happen throw an exception.
	 * 
	 * @throws Exception An exception will be raised if the current stored property is date and the time property is empty.
	 */
	public void isTime() throws Exception{
		if(!isTimeFlag) {
			throw new Exception("The DateParser object expect a time string without date, instead the string contain also the date values.");
		}
	}
	
	
	/**
	 * Method that check if the date-time string passed has date and time,  if it doesn't happen throw an exception.
	 * 
	 * @throws Exception An exception will be raised if the current stored property is time and the date property is empty.
	 */
	public void isDate() throws Exception{
		if(isTimeFlag) {
			throw new Exception("The DateParser object expect a date-time string, instead the string contain only the time values.");
		}
	}
	
	
	/**
	 * Method that check if the date-time string passed has only the time.
	 * 
	 * @return true if the DateParser object contain a time property and the date property is empty.
	 */
	public boolean isTimeBool(){
		if(!isTimeFlag) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * Method that check if the date-time string passed has date and time.
	 * 
	 * @return true if the DateParser object contain a date property and the time property is empty.
	 */
	public boolean isDateBool(){
		if(isTimeFlag) {
			return false;
		}
		return true;
	}
}
