package project.scheduler;

import java.util.Date;

import project.logger.Log;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.FloorMessageQueue;
import project.messageSystem.Message;
import project.simulationParser.Parser; 

/**
 * Class Scheduler that implements the Runnable class for the purpose of creating a thread. Acts as a communication channel for the Floor and the Elevator systems using a MessageQueue.
 * @author Dorothy Tran, Max Curkovic SYSC 3303 Winter 2023 Lab A1
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
    
    @Override
    /**
     * Overriden run method as part of Runnable. Checks to see whether a MessageQueue can be received 
     * or sent by the Floor or the Elevator to the other, through the use of the Scheduler. 
     * Notifies when this has occurred.
     */
    public void run() {
    	try {
    		while(!this.isDead) {
    			if (Parser.isEmpty()) {
            		this.isDead = false; 
            		break; 
            	}
    			
    			if (this.fMQ.requests.size() <= 0 && this.eMQ.responses.size() <= 0) {
    				Thread.sleep(500);
    			}else {
    				Message floorRequest = this.fMQ.requests.poll();
        			Message elevatorResponse = this.eMQ.responses.poll();
        			
    				if (floorRequest != null) {
    					Log.notification("SCHEDULER", "received floor request: " + floorRequest.toString(), new Date(), this.systemName);
    					this.eMQ.requests.addFirst(floorRequest);
    				}
    				else if (elevatorResponse != null) {
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
