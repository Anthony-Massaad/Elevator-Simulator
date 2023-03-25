package project.clockImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clock Class responsible for real time system and events time checking 
 * @author Anthony SYSC3303 Group 2
 */
public class Clock {
    private final int INCREMENT_MS = 1000;
    private final String DATE_FORMAT = "HH:mm:ss";

    private Date date;
    private final String START_TIME = "14:06:13.0";
    
    /**
     * Constructor for Clock.
     */
    public Clock() {
        try {
        	this.date = new SimpleDateFormat(DATE_FORMAT).parse(START_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Converting a string date to a Date object.
     * @param date A string date.
     * @return A Date object date.
     */
    public Date convertToDate(String date){
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new Error("Could nott convert String -> date!"); 
    }

    /**
     * Method for incrementing time by one second.
     */
    public void increment() {
        date.setTime(date.getTime() + this.INCREMENT_MS);
    }


    /**
     * Compares the date time to a passed in time.
     * @param A long value time.
     * @return boolean if they are the same.
     */
    public boolean isTime(long time) {
       return date.getTime() == time;
    }

}
