package project.statesImpl.elevatorStates;

import java.util.Date;

import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.constants.Time;
import project.elevatorImpl.Elevator;
import project.logger.Log;
import project.messageSystem.messages.ArrivalMessage;
import project.statesImpl.State;
import project.Timing.Timing;

/**
 * Elevator moving state
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorMovingState extends State{

    private Elevator elevator;

    /**
     * constructor for the elevator moving state
     * @param elevator Elevator, the elevator which is the super state
     */
    public ElevatorMovingState(Elevator elevator){
        super();
        this.elevator = elevator;
    }

    /**
     * Handle the current state
     * @return State, the new state
     */
    @Override
    public State handleState() {
        State returningState = null; 
        Log.notification("ELEVATOR", "Current floor " + this.elevator.getElevatorStatus().getCurrentFloor(), new Date(), this.elevator.getSystemName());
        Log.notification("ELEVATOR", "Moving now", new Date(), this.elevator.getSystemName());
        this.elevator.sendUpdateStatus();
        // activate the timer depending on input
        Timing timer; 
        if (this.elevator.getElevatorStatus().getNextDestination() == SimulationConstants.ELEVATOR_BROKEN_FAULT_INPUT){
            // input is fault, timer takes longer
            timer = new Timing(this, Time.MOVE.getTime() + 4, 1);
        }else{
            // input is normal, timer is correc
            timer = new Timing(this, Time.MOVE.getTime(), 1);
        }
        Thread timerThread = new Thread(timer);
        timerThread.start();
        // wait until notified by the timer
        synchronized(this){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // fault was active, retturn the broken elevator state
        if (this.isDieCalled){
            return this.elevator.getElevatorBrokenState();
        }
        this.elevator.setPreviousFloor(this.elevator.getElevatorStatus().getCurrentFloor());
        // move elevaor
        if (this.elevator.getElevatorStatus().getCurrentFloor() < this.elevator.getElevatorStatus().getNextDestination()) {
			this.elevator.getElevatorStatus().setCurrentFloor(this.elevator.getElevatorStatus().getCurrentFloor() + 1);
    	}else if (this.elevator.getElevatorStatus().getCurrentFloor() > this.elevator.getElevatorStatus().getNextDestination()) {
			this.elevator.getElevatorStatus().setCurrentFloor(this.elevator.getElevatorStatus().getCurrentFloor() - 1);
    	}
        
    	Log.notification("ELEVATOR", "Reached floor " + this.elevator.getElevatorStatus().getCurrentFloor(), new Date(), this.elevator.getSystemName());
    	// update the scheduler through the ElevatorSubsystem
        this.elevator.sendUpdateMoveStatus();
    	this.elevator.sendUpdateStatus();
        if (this.elevator.getElevatorStatus().getNextDestination() == this.elevator.getElevatorStatus().getCurrentFloor()) {
        	// when we reach the destination floor 
			this.elevator.getDestinations().remove(0);
            this.elevator.getLamps()[this.elevator.getElevatorStatus().getNextDestination() - 1] = false; 
            Log.notification("ELEVATOR", "Button Lamp " + (this.elevator.getElevatorStatus().getNextDestination())  + " is off", new Date(), this.elevator.getSystemName());
            ArrivalMessage arrivalMessage;
			if (this.elevator.getDestinations().size() == 0 && this.elevator.getUpcomingDirection() != this.elevator.getElevatorStatus().getMotorDirection()){
				arrivalMessage = new ArrivalMessage(new Date(), this.elevator.getElevatorStatus().getNextDestination(), MotorDirection.oppositeDirection(this.elevator.getElevatorStatus().getMotorDirection()));
			}else{
				arrivalMessage = new ArrivalMessage(new Date(), this.elevator.getElevatorStatus().getNextDestination(), this.elevator.getElevatorStatus().getMotorDirection());
			}
        	Log.notification("ELEVATOR", arrivalMessage.toString(), new Date(), this.elevator.getSystemName());
			Log.notification("ELEVATOR", "Lamp " + this.elevator.getElevatorStatus().getNextDestination() + " off", new Date(), this.elevator.getSystemName());
        	this.elevator.getResponses().addFirst(arrivalMessage);
            returningState = this.elevator.getElevatorDoorOpenState();
            this.elevator.sendUpdateStatus();
        }

        if (returningState == null){
            returningState = this.elevator.getElevatorMovingState();
        }
        
        return returningState; 

    } 

    @Override
    public String toString(){
        return "Moving";
    }

}
