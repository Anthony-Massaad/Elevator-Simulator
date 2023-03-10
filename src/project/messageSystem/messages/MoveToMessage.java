package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class MoveToMessage extends Message{
	
	private final int destinationFloor; 
	
	public MoveToMessage(Date timeStamp, int destinationFloor) {
		super(timeStamp);
		this.destinationFloor = destinationFloor; 
		// TODO Auto-generated constructor stub
	}
	
	
	public int getDestinationFloor() {
		return this.destinationFloor;
	}

	/**
	 * To string method.
	 */
	@Override
	public String toString() {
		String str = super.toString();
		str += ", destinationFloor=" + this.destinationFloor;
		return str;
	}

}
