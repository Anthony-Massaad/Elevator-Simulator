package project.messageSystem.messages;

import java.util.ArrayList;
import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

public class FloorRequestElevator extends Message {
    private final int floorNumber;
    private final MotorDirection direction; 
    private final ArrayList<Integer> buttonsToBePressed;
    
    public FloorRequestElevator(Date timeStamp, int floorNumber, MotorDirection direction, ArrayList<Integer> buttonsToBePressed) {
        super(timeStamp);
        this.floorNumber = floorNumber;
        this.direction = direction; 
        this.buttonsToBePressed = buttonsToBePressed;
    }

    public int getFloorNumber(){
        return this.floorNumber; 
    }

    public MotorDirection getDirection(){
        return this.direction; 
    }

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
