package project.scheduler;

import java.util.Date;

import project.logger.Log;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.FloorMessageQueue;
import project.messageSystem.Message;
import project.simulationParser.Parser; 

/**
 * Class Scheduler that implements the Runnable class for the purpose of creating a thread. Acts as a communication channel for the Floor and the Elevator systems using a MessageQueue.
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Scheduler implements Runnable {

    private boolean isDead;
    private ElevatorSubSystemMessageQueue eMQ;
    private FloorMessageQueue fMQ; 
    private String systemName; 
    /**
     * Construct for the Scheduler class.
     * @param isDead Boolean variable for determining if there is a connection active.
     * @param An messageQueue object for creating a message queue.
     */
    public Scheduler(FloorMessageQueue fMQ, ElevatorSubSystemMessageQueue eMQ, String systemName){
        this.isDead = false;
        this.eMQ = eMQ;
        this.fMQ = fMQ;
        this.systemName = systemName; 
    }
    
	/**
	 * Getter method to get the boolean value if the Scheduler system is dead or not.
	 * @return Boolean, true or false.
	 */
	public Boolean getIsDead() {
		return this.isDead;
	}

	/**
	 * Getter method to get Elevator subsystem message queue.
	 * @return ElevatorSubSystemMessageQueue.
	 */
	public ElevatorSubSystemMessageQueue getElevatorSubSystemMessageQueue() {
		return this.eMQ;
	}

	/**
	 * Getter method to get Floor message queue.
	 * @return FloorMessageQueue.
	 */
	public FloorMessageQueue getFloorMessageQueue() {
		return this.fMQ;
	}

	/**
	 * Getter method to get the name of the Scheduler system.
	 * @return String, name of the Scheduler system.
	 */
	public String getSystemName() {
		return this.systemName;
	}
    
    @Override
    /**
     * Overriden run method as part of Runnable. 
     */
    public void run() {
    	try {
    		while(!this.isDead) {
    			if (Parser.isEmpty()) {
            		this.isDead = false; 
					Log.notification("SCHEDULER", "Scheduler Killed", new Date(), this.systemName);
            		break; 
            	}
    			
    			// Will sleep when neither the elevator subsystem or the floor gave the scheduler anything 
    			if (this.fMQ.requests.size() <= 0 && this.eMQ.responses.size() <= 0) {
    				Thread.sleep(500);
    			}else {
    				Message floorRequest = this.fMQ.requests.poll();
        			Message elevatorResponse = this.eMQ.responses.poll();
        			
    				if (floorRequest != null) {
    					// floor requested something
    					Log.notification("SCHEDULER", "received floor request: " + floorRequest.toString(), new Date(), this.systemName);
    					this.eMQ.requests.addFirst(floorRequest);
    				}
    				else if (elevatorResponse != null) {
    					// Elevator Subsystem Responded
    					Log.notification("SCHEDULER", "received Elevator Response: " + elevatorResponse.toString(), new Date(), this.systemName);
    					this.fMQ.responses.addFirst(elevatorResponse);
    				}
    				else {
    		    		Log.error("SCHEDULER", "System skipped sleep and no conditions met", new Date(), this.systemName);
    				}
    			}
    		}
    	}catch(Exception e) {
    		Log.error("SCHEDULER", "System Crashed", new Date(), this.systemName);
    	}
    }
}
