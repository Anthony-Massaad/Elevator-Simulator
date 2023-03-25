package project.messageSystem.messages;

import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

/**
 * ElevatorLeavingMesage for when the elevator leaves a floor
 * @author Anthony Massaad SYSC3303 Group 2
 */
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

    /**
     * Get the floor number
     * @return Integer, the floor number
     */
    public int getFloorNumber(){
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
     * To String printing the message
     */
    public String toString(){
        return "Elevator is Leaving Floor=" + this.floorNumber; 
    }
    
}
