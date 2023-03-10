package project.elevatorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.ElevatorState;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.constants.Time;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.MoveToMessage;
import project.messageSystem.messages.UpdatePositionMessage;

// UPDATE POSITION MESSAGE!!! LOAD AND UNLOAD PASSENGERS IN THE OPENDOOR BULLSHIT 

public class Elevator implements Runnable{
	
	private ConcurrentLinkedDeque<Message> responses; 
	private ConcurrentLinkedDeque<Message> requests;
	private ArrayList<Integer> destinations;  // elevator buttons, technically passengers so .size is the number of passengers
	private boolean[] lamps; // respective to the destinations value - 1. It is the buttons lamps light
	private String systemName; 
	private ElevatorStatus elevatorStatus; 
	private ElevatorState state; 
	private int id; 
	
	public Elevator(int id, String systemName, ConcurrentLinkedDeque<Message> responses) {
		this.responses = responses; 
		this.requests = new ConcurrentLinkedDeque<>();
		// responsible for the basic statuses shared with the scheduler 
		// new Random().nextInt(SimulationConstants.NUM_OF_FLOORS) + 1 = (0-21) + 1 = (1-22)
		this.destinations = new ArrayList<>(); 
		elevatorStatus = new ElevatorStatus(this.destinations.size(), 0, new Random().nextInt(SimulationConstants.NUM_OF_FLOORS) + 1, MotorDirection.IDLE);
		this.state = ElevatorState.IDLE; 
		this.lamps = new boolean[SimulationConstants.NUM_OF_FLOORS];
		this.systemName = systemName; 
		this.id = id; 
		this.sendUpdateStatus();
	}

	public ElevatorStatus getElevatorStatus(){
		return this.elevatorStatus;
	}

	public void addRequest(Message msg){
		this.requests.add(msg);
	}
	
	private void sortDestinations() {
		if (this.elevatorStatus.getMotorDirection() == MotorDirection.UP) {
			Collections.sort(this.destinations);
		}else if (this.elevatorStatus.getMotorDirection() == MotorDirection.DOWN){
			Collections.reverse(this.destinations);
		}else {
			// error lol 
        	Log.notification("ELEVATOR","Sorting Error", new Date(), this.systemName);
		}
	}

	private void updateMotorStatus(){
		this.elevatorStatus.setMotorDirection(this.elevatorStatus.getNextDestination() > this.elevatorStatus.getCurrentFloor() ? MotorDirection.UP : MotorDirection.DOWN);
	}

	private void sendUpdateStatus(){
		UpdatePositionMessage updatePositionMessage = new UpdatePositionMessage(new Date(), this.id, this.elevatorStatus.getNumberOfPassengers(), this.elevatorStatus.getNextDestination(), this.elevatorStatus.getCurrentFloor(), this.elevatorStatus.getMotorDirection());
		Log.notification("ELEVATOR", updatePositionMessage.toString(), new Date(), this.systemName);
		this.responses.addFirst(updatePositionMessage);
	}
	
	/**
     * Check the requested message sent from the elevator Subsystem.
     * Elevator will change state accordingly depending on the message
     */
    private void checkMessage() {
    	Message message = this.requests.poll();
    	
    	if (message instanceof MoveToMessage) {
    		MoveToMessage moveToMessage = (MoveToMessage) message; 
    		int destination = moveToMessage.getDestinationFloor();
        	Log.notification("ELEVATOR", "Received move to request to floor " + destination, new Date(), this.systemName);
        	// For this iteraion, it's just moving! 
        	Log.notification("ELEVATOR", moveToMessage.toString(), new Date(), this.systemName);
			
        	if (this.elevatorStatus.getCurrentFloor() == destination) {
        		// already at the requested floor
        		this.state = ElevatorState.OPEN_DOOR;
        	}else if (this.state == ElevatorState.MOVING) {
        		// add to queue if button isn't already pressed
				if (this.destinations.contains(destination)){
					return; 
				}
        		this.destinations.add(destination);
				this.lamps[destination - 1] = true; 
				Log.notification("ELEVATOR", "Lamp " + destination + " turned on", new Date(), this.systemName);
        		this.sortDestinations();
        		// sort 
        	}else {
        		// start moving the elevator and change motor direction
        		this.elevatorStatus.setNextDestination(destination);
				this.updateMotorStatus();
				this.sendUpdateStatus();
    			this.state = ElevatorState.MOVING;
        	}
    	}
    }
    
    private void handleOpenDoor() throws InterruptedException {
		Log.notification("ELEVATOR", "Open Door", new Date(), this.systemName);
        Thread.sleep(Time.OPEN_DOOR.getTime());
        // Load/unload passengers add to the buttons arraylist then close doors
        this.state = ElevatorState.CLOSE_DOOR;
    }
    
    private void handleCloseDoor() throws InterruptedException {
    	Log.notification("ELEVATOR", "Closing Door", new Date(), this.systemName);
        Thread.sleep(Time.CLOSE_DOOR.getTime());
        // if buttons were pressed, then start moving. Otherwise transition to idle
        if (this.destinations.size() > 0) {
			this.elevatorStatus.setNextDestination(this.destinations.get(0));
        	this.destinations.remove(0);
			this.updateMotorStatus();
        	this.state = ElevatorState.MOVING;
        }else {
			this.elevatorStatus.setMotorDirection(MotorDirection.IDLE);
        	this.state = ElevatorState.IDLE;
        }
		this.sendUpdateStatus();
    }
    
    private void handleMoving() throws InterruptedException {
    	Log.notification("ELEVATOR", "Current floor " + this.elevatorStatus.getCurrentFloor(), new Date(), this.systemName);
    	
    	if (this.elevatorStatus.getCurrentFloor() < this.elevatorStatus.getNextDestination()) {
			this.elevatorStatus.setCurrentFloor(this.elevatorStatus.getCurrentFloor() + 1);
    	}else if (this.elevatorStatus.getCurrentFloor() > this.elevatorStatus.getNextDestination()) {
			this.elevatorStatus.setCurrentFloor(this.elevatorStatus.getCurrentFloor() - 1);
    	}
        Thread.sleep(Time.MOVE.getTime());
    	Log.notification("ELEVATOR", "Reached floor " + this.elevatorStatus.getCurrentFloor(), new Date(), this.systemName);
    	// update the scheduler through the ElevatorSubsystem
    	this.sendUpdateStatus();
        if (this.elevatorStatus.getNextDestination() == this.elevatorStatus.getCurrentFloor()) {
        	// when we reach the destination floor 
			this.destinations.remove(0);
			this.lamps[this.elevatorStatus.getNextDestination() - 1] = false; 
        	ArrivalMessage arrivalMessage = new ArrivalMessage(new Date(), this.elevatorStatus.getNextDestination(), this.elevatorStatus.getMotorDirection());
        	Log.notification("ELEVATOR", arrivalMessage.toString(), new Date(), this.systemName);
			Log.notification("ELEVATOR", "Lamp " + this.elevatorStatus.getNextDestination() + " off", new Date(), this.systemName);
        	this.responses.addFirst(arrivalMessage);
        	this.state = ElevatorState.OPEN_DOOR;
        }
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (this.state != ElevatorState.ERROR) {
			try {
				if (this.requests.size() >= 0) {
					this.checkMessage(); 
				}
				if (this.state == ElevatorState.IDLE) {
	                // checks with the scheduler using the message queue system
	                // to see if it can do something.
	                Thread.sleep(500);
	            }
	            if (this.state == ElevatorState.OPEN_DOOR){
	            	this.handleOpenDoor();
	            }
				
				if (this.state == ElevatorState.CLOSE_DOOR){
	            	this.handleCloseDoor();
	            }
	            
				if (this.state == ElevatorState.MOVING){
	            	this.handleMoving();
	            }
			}catch (InterruptedException e) {
				// tthrow error
			}
			
			
		}
	}

}
