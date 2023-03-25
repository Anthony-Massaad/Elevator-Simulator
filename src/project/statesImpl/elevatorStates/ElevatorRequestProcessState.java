package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.MotorDirection;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.MoveToMessage;
import project.statesImpl.State;

public class ElevatorRequestProcessState extends State{
    private Elevator elevator; 

    public ElevatorRequestProcessState(Elevator elevator){
		super();
        this.elevator = elevator;
    }
    
    @Override
    public State handleState() {
        State returningState = null; 
        Message message = this.elevator.getRequests().poll();
		
    	if (message instanceof MoveToMessage) {
    		MoveToMessage moveToMessage = (MoveToMessage) message; 
    		int destination = moveToMessage.getDestinationFloor();
			MotorDirection dir = moveToMessage.getDirection();
        	// Log.notification("ELEVATOR", "Received move to request to floor " + destination, new Date(), this.systemName);
        	// For this iteraion, it's just moving! 
        	Log.notification("ELEVATOR", moveToMessage.toString(), new Date(), this.elevator.getSystemName());
			
        	if (this.elevator.getCurrentState() instanceof ElevatorMovingState) {
        		// add to queue if button isn't already pressed
				// we don't need to set the upcoming directionn for the elevator here as the elevator is already moving in said 
				// direction by default. We assume the scheduler picked the correct elevator 
				if (this.elevator.getDestinations().contains(destination)){
					this.elevator.addUpcomingButtons(destination, moveToMessage.getButtonsToBePressed());
				}else{
                    // sort 
                    this.elevator.getDestinations().add(destination);
                    this.elevator.sortDestinations();
                    this.elevator.addUpcomingButtons(destination, moveToMessage.getButtonsToBePressed());
                }
                returningState = this.elevator.getElevatorMovingState();
        	}else if (this.elevator.getElevatorStatus().getCurrentFloor() == destination) {
        		// already at the requested floor
				this.elevator.setUpcomingDirection(dir);
                returningState = this.elevator.getElevatorDoorOpenState();
			}
			else {
        		// start moving the elevator and change motor direction
        		this.elevator.getElevatorStatus().setNextDestination(destination);
				this.elevator.getDestinations().add(destination);
				this.elevator.addUpcomingButtons(destination, moveToMessage.getButtonsToBePressed());
				this.elevator.updateMotorStatus();
				this.elevator.sendUpdateStatus();
				this.elevator.setUpcomingDirection(dir);
                returningState = this.elevator.getElevatorMovingState();
        	}
    	}

        if (returningState == null){
            returningState = this.elevator.getCurrentState();
        }
        
		return returningState;
    }
    
}
