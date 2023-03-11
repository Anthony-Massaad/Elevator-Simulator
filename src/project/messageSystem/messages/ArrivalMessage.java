package project.messageSystem.messages;

import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

public class ArrivalMessage extends Message{
	
	private final int floorNumber; 
	private final MotorDirection direction; 
	
	/**
	 * Constructor for ArrivalMessage.
	 * @param Date object timeStamp.
	 * @param floorNumber An integer floor number.
	 * @param direction MotorDirection object direction.
	 */
	public ArrivalMessage(Date timeStamp, int floorNumber, MotorDirection direction) {
		super(timeStamp);
		this.floorNumber = floorNumber;
		this.direction = direction; 
	}

	/**
	 * Getter method for the floor number.
	 * @return Integer floor number.
	 */
	public int getFloorNumber(){
		return this.floorNumber; 
	}

	/**
	 * Getter method for the motor direction.
	 * @return The motor direction.
	 */
	public MotorDirection getDirection(){
		return this.direction; 
	}
	
	/**
	 * Overriden toString method.
	 */
	@Override
	public String toString() {
		return "Arrival Message floorNumber=" + this.floorNumber + ", direction=" + this.direction;		
	}

}
