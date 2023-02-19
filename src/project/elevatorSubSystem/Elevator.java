package project.elevatorSubSystem;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.ElevatorState;
import project.constants.ElevatorTimes;
import project.logger.Log;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.Message;
import project.messageSystem.MessageQueue;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.MoveToMessage;
import project.simulationParser.Parser;

/**
 * Class Elevator that implements the Runnable class
 * @author Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Elevator implements Runnable{
	private boolean isDead;
	private String systemName; 
	private ConcurrentLinkedDeque<Message> responses; // TEMP FOR NOW.....GOTTA FIGURE OUT LAMBDA BULLSHIT 
	private ConcurrentLinkedDeque<Message> requests;
	private int currentFloor; 
	private int desitnationFloor; 
	private ElevatorState state; 
	
	/**
	 * Constructor for the Elevator class
	 * @param parser, Parser of the system
	 * @param messageQueue, MessageQueue object for creating a message queue.
	 * @param systemName, the name of the system
	 */
    public Elevator(String systemName, ConcurrentLinkedDeque<Message> responses){
    	this.isDead = false;
        this.systemName = systemName; 
        this.responses = responses; 
        this.currentFloor = 3;
        this.desitnationFloor = 0; 
        this.state = ElevatorState.IDLE;
        this.requests = new ConcurrentLinkedDeque<>();
    }
    
    public void addRequest(Message message) {
    	this.requests.add(message);
    }
    
    private void checkMessage() {
    	Message message = this.requests.poll();
    	
    	if (message instanceof MoveToMessage) {
    		MoveToMessage moveToMessage = (MoveToMessage) message; 
    		int destination = moveToMessage.getDestinationFloor();
        	Log.notification("ELEVATOR", "Received move to request to " + destination, new Date(), this.systemName);

    		if (destination == this.currentFloor) {
    			// the request for the floor is on the current floor 
    			this.state = ElevatorState.OPEN_DOOR;
    		}else {
            	Log.notification("ELEVATOR", moveToMessage.toString(), new Date(), this.systemName);
    			this.desitnationFloor = destination; 
    			this.state = ElevatorState.MOVING;
    		}
    	}
    }

    /**
     * Overriden run method as part of Runnable
     */
    @Override
    public void run() {
    	try {
    		while(!this.isDead) {
    			if (this.requests.size() <= 0) {
    				// later on also conditions the queue of the elevator
    				// since we are assuming one elevator and one floor we can just check if the request is 
    				// empty
    				this.state = ElevatorState.IDLE;
    			}else{
    				this.checkMessage(); 
    			}
    			
    			if (this.state == ElevatorState.IDLE) {
                    // checks with the scheduler using the message queue system
                    // to see if it can do something.
                    Thread.sleep(500);
                }
                else if (this.state == ElevatorState.OPEN_DOOR){
                	Log.notification("ELEVATOR", "Open Door", new Date(), this.systemName);
                    Thread.sleep(ElevatorTimes.OPEN_DOOR.getTime());
                    // Load/unload passengers and transition to close
                }else if (this.state == ElevatorState.CLOSE_DOOR){
                	Log.notification("ELEVATOR", "Closing Door", new Date(), this.systemName);
                    Thread.sleep(ElevatorTimes.CLOSING_DOOR.getTime());
                    // if buttons were pressed, then start moving. Otherwise transition to idle
                    this.state = ElevatorState.MOVING;
                }
                else if (this.state == ElevatorState.MOVING){
                	if (this.currentFloor < this.desitnationFloor) {
                        this.currentFloor++;
                	}else if (this.currentFloor > this.desitnationFloor) {
                        this.currentFloor--;
                	}
                    Thread.sleep(ElevatorTimes.MOVING.getTime());
                	Log.notification("ELEVATOR", "Reached floor " + this.currentFloor, new Date(), this.systemName);
                    if (this.desitnationFloor == this.currentFloor) {
                    	// when we reach the destination floor 
                    	ArrivalMessage arrivalMessage = new ArrivalMessage(new Date(), this.desitnationFloor);
                    	Log.notification("ELEVATOR", arrivalMessage.toString(), new Date(), this.systemName);
                    	this.responses.addFirst(arrivalMessage);
                    }   
                }
			}		
    	}catch(Exception e) {
    		//Log.error(this.systemName, "Something Broke");
    	}
    }
}
