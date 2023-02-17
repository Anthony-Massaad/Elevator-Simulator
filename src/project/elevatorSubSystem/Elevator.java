package project.elevatorSubSystem;

import project.logger.Log;
import project.messageSystem.MessageQueue;
import project.simulationParser.Parser;

/**
 * Class Elevator that implements the Runnable class
 * @author Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Elevator implements Runnable{
	private boolean isDead;
	private Parser parser;
	private MessageQueue messageQueue;
	private String systemName; 


	/**
	 * Constructor for the Elevator class
	 * @param parser, Parser of the system
	 * @param messageQueue, MessageQueue object for creating a message queue.
	 * @param systemName, the name of the system
	 */
    public Elevator(Parser parser, MessageQueue messageQueue, String systemName){
    	this.isDead = false;
        this.messageQueue = messageQueue;
        this.parser = parser; 
        this.systemName = systemName; 
    }


    /**
     * Overriden run method as part of Runnable
     */
    @Override
    public void run() {
    	try {
    		while(!this.isDead) {
    			synchronized (this.messageQueue) {
    				if (this.parser.isEmpty()) {
    					this.isDead = true;
    					break;
    				}
    				
					while(messageQueue.outputElevatorReceiver == null) {
						//Log.info("Elevator is waiting for a request");
						messageQueue.wait();
					}
					Log.info(this.systemName, "Request received from floor -> " + messageQueue.outputElevatorReceiver);
					messageQueue.inputElevatorRequest = messageQueue.outputElevatorReceiver;
					messageQueue.outputElevatorReceiver = null;
					messageQueue.notifyAll();
				}
    			
    		}
    	}catch(Exception e) {
    		Log.error(this.systemName, "Something Broke");
    	}
    }
}
