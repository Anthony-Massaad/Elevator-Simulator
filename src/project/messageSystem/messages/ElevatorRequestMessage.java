package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

/**
 * Elevator Request Message Class that inherits from Message to request an elevator
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class ElevatorRequestMessage extends Message{
	
	private final int floorNumber; 
	
	/**
	 * Constructor 
	 * @param timeStamp Date, the time in which the Message was created 
	 * @param floorNumber int, the floor number of the requested floor
	 */
	public ElevatorRequestMessage(Date timeStamp, int floorNumber) {
		super(timeStamp);
		this.floorNumber = floorNumber; 
	}
	
	/**
	 * Get the floor number
	 * @return Integer, the floor number 
	 */
	public int getFloorNumber() {
		return this.floorNumber;
	}
	
	/** 
	 * Overriden method to log the ElevatorRequestMessage class 
	 */
	public String toString() {
		return "Request Elevator to floor: " + this.floorNumber; 
	}
	
}
