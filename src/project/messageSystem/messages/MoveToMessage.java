package project.messageSystem.messages;

import java.util.ArrayList;
import java.util.Date;

import project.messageSystem.Message;

public class MoveToMessage extends Message{
	
	private final int destinationFloor; 
	private final ArrayList<Integer> buttonsToBePressed;

	public MoveToMessage(Date timeStamp, int destinationFloor, ArrayList<Integer> buttonsToBePressed) {
		super(timeStamp);
		this.destinationFloor = destinationFloor; 
		this.buttonsToBePressed = buttonsToBePressed;
	}
	
	
	public int getDestinationFloor() {
		return this.destinationFloor;
	}

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
