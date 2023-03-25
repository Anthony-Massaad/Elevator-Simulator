package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.constants.Time;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.statesImpl.State;

public class ElevatorOpenDoorState extends State{
    private Elevator elevator; 

    public ElevatorOpenDoorState(Elevator elevator){
        super();
        this.elevator = elevator;
    }

    @Override
    public State handleState() {
        Log.notification("ELEVATOR", "Opening Door", new Date(), this.elevator.getSystemName());
        this.elevator.sleep(Time.OPEN_DOOR.getTime());

        // check door open stuck
        if (!this.elevator.getDestinations().isEmpty()){
            // if the elevator has at least 2 destinations
            if (this.elevator.getDestinations().get(SimulationConstants.DOOR_OPEN_STUCK_INDEX) < 0){
                // door is stuck if the value is less than 0
                return this.elevator.getElevatorDoorFaultState();
            }
        }

		// handle unloading and loading passenger
		Log.notification("ELEVATOR", "Unloading Passenger", new Date(), this.elevator.getSystemName());
		this.elevator.sleep(Time.UNLOAD_PASSENGERS.getTime());

        // Load/unload passengers add to the buttons arraylist then close doors
		if (this.elevator.getFloorInputButtons().containsKey(this.elevator.getElevatorStatus().getCurrentFloor())){
			Log.notification("ELEVATOR", "Loading Passenger", new Date(), this.elevator.getSystemName());
			this.elevator.sleep(Time.LOAD_PASSENGERS.getTime());
			this.elevator.setDestinations(this.elevator.appendButtonsToExistingList(this.elevator.getDestinations(), this.elevator.getFloorInputButtons().get(this.elevator.getElevatorStatus().getCurrentFloor())));
            System.out.println("New size of destinations is " + this.elevator.getDestinations().size());
    
            if (this.elevator.getUpcomingDirection() != null){
                this.elevator.getElevatorStatus().setMotorDirection(this.elevator.getUpcomingDirection());
                this.elevator.setUpcomingDirection(null);
            }
            
            System.out.println("Motor Direction after contains is " + MotorDirection.toString(this.elevator.getElevatorStatus().getMotorDirection()));
            this.elevator.sortDestinations();
            this.elevator.getFloorInputButtons().remove(this.elevator.getElevatorStatus().getCurrentFloor());
        }

        return this.elevator.getElevatorDoorCloseState();
    }
    
}
