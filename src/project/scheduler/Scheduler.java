package project.scheduler;

import project.logger.Log;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.FloorMessageQueue;
import project.messageSystem.Message;
import project.messageSystem.MessageQueue;
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
    private Parser parser; 
    private String systemName; 
    /**
     * Construct for the Scheduler class.
     * @param isDead Boolean variable for determining if there is a connection active.
     * @param An messageQueue object for creating a message queue.
     */
    public Scheduler(Parser parser, FloorMessageQueue fMQ, ElevatorSubSystemMessageQueue eMQ, String systemName){
        this.isDead = false;
        this.eMQ = eMQ;
        this.fMQ = fMQ;
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
    			
    			while (this.fMQ.requests.size() <= 0 && this.eMQ.responses.size() <= 0) {
    				Thread.sleep(500);
    			}
    			
    			Message floorRequest = this.fMQ.requests.poll();
    			Message elevatorResponse = this.eMQ.responses.poll();
    			
				
				if (floorRequest != null) {
					Log.info(this.systemName, "Scheduler PROCCESSING: Floor request received -> " + floorRequest.toString());
					this.eMQ.requests.addFirst(floorRequest);
				}
				else if (elevatorResponse != null) {
					Log.info(this.systemName, "Scheduler PROCCESSING: Elevator Response received -> " + elevatorResponse.toString());
					this.fMQ.responses.addFirst(elevatorResponse);
				}
				else {
					Log.error(this.systemName, "Error with the message queue.");
				}
    			
    		}
    	}catch(Exception e) {
    		Log.error(this.systemName, "The Scheduler broke!");
    	}
    }
}
