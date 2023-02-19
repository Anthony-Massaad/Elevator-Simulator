package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class MoveToMessage extends Message{

	private final int destinationFloor;
	public MoveToMessage(Date timeStamp, int destinationFloor) {
		super(timeStamp);
		this.destinationFloor = destinationFloor; 
	}
	
	public int getDestinationFloor() {
		return this.destinationFloor; 
	}
	
	public String toString() {
		return "Moving to Floor: " + this.destinationFloor; 
	}

}
