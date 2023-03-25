package project.messageSystem.messages;

import java.util.ArrayList;
import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

/**
 * Scheduler to floor elevattor request message
 * @author Anthony Massaad SYSC3303 Group 2
 */
public class RequestElevatorMessage extends Message{
	
	private final int floorNumber;
	private final int elevatorID; 
	private final ArrayList<Integer> buttonsToBePressed;
	private final MotorDirection direction; 

	/**
	 * Constructor for RequestElevatorMessage.
	 * @param timeStamp A Date object timeStamp.
	 * @param floorNumber An integer floor number.
	 * @param elevatorID An integer elevatorID.
	 * @param buttonsToBePressed An arrayList of buttons to be pressed.
	 */
	public RequestElevatorMessage(Date timeStamp, int floorNumber, int elevatorID, ArrayList<Integer> buttonsToBePressed, MotorDirection direction){
		super(timeStamp);
		this.floorNumber = floorNumber; 
		this.elevatorID = elevatorID; 
		this.buttonsToBePressed = buttonsToBePressed;
		this.direction = direction; 
	}
	
	/**
	 * Getter for the elevator ID.
	 * @return The elevator ID.
	 */
	public int getElevatorID() {
		return this.elevatorID;
	}
	
	/**
	 * Getter for the floor number.
	 * @return The floor number.
	 */
	public int getFloorNumber() {
		return this.floorNumber;
	}

	/**
	 * get the direction
	 * @return MotorDirection, the direction 
	 */
	public MotorDirection getDirection(){
		return this.direction;
	}

	
	/**
	 * Getter for the buttons to be pressed.
	 * @return An arraylist of buttons to be pressed.
	 */
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
