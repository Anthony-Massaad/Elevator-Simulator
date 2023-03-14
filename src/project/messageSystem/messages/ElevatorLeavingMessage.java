package project.messageSystem.messages;

import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

public class ElevatorLeavingMessage extends Message {

    private final MotorDirection direction; 
    private final int floorNumber; 
	/**
	 * Constructor for ArrivalMessage.
	 * @param Date object timeStamp.
	 * @param floorNumber An integer floor number.
	 * @param direction MotorDirection object direction.
	 */
	public ElevatorLeavingMessage(Date timeStamp, int floorNumber, MotorDirection direction) {
		super(timeStamp);
		this.floorNumber = floorNumber;
		this.direction = direction; 
	}

    public int getFloorNumber(){
        return this.floorNumber;
    }

    public MotorDirection getDirection(){
        return this.direction;
    }

    public String toString(){
        return "Elevator is Leaving Floor=" + this.floorNumber; 
    }
    
}
