package project.statesImpl.elevatorStates;

import java.util.Date;

import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.statesImpl.State;

/**
 * State for handling the elevator broken state. Extends State
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorBrokenState extends State{

    private Elevator elevator; 

    /**
     * Constructor for Elevator Broken State
     * @param elevator Elevator, the elevator which is the super sate
     */
    public ElevatorBrokenState(Elevator elevator){
        super();
        this.elevator = elevator;
    }

    /**
     * Overriden method for handling the current state
     * @return State, the new state
     */
    @Override
    public State handleState() {
        Log.error("ELEVATOR", "Elevator Entirely Broken!!!!!!!", new Date(), this.elevator.getSystemName());
        return this.elevator.getElevatorBrokenState();
    }
    
}
