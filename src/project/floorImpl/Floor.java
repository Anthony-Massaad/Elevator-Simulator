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

    /**
     * Constructor for the Floor.
     * @param floorNumber Integer floor number.
     * @param upBtn Up button defined by a floor button state.
     * @param downBtn Down button defined by a floor button state.
     * @param systemName String system name.
     */
    public Floor(int floorNumber, FloorButtonState upBtn, FloorButtonState downBtn, String systemName){
        this.floorNumber = floorNumber;
        this.upBtn = upBtn;
        this.downBtn = downBtn;
        this.systemName = systemName;
    }

    /**
     * Getter method for a floor number.
     * @return The floor number.
     */
    public int getFloorNumber(){
        return this.floorNumber;
    }

    /**
     * Getter method for the up button state.
     * @return The up button state.
     */
    public FloorButtonState getUpBtn(){
        return this.upBtn;
    }

    /**
     * Getter method for the down button state.
     * @return The down button state.
     */
    public FloorButtonState getDownBtn(){
        return this.downBtn;
    }

    /**
     * Setter method for setting the up button state.
     * @param state The floor button state.
     */
    public void setUpBtn(FloorButtonState state){
        this.upBtn = state;
        Log.notification("FLOOR", "Up button is set to " + FloorButtonState.parseState(state), new Date(), this.systemName);
    }
    
    /**
     * Setter method for setting the down button state.
     * @param state The floor button state.
     */
    public void setDownBtn(FloorButtonState state){
        this.downBtn = state;
        Log.notification("FLOOR", "Down button is set to " + FloorButtonState.parseState(state), new Date(), this.systemName);
    }

}
