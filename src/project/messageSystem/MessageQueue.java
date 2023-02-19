package project.messageSystem;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Class MessageQueue that serves as the basis for four message queues: input floor request, 
 * input elevator request, output floor receiver, and elevator floor receiver.
 * @author Max Curkovic, Dorothy Tran Group 2 SYSC 3303 A1
 */
public class MessageQueue {
	
	public ConcurrentLinkedDeque<Message> responses;
	public ConcurrentLinkedDeque<Message> requests;

	/**
	 * Constructor for the MessageQueue class.
	 */
	public MessageQueue() {
		this.requests = new ConcurrentLinkedDeque<>();
		this.responses = new ConcurrentLinkedDeque<>();
	}
	
}



