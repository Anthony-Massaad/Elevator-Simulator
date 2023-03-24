package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.MotorDirection;
import project.constants.Time;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.messageSystem.messages.ArrivalMessage;
import project.statesImpl.State;

public class ElevatorMovingState extends State{

    private Elevator elevator;
    public ElevatorMovingState(Elevator elevator){
        this.elevator = elevator;
    }

    @Override
    public State handleState() {
        State returningState = null; 
        Log.notification("ELEVATOR", "Current floor " + this.elevator.getElevatorStatus().getCurrentFloor(), new Date(), this.elevator.getSystemName());
    	
        if (this.elevator.getElevatorStatus().getCurrentFloor() < this.elevator.getElevatorStatus().getNextDestination()) {
			this.elevator.getElevatorStatus().setCurrentFloor(this.elevator.getElevatorStatus().getCurrentFloor() + 1);
    	}else if (this.elevator.getElevatorStatus().getCurrentFloor() > this.elevator.getElevatorStatus().getNextDestination()) {
			this.elevator.getElevatorStatus().setCurrentFloor(this.elevator.getElevatorStatus().getCurrentFloor() - 1);
    	}

        this.elevator.sleep(Time.MOVE.getTime());
    	Log.notification("ELEVATOR", "Reached floor " + this.elevator.getElevatorStatus().getCurrentFloor(), new Date(), this.elevator.getSystemName());
    	// update the scheduler through the ElevatorSubsystem
    	this.elevator.sendUpdateStatus();
        if (this.elevator.getElevatorStatus().getNextDestination() == this.elevator.getElevatorStatus().getCurrentFloor()) {
        	// when we reach the destination floor 
			this.elevator.getDestinations().remove(0);
            this.elevator.getLamps()[this.elevator.getElevatorStatus().getNextDestination() - 1] = false; 
            Log.notification("ELEVATOR", "Button Lamp " + (this.elevator.getElevatorStatus().getNextDestination())  + " is off", new Date(), this.elevator.getSystemName());
            ArrivalMessage arrivalMessage;
			if (this.elevator.getDestinations().size() == 0){
				arrivalMessage = new ArrivalMessage(new Date(), this.elevator.getElevatorStatus().getNextDestination(), MotorDirection.oppositeDirection(this.elevator.getElevatorStatus().getMotorDirection()));
			}else{
				arrivalMessage = new ArrivalMessage(new Date(), this.elevator.getElevatorStatus().getNextDestination(), this.elevator.getElevatorStatus().getMotorDirection());
			}
        	Log.notification("ELEVATOR", arrivalMessage.toString(), new Date(), this.elevator.getSystemName());
			Log.notification("ELEVATOR", "Lamp " + this.elevator.getElevatorStatus().getNextDestination() + " off", new Date(), this.elevator.getSystemName());
        	this.elevator.getResponses().addFirst(arrivalMessage);
            returningState = this.elevator.getElevatorDoorOpenState();
        }

        if (returningState == null){
            returningState = this.elevator.getElevatorMovingState();
        }
        
        return returningState; 

    } 

}
