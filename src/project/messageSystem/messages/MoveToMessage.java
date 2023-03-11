package project.messageSystem.messages;

import java.util.ArrayList;
import java.util.Date;

import project.messageSystem.Message;

public class MoveToMessage extends Message{
	
	private final int destinationFloor; 
	private final ArrayList<Integer> buttonsToBePressed;

	/**
	 * Constructor for MoveToMessage.
	 * @param timeStamp A Date object timeStamp.
	 * @param destinationFloor An integer destinationFloor.
	 * @param buttonsToBePressed An arraylist of buttons to be pressed.
	 */
	public MoveToMessage(Date timeStamp, int destinationFloor, ArrayList<Integer> buttonsToBePressed) {
		super(timeStamp);
		this.destinationFloor = destinationFloor; 
		this.buttonsToBePressed = buttonsToBePressed;
	}
	
	/**
	 * Getter method for the destination floor.
	 * @return The destination floor.
	 */
	public int getDestinationFloor() {
		return this.destinationFloor;
	}

	/**
	 * Getter method for the arraylist of buttons to be pressed.
	 * @return The arraylist of buttons to be pressed.
	 */
	public ArrayList<Integer> getButtonsToBePressed(){
		return this.buttonsToBePressed;
	}

	/**
	 * To string method.
	 */
	@Override
	public String toString() {
		return "Move Elevator To Message, destinationFloor=" + this.destinationFloor;
	}

}
