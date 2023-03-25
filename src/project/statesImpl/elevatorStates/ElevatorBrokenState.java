package project.statesImpl.elevatorStates;

import java.util.Date;

import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.statesImpl.State;

public class ElevatorBrokenState extends State{

    private Elevator elevator; 
    public ElevatorBrokenState(Elevator elevator){
        super();
        this.elevator = elevator;
    }

    @Override
    public State handleState() {
        Log.error("ELEVATOR", "Elevator Entirely Broken!!!!!!!", new Date(), this.elevator.getSystemName());
        return this.elevator.getElevatorBrokenState();
    }
    
}
