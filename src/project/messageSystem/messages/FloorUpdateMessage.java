package project.messageSystem.messages;

import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

public class FloorUpdateMessage extends Message{

    private final MotorDirection direction;
    private final int floorNumber; 
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
