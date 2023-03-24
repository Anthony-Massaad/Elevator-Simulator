package project.statesImpl.elevatorStates;

import project.elevatorImpl.Elevator;
import project.statesImpl.State;

public class ElevatorBrokenState extends State{

    private Elevator elevator; 
    public ElevatorBrokenState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public State handleState() {
        this.elevator.getElevatorStatus().setIsStuck(true);
        this.elevator.sendUpdateStatus();
        return this.elevator.getElevatorBrokenState();
    }
    
}
