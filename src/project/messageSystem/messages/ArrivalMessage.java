package project.messageSystem.messages;

import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

public class ArrivalMessage extends Message{
	
	private final int floorNumber; 
	private final MotorDirection direction; 
	
	public ArrivalMessage(Date timeStamp, int floorNumber, MotorDirection direction) {
		super(timeStamp);
		this.floorNumber = floorNumber;
		this.direction = direction; 
	}

	public int getFloorNumber(){
		return this.floorNumber; 
	}

	public MotorDirection getDirection(){
		return this.direction; 
	}
	
	@Override
	public String toString() {
		return "Arrival Message floorNumber=" + this.floorNumber + ", direction=" + this.direction;		
	}

}
