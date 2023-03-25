package project.elevatorImpl;

import project.constants.MotorDirection;

/**
 * Class to log the current status of the Elevator
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorStatus {
    
    private int numberOfPassengers; 
    private int nextDestination; 
	private int currentFloor;
    private boolean isStuck; 
	private MotorDirection motorDir;

	/**
	 * Default constructor for the ElevatorStatus that initializes the base values for the Elevator.
	 */
    public ElevatorStatus(){
        this(0, 0, 0, MotorDirection.IDLE);
    }

    /**
     * Second constructor for the ElevatorStatus.
     * @param numberOfPassengers Integer number of passengers.
     * @param nextDestination Integer nextDestination.
     * @param currentFloor Integer currentFloor.
     * @param motorDir Motor direction state.
     */
    public ElevatorStatus(int numberOfPassengers, int nextDestination, int currentFloor, MotorDirection motorDir){
        this.numberOfPassengers = numberOfPassengers;
        this.nextDestination = nextDestination;
        this.currentFloor = currentFloor;
        this.motorDir = motorDir;
        this.isStuck = false; 
    }

    /**
     * get is stuck boolean 
     * @return boolean, true if stuck otherwise false 
     */
    public boolean getIsStuck(){
        return this.isStuck;
    }

    /**
     * set the is stuck variable
     * @param isStuck boolean, true if stuck otherwise false
     */
    public void setIsStuck(boolean isStuck){
        this.isStuck = isStuck;
    }

    /**
     * Void method for setting the motor direction.
     * @param motorDir A motor direction.
     */
    public void setMotorDirection(MotorDirection motorDir){
        this.motorDir = motorDir;
    }

    /**
     * Void method for setting the current floor.
     * @param currentFloor The current floor.
     */
    public void setCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }

    /**
     * Void method for setting the next destination.
     * @param motorDir An integer nextDestination.
     */
    public void setNextDestination(int nextDestination){
        this.nextDestination = nextDestination; 
    }

    /**
     * Void method for setting the number of passengers.
     * @param numberOfPassengers The number of passengers.
     */
    public void setNumberOfPassengers(int numberOfPassengers){
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * Getter method for the motor direction.
     */
    public MotorDirection getMotorDirection(){
        return this.motorDir;
    }

    /**
     * Getter method for the current floor.
     */
    public int getCurrentFloor(){
        return this.currentFloor;
    } 

    /**
     * Getter method for the next destination.
     */
    public int getNextDestination(){
        return this.nextDestination;
    }

    /**
     * Getter method for the number of passengers.
     */
    public int getNumberOfPassengers(){
        return this.numberOfPassengers;
    }

    /**
     * Void method for updating the elevator status.
     * @param direction A motor direction.
     * @param currentFloor The current floor.
     * @param numberOfPassengers The number of passengers.
     * @param nextDestination The next destination.
     */
    public void update(MotorDirection direction, int currentFloor, int numberOfPassengers, int nextDestination, boolean isStuck){
        this.motorDir = direction; 
        this.currentFloor = currentFloor;
        this.numberOfPassengers = numberOfPassengers;
        this.nextDestination = nextDestination;
        this.isStuck = isStuck;
    }

}
