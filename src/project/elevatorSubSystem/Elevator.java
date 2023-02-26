package project.elevatorSubSystem;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.ElevatorState;
import project.constants.ElevatorTimes;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.MoveToMessage;
import project.simulationParser.Parser;

/**
 * Class Elevator that implements the Runnable class
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Elevator implements Runnable{
	private boolean isDead;
	private String systemName; 
	private ConcurrentLinkedDeque<Message> responses; // TEMP FOR NOW.....GOTTA FIGURE OUT LAMBDA BULLSHIT 
	private ConcurrentLinkedDeque<Message> requests;
	private int currentFloor; 
	private int destinationFloor; 
	private ElevatorState state; 
	
	/**
	 * Constructor for the Elevator class
	 * @param parser, Parser of the system
	 * @param messageQueue, MessageQueue object for creating a message queue.
	 * @param systemName, the name of the system
	 */
    public Elevator(String systemName, ConcurrentLinkedDeque<Message> responses, int currentFloor){
    	this.isDead = false;
        this.systemName = systemName; 
        this.responses = responses; 
        this.currentFloor = currentFloor;
        this.destinationFloor = 0; 
        this.state = ElevatorState.IDLE;
        this.requests = new ConcurrentLinkedDeque<>();
    }
    
    /**
     * Elevator Subsystem will use this method to add requests to the elevator
     * @param message Message, the message
     */
    public void addRequest(Message message) {
    	this.requests.add(message);
    }
    	/**
	 * Getter method to get the boolean value if the elevator system is dead or not.
	 * @return Boolean, true or false.
	 */
	public Boolean getIsDead() {
		return this.isDead;
	}

	/**
	 * Getter method to get the name of the elevator system.
	 * @return
	 */
	public String getSystemName() {
		return this.systemName;
	}

	/**
	 * Getter method to receive the message responses.
	 * @return ConcurrentLinkedDeque, response messages.
	 */
	public ConcurrentLinkedDeque<Message> getResponses() {
		return this.responses;
	}

	/**
	 * Getter method to get the current floor of the elevator.
	 * @return Integer, floor number.
	 */
	public Integer getCurrentFloor() {
		return this.currentFloor;
	}

	/**
	 * Getter method to get the destination floor number.
	 * @return Integer, floor number.
	 */
	public Integer getDestinationFloor() {
		return this.destinationFloor;
	}

	/**
	 * Getter method to get the state of the elevator. 
	 * @return Elevator State, enum of the state.
	 */
	public ElevatorState getElevatorState() {
		return this.state;
	}
    
	/**
	 * Getter method to receive the message requests.
	 * @return ConcurrentLinkedDeque, requests.
	 */
	public ConcurrentLinkedDeque<Message> getRequests() {
		return this.requests;
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

    		if (destination == this.currentFloor) {
    			// the request for the floor is on the current floor 
    			this.state = ElevatorState.OPEN_DOOR;
    		}else {
            	Log.notification("ELEVATOR", moveToMessage.toString(), new Date(), this.systemName);
    			this.destinationFloor = destination; 
    			this.state = ElevatorState.MOVING;
    		}
    	}
    }
    
    /**
     * method handler for when the elevator is in a open door state
     * @throws InterruptedException
     */
    private void handleOpenDoor() throws InterruptedException {
    	Log.notification("ELEVATOR", "Open Door", new Date(), this.systemName);
        Thread.sleep(ElevatorTimes.OPEN_DOOR.getTime());
        // Load/unload passengers and transition to close
    }
    
    /**
     * method handler for when the elevator is in a close door state
     * @throws InterruptedException
     */
    private void handleCloseDoor() throws InterruptedException {
    	Log.notification("ELEVATOR", "Closing Door", new Date(), this.systemName);
        Thread.sleep(ElevatorTimes.CLOSING_DOOR.getTime());
        // if buttons were pressed, then start moving. Otherwise transition to idle
        this.state = ElevatorState.MOVING;
    }
    
    /**
     * method handler for when the elevator is in a moving state
     * @throws InterruptedException
     */
    private void handleMoving() throws InterruptedException {
    	Log.notification("ELEVATOR", "Current floor " + this.currentFloor, new Date(), this.systemName);
    	if (this.currentFloor < this.destinationFloor) {
            this.currentFloor++;
    	}else if (this.currentFloor > this.destinationFloor) {
            this.currentFloor--;
    	}
    	
        Thread.sleep(ElevatorTimes.MOVING.getTime());
    	Log.notification("ELEVATOR", "Reached floor " + this.currentFloor, new Date(), this.systemName);
        if (this.destinationFloor == this.currentFloor) {
        	// when we reach the destination floor 
        	ArrivalMessage arrivalMessage = new ArrivalMessage(new Date(), this.destinationFloor);
        	Log.notification("ELEVATOR", arrivalMessage.toString(), new Date(), this.systemName);
        	this.responses.addFirst(arrivalMessage);
        	this.state = ElevatorState.IDLE;
        }
    }

    /**
     * Overriden run method as part of Runnable
     */
    @Override
    public void run() {
    	try {
    		while(!this.isDead) {
    			if (Parser.isEmpty()) {
            		this.isDead = false; 
                	Log.notification("ELEVATOR", "Elevator Killed", new Date(), this.systemName);
            		break; 
            	}
    			
    			if (this.requests.size() >= 0) {
    				this.checkMessage(); 
    			}
    			
    			if (this.state == ElevatorState.IDLE) {
                    // checks with the scheduler using the message queue system
                    // to see if it can do something.
                    Thread.sleep(500);
                }
                else if (this.state == ElevatorState.OPEN_DOOR){
                	this.handleOpenDoor();
                }else if (this.state == ElevatorState.CLOSE_DOOR){
                	this.handleCloseDoor();
                }
                else if (this.state == ElevatorState.MOVING){
                	this.handleMoving();
                }
			}		
    	}catch(Exception e) {
    		//Log.error(this.systemName, "Something Broke");
    	}
    }
}
