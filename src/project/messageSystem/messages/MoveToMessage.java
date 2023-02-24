package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

/**
 * Move To Message class that inherits from Message to indicate an elevator to move
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class MoveToMessage extends Message{

	private final int destinationFloor;
	
	/**
	 * Constructor 
	 * @param timeStamp Date, the date of the message creation 
	 * @param destinationFloor int, the floor to move to 
	 */
	public MoveToMessage(Date timeStamp, int destinationFloor) {
		super(timeStamp);
		this.destinationFloor = destinationFloor; 
	}
	
	/**
	 * Getter for Destination Floor
	 * @return Integer, the destination floor
	 */
	public int getDestinationFloor() {
		return this.destinationFloor; 
	}
	
	/**
	 * Overriden method to log the MoveToMessage Class
	 */
	public String toString() {
		return "Moving to Floor: " + this.destinationFloor; 
	}

}
