package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class ElevatorRequestMessage extends Message{
	
	private final int floorNumber; 
	public ElevatorRequestMessage(Date timeStamp, int floorNumber) {
		super(timeStamp);
		this.floorNumber = floorNumber; 
	}
	
	public int getFloorNumber() {
		return this.floorNumber;
	}
	
	public String toString() {
		return "Request Elevator to floor: " + this.floorNumber; 
	}
	
}
