package project.floorSubSystem;

import project.logger.Log;
import project.messageSystem.MessageQueue;
import project.messageSystem.FloorMessageQueue;
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
    private FloorMessageQueue messageQueue; 
    private String systemName; 
    
    /**
     * Constructor for the Floor class
     * @param parser, Parser of the system
     * @param messageQueue, MessageQueue object for creating a message queue.
     * @param systemName, the name of the system
     */
    public Floor(Parser parser, FloorMessageQueue messageQueue, String systemName){
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
            	while ((this.messageQueue.responses.size() <= 0 && this.parser.isEmpty()) || this.messageQueue.requests.size() >= 1) {
                	Thread.sleep(500);
                }
            	
            	Message receive = this.messageQueue.responses.poll();
            	Message request = this.messageQueue.requests.poll();
            	
            	if (receive != null) {
                	Log.info(this.systemName, "Message Received from elevator -> " + receive.toString());
                }else if (request == null && !this.parser.isEmpty()) {
                	Message m = new Message(new Date(), this.parser.getRequest());
                	this.messageQueue.requests.addFirst(m);
                	Log.info(this.systemName, "Sending message -> " + m.toString());
                	this.parser.removeRequest();
                }
            }
        }
        catch (Exception e) {
        }
    }
    
}
