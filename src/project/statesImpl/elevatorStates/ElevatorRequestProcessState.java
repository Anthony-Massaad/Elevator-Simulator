package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.MotorDirection;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.MoveToMessage;
import project.statesImpl.State;

/**
 * Elevator sttate for processing message
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorRequestProcessState extends State{
    private Elevator elevator; 

	/**
	 * Constructor for elevator request process 
	 * @param elevator Elevator, 
	 */
    public ElevatorRequestProcessState(Elevator elevator){
		super();
        this.elevator = elevator;
    }
    
	/**
	 * Handle current state
	 * @return State, the current state
	 */
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
			
        	if (this.elevator.getElevatorStatus().getMotorDirection() != MotorDirection.IDLE) {
        		// add to queue if button isn't already pressed
				// we don't need to set the upcoming directionn for the elevator here as the elevator is already moving in said 
				// direction by default. We assume the scheduler picked the correct elevator 
				if (this.elevator.getDestinations().contains(destination)){
					this.elevator.addUpcomingButtons(destination, moveToMessage.getButtonsToBePressed());
				}else{
					int tpm;
					if (destination < this.elevator.getElevatorStatus().getNextDestination() && this.elevator.getElevatorStatus().getMotorDirection() == MotorDirection.UP){
						// condition for when the elevator is not at the destination floor already and is moving to the destination floor for up direction
						tpm = this.elevator.getElevatorStatus().getNextDestination();
						this.elevator.getElevatorStatus().setNextDestination(destination);
						this.elevator.getDestinations().add(tpm);
					}else if (this.elevator.getElevatorStatus().getNextDestination() == this.elevator.getElevatorStatus().getCurrentFloor() && destination < this.elevator.getDestinations().get(0) && this.elevator.getElevatorStatus().getMotorDirection() == MotorDirection.UP){
						// condition for when the elevator is at the destination floor, so we want to check the NEXT destination for up direction
						this.elevator.getElevatorStatus().setNextDestination(destination);
					}else if (destination > this.elevator.getElevatorStatus().getNextDestination() && this.elevator.getElevatorStatus().getMotorDirection() == MotorDirection.DOWN){
						// condition for when the elevator is not at the destination floor already and is moving to the destination floor for down direction
						tpm = this.elevator.getElevatorStatus().getNextDestination();
						this.elevator.getElevatorStatus().setNextDestination(destination);
						this.elevator.getDestinations().add(tpm);
					}else if (this.elevator.getElevatorStatus().getNextDestination() == this.elevator.getElevatorStatus().getCurrentFloor() && destination > this.elevator.getDestinations().get(0) && this.elevator.getElevatorStatus().getMotorDirection() == MotorDirection.DOWN){
						// condition for when the elevator is at the destination floor, so we want to check the NEXT destination for down direction
						this.elevator.getElevatorStatus().setNextDestination(destination);
					}
					// sort
					this.elevator.getDestinations().add(destination);
                    this.elevator.sortDestinations();
                    this.elevator.addUpcomingButtons(destination, moveToMessage.getButtonsToBePressed());
                }
				this.elevator.setUpcomingDirection(dir); // ?
                returningState = this.elevator.getElevatorMovingState();
        	}else if (this.elevator.getElevatorStatus().getCurrentFloor() == destination) {
        		// already at the requested floor
				this.elevator.setUpcomingDirection(dir);
				this.elevator.addUpcomingButtons(destination, moveToMessage.getButtonsToBePressed());
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
    
	@Override
	public String toString(){
		return "Processing";
	}

}
