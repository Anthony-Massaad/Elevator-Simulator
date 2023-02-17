package project.scheduler;

import project.logger.Log;
import project.messageSystem.MessageQueue;
import project.simulationParser.Parser; 
import project.constants.SchedulerStates; 

/**
 * Class Scheduler that implements the Runnable class for the purpose of creating a thread. Acts as a communication channel for the Floor and the Elevator systems using a MessageQueue.
 * @author Dorothy Tran, Max Curkovic SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Scheduler implements Runnable {

    private boolean isDead;
    private MessageQueue messageQueue;
    private Parser parser; 
    private String systemName; 
    private SchedulerStates state; 
    
    /**
     * Construct for the Scheduler class.
     * @param isDead Boolean variable for determining if there is a connection active.
     * @param An messageQueue object for creating a message queue.
     */
    public Scheduler(Parser parser, MessageQueue messageQueue, String systemName){
        this.isDead = false;
        this.messageQueue = messageQueue;
        this.parser = parser; 
        this.systemName = systemName; 
    }
    
    @Override
    /**
     * Overriden run method as part of Runnable. Checks to see whether a MessageQueue can be received 
     * or sent by the Floor or the Elevator to the other, through the use of the Scheduler. 
     * Notifies when this has occurred.
     */
    public void run() {
    	try {
    		while(!this.isDead) {
    			synchronized (this.messageQueue) {
    				
    				if (this.parser.isEmpty()) {
    					this.isDead = true; 
    					break; 
    				}
    				
    				while (this.messageQueue.inputElevatorRequest == null && this.messageQueue.inputFloorRequest == null) {
        				//Log.info("The Scheduler is waiting for the Floor and the Elevator...");
    					this.messageQueue.wait();
    				}
    				
    				if (this.messageQueue.inputFloorRequest != null) {
    					Log.info(this.systemName, "Scheduler PROCCESSING: Floor request received -> " + this.messageQueue.inputFloorRequest.toString());
    					this.messageQueue.outputElevatorReceiver = this.messageQueue.inputFloorRequest;
    					this.messageQueue.inputFloorRequest = null;
    					//System.out.println("The Scheduler has received the Floor's request. Notifying the Elevator!");
    				}
    				else if (this.messageQueue.inputElevatorRequest != null) {
    					Log.info(this.systemName, "Scheduler PROCCESSING: Elevator Response received -> " + this.messageQueue.inputElevatorRequest.toString());
    					this.messageQueue.outputFloorReceiver = this.messageQueue.inputElevatorRequest;
    					this.messageQueue.inputElevatorRequest = null;
    					//System.out.println("The Scheduler has received the Elevator's request. Notifying the Floor!");
    				}
    				else {
    					Log.error(this.systemName, "Error with the message queue.");
    				}
    				this.messageQueue.notifyAll();
    			}
    		}
    	}catch(Exception e) {
    		Log.error(this.systemName, "The Scheduler broke!");
    	}
    }
}
