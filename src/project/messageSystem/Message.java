package project.messageSystem;

import java.util.Date;
import java.io.Serializable;
/**
 * Class Message for defining a message that can be passed as communication between the Floor, Scheduler and the Elevator systems.
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 */
public class Message implements Serializable{
    protected final Date timeStamp;

    /**
     * Constructor for the Message class.
     * @param timeStamp A Date object timeStamp.
     */
    public Message(Date timeStamp){
        this.timeStamp = timeStamp;
    }

    public Date getTimeStamp(){
        return this.timeStamp;
    }

    /**
     * Overriden toString method that returns a string representation of the Message superclass.
     */
    public String toString() {
        return "Message Type Received=" + this.getClass().getName(); 
    }
}