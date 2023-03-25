package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.MotorDirection;
import project.constants.Time;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.messageSystem.messages.ElevatorLeavingMessage;
import project.statesImpl.State;

public class ElevatorCloseDoorState extends State{

    private Elevator elevator; 
    public ElevatorCloseDoorState(Elevator elevator){
        super();
        this.elevator = elevator;
    }

    @Override
    public State handleState() {
        Log.notification("ELEVATOR", "Closing Door", new Date(), this.elevator.getSystemName());
        this.elevator.sleep(Time.CLOSE_DOOR.getTime());
        State returningState; 
        
        // if buttons were pressed, then start moving. Otherwise transition to idle
        if (this.elevator.getDestinations().size() > 0) {
            if (this.elevator.getDestinations().get(0) == 0){
                // door is stuck to close if the value is 0
                returningState = this.elevator.getElevatorDoorFaultState();
            }else{
                Log.notification("ELEVATOR", "Doors Closed", new Date(), this.elevator.getSystemName());
                this.elevator.getElevatorStatus().setNextDestination(this.elevator.getDestinations().get(0));
                // this.elevator.getDestinations().remove(0);
                this.elevator.updateMotorStatus();
                this.elevator.getResponses().add(new ElevatorLeavingMessage(new Date(), this.elevator.getElevatorStatus().getCurrentFloor(), this.elevator.getElevatorStatus().getMotorDirection()));
                returningState = this.elevator.getElevatorMovingState();
            }
        }else {
			this.elevator.getElevatorStatus().setMotorDirection(MotorDirection.IDLE);
			Log.notification("ELEVATOR", "Motor Direction is " + MotorDirection.toString(this.elevator.getElevatorStatus().getMotorDirection()), new Date(), this.elevator.getSystemName());
        	returningState = this.elevator.getElevatorIdleState();
            this.elevator.setDirectionLampUp(false);
            this.elevator.setDirectionLampDown(false);
			Log.notification("ELEVATOR", "up direction lamp set to " + this.elevator.getDirectionLampUp(), new Date(), this.elevator.getSystemName());
			Log.notification("ELEVATOR", "down direction lamp set to " + this.elevator.getDirectionLampDown(), new Date(), this.elevator.getSystemName());
            this.elevator.getResponses().add(new ElevatorLeavingMessage(new Date(), this.elevator.getElevatorStatus().getCurrentFloor(), this.elevator.getElevatorStatus().getMotorDirection()));
        }

		this.elevator.sendUpdateStatus();
        return returningState; 
    }
}
