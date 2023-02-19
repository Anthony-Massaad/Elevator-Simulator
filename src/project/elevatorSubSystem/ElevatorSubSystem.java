package project.elevatorSubSystem;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.logger.Log;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.Message;
import project.messageSystem.messages.ElevatorRequestMessage;
import project.messageSystem.messages.MoveToMessage;

public class ElevatorSubSystem implements Runnable{
	
	private ElevatorSubSystemMessageQueue messageQueue;
	private boolean isDead;
	private String systemName; 
	private Elevator elevator; 
	private ConcurrentLinkedDeque<Message> elevatorResponses;

	public ElevatorSubSystem(ElevatorSubSystemMessageQueue messageQueue, String systemName) {
		this.messageQueue = messageQueue; 
		this.isDead = false; 
		this.systemName = systemName;
		this.elevatorResponses = new ConcurrentLinkedDeque<>();
	}
	
	@Override
	public void run() {
		// begin initialize elevator // 
		this.elevator = new Elevator("Elevator[0]", elevatorResponses); 
		Thread elT = new Thread(this.elevator);
		elT.start();
		// begin initialize elevator // 
		
		try {
    		while(!this.isDead) {
    			while (this.messageQueue.requests.size() <= 0 && this.elevatorResponses.size() <= 0) {
    				Thread.sleep(500);
    			}
    			
    			if (this.messageQueue.requests.size() > 0) {
    				// always true 
        			Message request = this.messageQueue.requests.poll();
        			
        			if (request instanceof ElevatorRequestMessage) {
        				ElevatorRequestMessage message = (ElevatorRequestMessage) request;
        				MoveToMessage moveToMessage = new MoveToMessage(message.getTimeStamp(), message.getFloorNumber());
        				this.elevator.addRequest(moveToMessage);
        				// Assuming we have one elevator, it's starting is always idle
        			}
    			}else if (this.elevatorResponses.size() > 0) {
    				this.messageQueue.responses.addFirst(this.elevatorResponses.poll());
    			}
			}
    	}catch(Exception e) {
    		e.printStackTrace();
//    		Log.error(this.systemName, "Something Broke");
    	}
		
	}

}
