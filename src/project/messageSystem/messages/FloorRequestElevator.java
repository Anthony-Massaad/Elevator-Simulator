package project.messageSystem.messages;

import java.util.ArrayList;
import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

/**
 * Floor sending request for elevator
 * @author Anthony Massaad SYSC3303 Group 2
 */
public class FloorRequestElevator extends Message {
    private final int floorNumber;
    private final MotorDirection direction; 
    private final ArrayList<Integer> buttonsToBePressed;
    
    /**
     * Constructor for FloorRequestElevator.
     * @param timeStamp Date object timeStamp.
     * @param floorNumber Int floor number.
     * @param direction MotorDirection direction.
     * @param buttonsToBePressed ArrayList of buttons to be pressed.
     */
    public FloorRequestElevator(Date timeStamp, int floorNumber, MotorDirection direction, ArrayList<Integer> buttonsToBePressed) {
        super(timeStamp);
        this.floorNumber = floorNumber;
        this.direction = direction; 
        this.buttonsToBePressed = buttonsToBePressed;
    }

    /**
     * Getter method for the floor number.
     * @return The floor number.
     */
    public int getFloorNumber(){
        return this.floorNumber; 
    }

    /**
     * Getter method for the motor direction.
     * @return The motor direction.
     */
    public MotorDirection getDirection(){
        return this.direction; 
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
		return "Floor Request Message to floorNumber=" + this.floorNumber + ", Direction=" + direction;
	}
    
}
