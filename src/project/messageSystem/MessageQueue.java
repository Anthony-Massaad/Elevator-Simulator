package project.messageSystem;


/**
 * Class MessageQueue that serves as the basis for four message queues: input floor request, 
 * input elevator request, output floor receiver, and elevator floor receiver.
 * @author Max Curkovic, Dorothy Tran Group 2 SYSC 3303 A1
 */
public class MessageQueue {
	
	
	public Message inputFloorRequest;
	public Message outputFloorReceiver;
	public Message inputElevatorRequest;
	public Message outputElevatorReceiver;
	
	/**
	 * Constructor for the MessageQueue class.
	 */
	public MessageQueue() {
		this.inputFloorRequest = null;
		this.outputFloorReceiver = null;
		this.inputElevatorRequest = null;
		this.outputElevatorReceiver = null;
	}
	
}



