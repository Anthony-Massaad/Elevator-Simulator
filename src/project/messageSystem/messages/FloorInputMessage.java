package project.messageSystem.messages;

import java.util.Date;

import project.constants.MotorDirection;
import project.messageSystem.Message;

public class FloorInputMessage extends Message{

    private final String input; 
    private final MotorDirection direction; 
    private final int floorNumber;
    public FloorInputMessage(Date timeStamp, String input, MotorDirection direction, int floorNumber) {
        super(timeStamp);
        this.input = input;
        this.direction = direction;
        this.floorNumber = floorNumber;
    }

    public String getInput(){
        return this.input; 
    }

    public MotorDirection getDirection(){
        return this.direction;
    }

    public int getFloorNumber(){
        return this.floorNumber;
    }

    public String toString(){
        return "Input messsage=" + this.getInput();
    }
    
}
