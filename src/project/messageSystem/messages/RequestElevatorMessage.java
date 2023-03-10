package project.messageSystem.messages;

import java.util.ArrayList;
import java.util.Date;

import project.messageSystem.Message;

public class RequestElevatorMessage extends Message{
	
	private final int floorNumber;
	private final int elevatorID; 
	private final ArrayList<Integer> buttonsToBePressed;

	public RequestElevatorMessage(Date timeStamp, int floorNumber, int elevatorID, ArrayList<Integer> buttonsToBePressed){
		super(timeStamp);
		this.floorNumber = floorNumber; 
		this.elevatorID = elevatorID; 
		this.buttonsToBePressed = buttonsToBePressed;
	}
	
	public int getElevatorID() {
		return this.elevatorID;
	}
	
	public int getFloorNumber() {
		return this.floorNumber;
	}

	public ArrayList<Integer> getButtonsToBePressed(){
		return this.buttonsToBePressed;
	}
	
	/**
	 * To string method.
	 */
	@Override
	public String toString() {
		return "Scheduler Request an Elevator Message, floorNumber=" + this.floorNumber + ", ElevatorChosen=" + this.elevatorID;
	}

}
