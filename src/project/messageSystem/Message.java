package project.messageSystem;

import java.util.Date;

/**
 * Class Message for defining a message that can be passed as communication between the Floor, Scheduler and the Elevator systems.
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 */
public class Message {
    private final Date timeStamp;
    private String message; 

    /**
     * base constructor for the Message class 
     * @param timeStamp Date, the date in which a message is made
     */
    public Message(Date timeStamp) {
    	this(timeStamp, "");
    }
    
    /**
     * Constructor for the Message class.
     * @param timeStamp A Date object timeStamp.
     */
    public Message(Date timeStamp, String message){
        this.timeStamp = timeStamp;
        this.message = message; 
    }

    /**
     * Getter method for returning a timeStamp.
     * @return A Date object timeStamp.
     */
    public Date getTimeStamp() {
        return this.timeStamp;
    }

    /**
     * Overriden toString method that returns a string representation of the Message superclass.
     */
    public String toString() {
        return "[" + this.timeStamp + "] " + this.message; 
    }
}
