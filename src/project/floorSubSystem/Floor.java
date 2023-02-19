package project.floorSubSystem;

import project.logger.Log;
import project.messageSystem.MessageQueue;
import project.messageSystem.messages.ElevatorRequestMessage;
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
    private final String systemName; 
    private int floorNumber; 
    
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
            		Log.notification("FLOOR", "Message received from an elevator: " + receive.toString(), new Date(), this.systemName);
            		this.floorNumber++; 
                }else if (request == null && !this.parser.isEmpty()) {
                	ElevatorRequestMessage elRequestMessage = new ElevatorRequestMessage(new Date(), this.floorNumber);
                	this.messageQueue.requests.addFirst(elRequestMessage);
            		Log.notification("FLOOR", "Sending Message to Schedular: " + elRequestMessage.toString(), new Date(), this.systemName);
                	this.parser.removeRequest();
                }
            }
        }
        catch (Exception e) {
        }
    }
    
}
