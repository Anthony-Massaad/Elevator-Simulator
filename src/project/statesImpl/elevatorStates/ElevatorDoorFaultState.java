package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.SimulationConstants;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.statesImpl.State;

public class ElevatorDoorFaultState extends State{
    private Elevator elevator; 
    
    public ElevatorDoorFaultState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public State handleState() {
        Log.warning("ELEVATOR", "DOOR FAULT, FIXING ITSELF", new Date(), this.elevator.getSystemName());
        this.elevator.getElevatorStatus().setIsStuck(true);
        Log.notification("ELEVATOR", "Elevator is Stuck True", new Date(), this.elevator.getSystemName());
        this.elevator.sendUpdateStatus();
        this.elevator.sleep(2000);
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
