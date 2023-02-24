package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

/**
 * Class to identify an arrival by an elevator as an object. Inherits from Message class
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 * 
 */
public class ArrivalMessage extends Message{

	private final int floorNumber; 
	
	/**
	 * Constructor
	 * @param timeStamp Date, the date of the message
	 * @param floorNumber int, the floor number in which the elevator arrived to 
	 */
	public ArrivalMessage(Date timeStamp, int floorNumber) {
		super(timeStamp);
		this.floorNumber = floorNumber;
	}

	/**
	 * Overriden method to log the Arrival Message
	 */
	public String toString() {
		return "Arrived to floor " + this.floorNumber; 
	}

}
