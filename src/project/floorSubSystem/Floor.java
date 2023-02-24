package project.floorSubSystem;

import project.logger.Log;
import project.messageSystem.messages.ElevatorRequestMessage;
import project.messageSystem.FloorMessageQueue;
import project.messageSystem.Message;
import project.simulationParser.Parser;
import java.util.Date;

/**
 * Class Floor that implements the Runnable class for the purpose of creating a thread. 
 * This class represents the simulation of arrivals of passengers to the elevator.
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Floor implements Runnable{

    private boolean isDead;
    private Parser parser;
    private FloorMessageQueue messageQueue; 
    private final String systemName; 
    private int floorNumber; 
    // ASSUMING ONE REQUEST AT A TIME, TEMP VARIABLE FOR NOW, DELETE LATER WHEN SENDING MULTIPLE REQUEST AT ONCE 
    private boolean requestSent; 
    
    /**
     * Constructor for the Floor class
     * @param parser, Parser of the system
     * @param messageQueue, MessageQueue object for creating a message queue.
     * @param systemName, the name of the system
     */
    public Floor(Parser parser, FloorMessageQueue messageQueue, int floorNumber, String systemName){
        this.isDead = false;
        this.parser = parser;
        this.messageQueue = messageQueue;
        this.systemName = systemName; 
        this.floorNumber = floorNumber; 
        this.requestSent = false; // TEMP VARIABLE 
    }

    /**
     * Overriden run method as part of Runnable
     */
    @Override
    public void run() {
        try {
            while (!this.isDead){
            	if (Parser.isEmpty()) {
            		this.isDead = false; 
            		break; 
            	}
            	
            	// will continuously sleep when there is no responses and if a request was sent already 
            	// For this milestone still, it is one request at a time
            	while (this.requestSent && this.messageQueue.responses.size() <= 0) {

                	Thread.sleep(500);
                }
            	
            	Message receive = this.messageQueue.responses.poll();
            	Message request = this.messageQueue.requests.poll();
            	
            	// Floor has received a message
            	if (receive != null) {
            		Log.notification("FLOOR", "Message received from an elevator: " + receive.toString(), new Date(), this.systemName);
            		this.floorNumber++; 
                	this.parser.removeRequest();
            		this.requestSent = false; // TEMP DELETE LATER. SEE REASONING ON INITIALIZER 
            	}
            	
            	// Floor is sending a request to the scheduler
            	if (request == null && !Parser.isEmpty()) {
                	ElevatorRequestMessage elRequestMessage = new ElevatorRequestMessage(new Date(), this.floorNumber);
                	this.messageQueue.requests.addFirst(elRequestMessage);
            		Log.notification("FLOOR", "Sending Message to Schedular: " + elRequestMessage.toString(), new Date(), this.systemName);
                	this.requestSent = true; // TEMP DELETE LATER. SEE REASONING ON INITIALIZER 
                }
            }
        }
        catch (Exception e) {
        	Log.error("FLOOR", "System Crashed", new Date(), this.systemName);
        }
    }
    
}
