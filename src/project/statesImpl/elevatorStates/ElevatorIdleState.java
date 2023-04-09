package project.statesImpl.elevatorStates;

import project.elevatorImpl.Elevator;
import project.statesImpl.State;

/**
 * Elevator idle state
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorIdleState extends State{
    private Elevator elevator; 

    /**
     * Constructor for the elevator idle state
     * @param elevator Elevator, the elevator which is the superstate
     */
    public ElevatorIdleState(Elevator elevator){
        super();
        this.elevator = elevator; 
    }

    /**
     * Handle the current state
     * @return State, the new state
     */
    @Override
    public State handleState() {
        this.elevator.sleep(500);
        return this.elevator.getElevatorIdleState();
    }

    @Override
    public String toString(){
        return "Idle";
    }
    
}
