package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class ArrivalMessage extends Message{

	private final int floorNumber; 
	public ArrivalMessage(Date timeStamp, int floorNumber) {
		super(timeStamp);
		this.floorNumber = floorNumber;
	}
	
	private int getFloorNumber() {
		return this.floorNumber; 
	}
	
	public String toString() {
		return "Arrived to floor " + this.floorNumber; 
	}

}
