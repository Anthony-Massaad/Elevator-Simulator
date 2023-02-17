package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class RequestElevatorMessage extends Message{
	
	private int floorNumber; 
	public RequestElevatorMessage(Date timeStamp, int floorNumber) {
		super(timeStamp);
		this.floorNumber = floorNumber; 
		// TODO Auto-generated constructor stub
	}
	
	public int getFloorNumber() {
		return this.floorNumber; 
	}
	
	public String toString() { 
		return "Request Elevator to Floor Message Sent at [" + this.getTimeStamp().getTime() + "]" + " floor num: " + this.floorNumber; 
	}
	
}
