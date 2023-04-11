package project.messageSystem.messages;

import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

/**
 * Responsible for updating the floor with the gui
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class FloorUpdateMessage extends Message{

    private final MotorDirection direction;
    private final int floorNumber; 
    /**
     * Constructor 
     * @param timeStamp Date, the date
     * @param direction MotorDirection, direction
     * @param floorNumber Integer, floor number
     */
    public FloorUpdateMessage(Date timeStamp, MotorDirection direction, int floorNumber) {
        super(timeStamp);
        this.direction = direction;
        this.floorNumber = floorNumber;
    }

    public MotorDirection getDirection(){
        return this.direction;
    }

    public int getFloorNumber(){
        return this.floorNumber;
    }

    public String toString(){
        return "Update the floor components message.";
    }
    
}
