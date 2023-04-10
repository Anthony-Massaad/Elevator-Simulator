package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

public class ElevatorMoved extends Message{

    private final int previousFloor; 
    private final int currentFloor; 
    private final int elevatorID;
    public ElevatorMoved(Date timeStamp, int previousFloor, int currentFloor, int elevatorID) {
        super(timeStamp);
        this.previousFloor = previousFloor;
        this.currentFloor = currentFloor;
        this.elevatorID = elevatorID;
    }

    public int getPreviousFloor(){
        return this.previousFloor;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public int getElevatorId(){
        return this.elevatorID;
    }

    public String toString(){
        return "Elevator Moved!!";
    }
    
}
