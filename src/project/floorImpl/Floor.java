package project.floorImpl;

import java.util.Date;
import project.constants.FloorButtonState;
import project.logger.Log;

public class Floor {

    private final int floorNumber; 
    // -1 means none existent, 0 means off, 1 means on 
    private FloorButtonState upBtn; 
    private FloorButtonState downBtn; 
    private final String systemName; 

    public Floor(int floorNumber, FloorButtonState upBtn, FloorButtonState downBtn, String systemName){
        this.floorNumber = floorNumber;
        this.upBtn = upBtn;
        this.downBtn = downBtn;
        this.systemName = systemName;
    }

    public int getFloorNumber(){
        return this.floorNumber;
    }

    public FloorButtonState getUpBtn(){
        return this.upBtn;
    }

    public FloorButtonState getDownBtn(){
        return this.downBtn;
    }

    public void setUpBtn(FloorButtonState state){
        this.upBtn = state;
        Log.notification("FLOOR", "Up button is set to " + FloorButtonState.parseState(state), new Date(), this.systemName);
    }

    public void setDownBtn(FloorButtonState state){
        this.downBtn = state;
        Log.notification("FLOOR", "Down button is set to " + FloorButtonState.parseState(state), new Date(), this.systemName);
    }

}
