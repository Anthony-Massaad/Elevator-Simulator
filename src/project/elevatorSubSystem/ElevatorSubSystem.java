package project.elevatorSubSystem;

import java.util.Date;

import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.MessageQueue;
import project.messageSystem.messages.*;

public class ElevatorSubSystem implements Runnable{
	
	private MessageQueue messageQueue;
	private Elevator elevator; 
	private ElevatorCommunication elevatorCommunication;
	private boolean isDead; 
	
	public ElevatorSubSystem(MessageQueue messageQueue, ElevatorCommunication elevatorCommunication) {
		this.messageQueue = messageQueue; 
		this.elevatorCommunication = elevatorCommunication; 
		this.isDead = false; 
	}

	@Override
	public void run() {
		// begin initialize elevator block 
		this.elevator = new Elevator("Elevator 1", this.elevatorCommunication);
		Thread elevatorT = new Thread(this.elevator);
		elevatorT.start();
		// end of initialize elevator block
		try {
    		while(!this.isDead) {
    			synchronized (this.messageQueue) {
					while(this.messageQueue.outputElevatorReceiver == null) {
						//Log.info("Elevator is waiting for a request");
						this.messageQueue.wait();
					}
					Message sentMessage = this.messageQueue.outputElevatorReceiver;
					
					if (sentMessage instanceof RequestElevatorMessage) {
						// Assumption of 1 elevator 
						RequestElevatorMessage message = (RequestElevatorMessage) sentMessage;
						this.elevator.giveMessage(new MoveToMessage(new Date(), message.getFloorNumber()));
					}
					
					this.messageQueue.inputElevatorRequest = this.messageQueue.outputElevatorReceiver;
					this.messageQueue.outputElevatorReceiver = null;
					this.messageQueue.notifyAll();
				}
    			
    		}
    	}catch(Exception e) {
    		//Log.error(this.systemName, "Something Broke");
    	}
		
				
		try {
			while (!this.isDead) {
				synchronized(this.elevatorCommunication) {
					while (this.elevatorCommunication.responses.size() <= 0) {
						this.elevatorCommunication.wait();
					}
				}
			}
		}catch (Exception e) {
			
		}
		
	}
	
	
	
}
