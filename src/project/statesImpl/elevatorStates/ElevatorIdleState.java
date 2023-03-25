package project.statesImpl.elevatorStates;

import project.elevatorImpl.Elevator;
import project.statesImpl.State;

public class ElevatorIdleState extends State{
    private Elevator elevator; 

    public ElevatorIdleState(Elevator elevator){
        super();
        this.elevator = elevator; 
    }

    @Override
    public State handleState() {
        this.elevator.sleep(500);
        return this.elevator.getElevatorIdleState();
    }
    
}
