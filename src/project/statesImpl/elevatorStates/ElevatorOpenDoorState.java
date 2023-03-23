package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.Time;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.statesImpl.State;

public class ElevatorOpenDoorState extends State{
    private Elevator elevator; 

    public ElevatorOpenDoorState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public State handleState() {
        Log.notification("ELEVATOR", "Open Door", new Date(), this.elevator.getSystemName());
        this.elevator.sleep(Time.OPEN_DOOR.getTime());

		// handle unloading and loading passenger
		Log.notification("ELEVATOR", "Unloading Passenger", new Date(), this.elevator.getSystemName());
		this.elevator.sleep(Time.UNLOAD_PASSENGERS.getTime());

        // Load/unload passengers add to the buttons arraylist then close doors
		if (this.elevator.getFloorInputButtons().containsKey(this.elevator.getElevatorStatus().getCurrentFloor())){
			Log.notification("ELEVATOR", "Loading Passenger", new Date(), this.elevator.getSystemName());
			this.elevator.sleep(Time.LOAD_PASSENGERS.getTime());
			this.elevator.setDestinations(this.elevator.appendButtonsToExistingList(this.elevator.getDestinations(), this.elevator.getFloorInputButtons().get(this.elevator.getElevatorStatus().getNextDestination())));
		}

        return this.elevator.getElevatorDoorCloseState();
    }
    
}
