package project.messageSystem.messages;

import java.util.Date;
import project.constants.MotorDirection;
import project.messageSystem.Message;

public class UpdatePositionMessage extends Message{

    private final int numberOfPassengers; 
    private final int nextDestination; 
	private final int currentFloor;
	private final MotorDirection motorDir;
    private final int elevatorID; 

    public UpdatePositionMessage(Date timeStamp, int elevatorID, int numberOfPassengers, int nextDestination, int currentFloor, MotorDirection motorDir) {
        super(timeStamp);
        this.numberOfPassengers = numberOfPassengers;
        this.nextDestination = nextDestination;
        this.currentFloor = currentFloor;
        this.motorDir = motorDir;
        this.elevatorID = elevatorID;
    }

    public int getElevatorID(){
        return this.elevatorID;
    }

    public int getNumberOfPassengers(){
        return this.numberOfPassengers;
    }

    public int getNextDestination(){
        return this.nextDestination;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public MotorDirection getDirection(){
        return this.motorDir; 
    }

    /**
	 * To string method.
	 */
	@Override
	public String toString() {
		return "Update Position with Scheduler Message, position to be updated for elevator=" + this.elevatorID;
	}
    
}
