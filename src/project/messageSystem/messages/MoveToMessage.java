package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message; 

public class MoveToMessage extends Message{
	
	private int destinationFloor; 
	public MoveToMessage(Date timeStamp, int destinationFloor) {
		super(timeStamp);
		this.destinationFloor = destinationFloor;
		// TODO Auto-generated constructor stub
	}
	
	public int getDestinationFloor() {
		return this.destinationFloor;
	}
	
	public String toString() {
		return "Move Floor Message Sent at [" + this.getTimeStamp().getTime() + "]" + " destination: " + this.destinationFloor; 
	}
}
