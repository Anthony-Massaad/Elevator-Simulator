package project.elevatorSubSystem;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.logger.Log;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.Message;
import project.messageSystem.messages.ElevatorRequestMessage;
import project.messageSystem.messages.MoveToMessage;
import project.simulationParser.Parser;

/**
 * Class for the Elevator Subsystem. This class will be responsible for managing all elevators in this applicaiton 
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 */
public class ElevatorSubSystem implements Runnable{
	
	private ElevatorSubSystemMessageQueue messageQueue;
	private boolean isDead;
	private String systemName; 
	private Elevator elevator; 
	private ConcurrentLinkedDeque<Message> elevatorResponses;

	/**
	 * Constrcutor for the Elevator Subsystem 
	 * @param messageQueue
	 * @param systemName
	 */
	public ElevatorSubSystem(ElevatorSubSystemMessageQueue messageQueue, String systemName) {
		this.messageQueue = messageQueue; 
		this.isDead = false; 
		this.systemName = systemName;
		this.elevatorResponses = new ConcurrentLinkedDeque<>();
	}
	
	public ElevatorSubSystemMessageQueue getMessageQueue() {
		return this.messageQueue;
	}

	public Boolean getIsDead() {
		return this.isDead;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public ConcurrentLinkedDeque<Message> getElevatorResponses() {
		return this.elevatorResponses;
	}
	
	
	/**
	 * Overriden run method from the Runnable class
	 */
	@Override
	public void run() {
		// begin initialize elevator // 
		this.elevator = new Elevator("Elevator[0]", elevatorResponses, 0); 
		Thread elT = new Thread(this.elevator);
		elT.start();
		// begin initialize elevator // 
		
		try {
    		while(!this.isDead) {
    			if (Parser.isEmpty()) {
            		this.isDead = false; 
            		Log.notification("ELEVATOR SUBSYSTEM", "Elevator SubSystem Killed", new Date(), this.systemName);

            		break; 
            	}
    			// Concurrent loop
    			// Will sleep if it does not have responses or requests
    			if (this.messageQueue.requests.size() <= 0 && this.elevatorResponses.size() <= 0) {
    				Thread.sleep(500);
    			}else if (this.messageQueue.requests.size() > 0) {
    				// always true for now!!! as there is only one message being sent to the elevator sybsystem // 
        			Message request = this.messageQueue.requests.poll();
        			if (request instanceof ElevatorRequestMessage) {
        				ElevatorRequestMessage message = (ElevatorRequestMessage) request;
        				MoveToMessage moveToMessage = new MoveToMessage(message.getTimeStamp(), message.getFloorNumber());
        				this.elevator.addRequest(moveToMessage);
        				// Assuming we have one elevator, it's starting is always idle
        			}
    			}else if (this.elevatorResponses.size() > 0) {
    				// Send back to the schedule the response from an elevator
    				this.messageQueue.responses.addFirst(this.elevatorResponses.poll());
    			}
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    		Log.error("ELEVATOR SUBSYSTEM", "System Crashed", new Date(), this.systemName);
    	}
		
	}
}
