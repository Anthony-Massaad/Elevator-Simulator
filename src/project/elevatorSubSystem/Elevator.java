package project.elevatorSubSystem;

import project.constants.ElevatorStates;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.MessageQueue;
import project.simulationParser.Parser;
import project.messageSystem.messages.*;

/**
 * Class Elevator that implements the Runnable class
 * @author Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Elevator implements Runnable{
	private boolean isDead;
	private String systemName; 
	private ElevatorStates state;
	private ElevatorCommunication elevatorCommunication; 
	private int destinationFloor;
	private int currentFloor; 
	/**
	 * Constructor for the Elevator class
	 * @param parser, Parser of the system
	 * @param messageQueue, MessageQueue object for creating a message queue.
	 * @param systemName, the name of the system
	 */
    public Elevator(String systemName, ElevatorCommunication elevatorCommunication){
    	this.isDead = false;
        this.systemName = systemName; 
        this.state = ElevatorStates.IDLE; 
        this.currentFloor = 4; 
        this.destinationFloor = 0; 
        this.elevatorCommunication = elevatorCommunication; 
    }
    
    public void giveMessage(Message message) { 
    	if (message instanceof MoveToMessage) {
    		// Assume idle is its default state. We are not condition checking if the 
    		// elevator is already moving
    		MoveToMessage castedMessage = (MoveToMessage) message; 
    		this.destinationFloor = castedMessage.getDestinationFloor();
    		this.state = ElevatorStates.MOVING;
    	}
    }

    /**
     * Overriden run method as part of Runnable
     */
    @Override
    public void run() {
    	try {
    		while (!this.isDead) {
    			synchronized(this.elevatorCommunication) {
    				while (this.state == ElevatorStates.IDLE) {
    					this.elevatorCommunication.wait();
    				}
    				
    				
    				
    				
    			}
    		}
    	}catch (Exception e) {
    		
    	}
    }
}
