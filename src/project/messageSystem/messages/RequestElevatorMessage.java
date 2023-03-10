package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class RequestElevatorMessage extends Message{
	
	private final int floorNumber;
	private final int elevatorID; 

	public RequestElevatorMessage(Date timeStamp, int floorNumber, int elevatorID){
		super(timeStamp);
		this.floorNumber = floorNumber; 
		this.elevatorID = elevatorID; 
	}
	
	public int getElevatorID() {
		return this.elevatorID;
	}
	
	public int getFloorNumber() {
		return this.floorNumber;
	}
	
	/**
	 * To string method.
	 */
	@Override
	public String toString() {
		String str = super.toString();
		str += ", floorNumber=" + this.floorNumber + ", ElevatorChosen=" + this.elevatorID;
		return str;
	}

}
