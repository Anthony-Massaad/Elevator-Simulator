package project.elevatorImpl;

import project.constants.MotorDirection;

public class ElevatorStatus {
    
    private int numberOfPassengers; 
    private int nextDestination; 
	private int currentFloor;
	private MotorDirection motorDir;

    public ElevatorStatus(){
        this(0, 0, 0, MotorDirection.IDLE);
    }

    public ElevatorStatus(int numberOfPassengers, int nextDestination, int currentFloor, MotorDirection motorDir){
        this.numberOfPassengers = numberOfPassengers;
        this.nextDestination = nextDestination;
        this.currentFloor = currentFloor;
        this.motorDir = motorDir;
    }

    public void setMotorDirection(MotorDirection motorDir){
        this.motorDir = motorDir;
    }

    public void setCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }

    public void setNextDestination(int nextDestination){
        this.nextDestination = nextDestination; 
    }

    public void setNumberOfPassengers(int numberOfPassengers){
        this.numberOfPassengers = numberOfPassengers;
    }

    public MotorDirection getMotorDirection(){
        return this.motorDir;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    } 

    public int getNextDestination(){
        return this.nextDestination;
    }

    public int getNumberOfPassengers(){
        return this.numberOfPassengers;
    }

    public void update(MotorDirection direction, int currentFloor, int numberOfPassengers, int nextDestination){
        this.motorDir = direction; 
        this.currentFloor = currentFloor;
        this.numberOfPassengers = numberOfPassengers;
        this.nextDestination = nextDestination;
    }

}
