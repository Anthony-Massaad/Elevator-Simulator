package project.messageSystem.messages;

import java.util.Date;

import project.messageSystem.Message;

/**
 * Responsible for updating the elevator with the gui simulation
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorMoved extends Message{

    private final int previousFloor; 
    private final int currentFloor; 
    private final int elevatorID;
    /**
     * constructor
     * @param timeStamp Date, the date
     * @param previousFloor Integer, the previous floor
     * @param currentFloor Integer, the current floor
     * @param elevatorID Integer, the elevator id
     */
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
