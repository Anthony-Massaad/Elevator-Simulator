package project.messageSystem.messages;

import java.util.ArrayList;
import java.util.Date;
import project.constants.MotorDirection;
import project.messageSystem.Message;

/**
 * Update position message from elevator to floor
 * @author Anthony Massaad SYSC3303 Group 2
 */
public class UpdatePositionMessage extends Message{

    private final int numberOfPassengers; 
    private final int nextDestination; 
	private final int currentFloor;
	private final MotorDirection motorDir;
    private final int elevatorID; 
    private final boolean isStuck; 
    private final ArrayList<Integer> destinations;
    private final String state;

    /**
     * Constructor for UpdatePositionMessage.
     * @param timeStamp A Date timeStamp object.
     * @param elevatorID An int elevator ID.
     * @param numberOfPassengers An int numberOfPassengers.
     * @param nextDestination An int next destination for the Elevator.
     * @param currentFloor An int currentFloor.
     * @param motorDir A MotorDirection object motorDir.
     */
    public UpdatePositionMessage(Date timeStamp, int elevatorID, int numberOfPassengers, int nextDestination, int currentFloor, MotorDirection motorDir, boolean isStuck, ArrayList<Integer> destinations, String state) {
        super(timeStamp);
        this.numberOfPassengers = numberOfPassengers;
        this.nextDestination = nextDestination;
        this.currentFloor = currentFloor;
        this.motorDir = motorDir;
        this.elevatorID = elevatorID;
        this.isStuck = isStuck; 
        this.destinations = destinations;
        this.state = state;
    }

    /**
     * Getter method for the elevator ID.
     * @return The elevator ID.
     */
    public int getElevatorID(){
        return this.elevatorID;
    }

    /**
     * Getter method for the number of passengers.
     * @return The number of passengers.
     */
    public int getNumberOfPassengers(){
        return this.numberOfPassengers;
    }

    /**
     * Getter method for the next destination.
     * @return The next destination.
     */
    public int getNextDestination(){
        return this.nextDestination;
    }

    /**
     * Getter method for the current floor.
     * @return The current floor.
     */
    public int getCurrentFloor(){
        return this.currentFloor;
    }

    /**
     * Getter method for the motor direction.
     * @return The motor direction.
     */
    public MotorDirection getDirection(){
        return this.motorDir; 
    }

    /**
     * getter method for is stuck
     * @return boolean, true if stuck otherwise false
     */
    public boolean getIsStuck(){
        return this.isStuck;
    }

    public ArrayList<Integer> getDestinations(){
        return this.destinations; 
    }

    public String getState(){
        return this.state;
    }

    /**
	 * To string method.
	 */
	@Override
	public String toString() {
		return "Update Position with Scheduler Message, position to be updated for elevator=" + this.elevatorID + " currentfloor=" + this.currentFloor + " stuck=" + this.isStuck;
	}
    
}
