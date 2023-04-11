package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.constants.Time;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.messageSystem.messages.ArrivalMessage;
import project.statesImpl.State;

/**
 * Open door statte
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorOpenDoorState extends State{
    private Elevator elevator; 

    /** 
     * Constructor for open door state
     * @param elevator Elevator, the elevator which is the super state
     */
    public ElevatorOpenDoorState(Elevator elevator){
        super();
        this.elevator = elevator;
    }

    /**
     * Handle current state
     * @return State, new state
     */
    @Override
    public State handleState() {
        this.elevator.sendUpdateStatus();

        if (this.elevator.getUpcomingDirection() != null && this.elevator.getDestinations().size() == 0){
            // elevator was there to begin with! 
            ArrivalMessage arrivalMessage = new ArrivalMessage(new Date(), this.elevator.getElevatorStatus().getCurrentFloor(), this.elevator.getUpcomingDirection());
            this.elevator.getResponses().addFirst(arrivalMessage);
        }
        
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
    
    @Override
    public String toString(){
        return "Open Door";
    }

}
