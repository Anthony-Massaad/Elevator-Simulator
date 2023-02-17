package project.floorSubSystem;

import project.logger.Log;
import project.messageSystem.MessageQueue;
import project.messageSystem.Message;
import project.simulationParser.Parser;
import java.util.Date;

import java.util.ArrayList;

/**
 * Class Floor that implements the Runnable class for the purpose of creating a thread. 
 * This class represents the simulation of arrivals of passengers to the elevator.
 * @author Anthony Massaad SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Floor implements Runnable{

    private boolean isDead;
    private Parser parser;
    private MessageQueue messageQueue; 
    private String systemName; 
    
    /**
     * Constructor for the Floor class
     * @param parser, Parser of the system
     * @param messageQueue, MessageQueue object for creating a message queue.
     * @param systemName, the name of the system
     */
    public Floor(Parser parser, MessageQueue messageQueue, String systemName){
        this.isDead = false;
        this.parser = parser;
        this.messageQueue = messageQueue;
        this.systemName = systemName; 

    }

    /**
     * Overriden run method as part of Runnable
     */
    @Override
    public void run() {
        try {
            while (!this.isDead){
            	synchronized(this.messageQueue) { 
            		Message receive = this.messageQueue.outputFloorReceiver;
            		Message request = this.messageQueue.inputFloorRequest;
            		
            		if (this.parser.isEmpty()) {
    					this.isDead = true;
    					break;
    				}
            		
                    while ((receive == null && this.parser.isEmpty()) && request != null) {
                    	this.messageQueue.wait();
                    	//Log.info("FLOOR IS WAITING");
                    }
                    
                    if (receive != null) {
                    	Log.info(this.systemName, "Message Received from elevator -> " + receive.toString());
                    	this.messageQueue.outputFloorReceiver = null;
                    }else if (request == null && !this.parser.isEmpty()) {
                    	this.messageQueue.inputFloorRequest = new Message(new Date());
                    	Log.info(this.systemName, "Sending message -> " + this.messageQueue.inputFloorRequest.toString());
                    	this.parser.removeRequest();
                    }
                    this.messageQueue.notifyAll();
            	}
            }
        }
        catch (Exception e) {
        }
    }
    
}
