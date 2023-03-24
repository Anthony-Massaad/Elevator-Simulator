package project.statesImpl.elevatorStates;


import java.util.Date;
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
        this.elevator.sleep(2000);
        Log.warning("ELEVATOR", "DOOR FAULT, FIXED", new Date(), this.elevator.getSystemName());
        this.elevator.getElevatorStatus().setIsStuck(false);
        Log.notification("ELEVATOR", "Elevator is Stuck False", new Date(), this.elevator.getSystemName());
        this.elevator.getDestinations().remove(0);
        return this.elevator.getElevatorDoorCloseState();
    }

}
