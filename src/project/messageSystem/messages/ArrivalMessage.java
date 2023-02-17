package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class ArrivalMessage extends Message{
	
	private int floorNumber;
	public ArrivalMessage(Date timeStamp, int floorNumber) {
		super(timeStamp);
		this.floorNumber = floorNumber; 
		// TODO Auto-generated constructor stub
	}
	
	public int getFloorNumber() {
		return this.floorNumber; 
	}
	
	public String toString() { 
		return "Elevator Arrving to Floor Message Sent at [" + this.getTimeStamp().getTime() + "]" + " floor num: " + this.floorNumber; 
	}

}
