package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.SimulationConstants;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.statesImpl.State;

/**
 * Elevator Door fault State
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorDoorFaultState extends State{
    private Elevator elevator; 
    
    /**
     * Constuctor for the door fault state
     * @param elevator Elevator, the elevator which is the super state
     */
    public ElevatorDoorFaultState(Elevator elevator){
        super();
        this.elevator = elevator;
    }

    /**
     * Handle the current state
     * @return State, the new state
     */
    @Override
    public State handleState() {
        // Elevator door is stuck
        Log.warning("ELEVATOR", "DOOR FAULT, FIXING ITSELF", new Date(), this.elevator.getSystemName());
        this.elevator.getElevatorStatus().setIsStuck(true);
        Log.notification("ELEVATOR", "Elevator is Stuck True", new Date(), this.elevator.getSystemName());
        this.elevator.sendUpdateStatus();
        this.elevator.sleep(2000);
        // elevator door is unstuck
        Log.warning("ELEVATOR", "DOOR FAULT, FIXED", new Date(), this.elevator.getSystemName());
        this.elevator.getElevatorStatus().setIsStuck(false);
        Log.notification("ELEVATOR", "Elevator is Stuck False", new Date(), this.elevator.getSystemName());
        this.elevator.sendUpdateStatus();

        if (this.elevator.getDestinations().get(SimulationConstants.DOOR_CLOSE_STUCK_INDEX) == SimulationConstants.DOOR_CLOSE_STUCK_INPUT){
            // door close stuck input
            this.elevator.getDestinations().remove(SimulationConstants.DOOR_CLOSE_STUCK_INDEX);
            return this.elevator.getElevatorDoorCloseState();
        }else{
            // door open stuck input
            this.elevator.getDestinations().remove(SimulationConstants.DOOR_OPEN_STUCK_INDEX);
            return this.elevator.getElevatorDoorOpenState();
        }
        
    }

}
