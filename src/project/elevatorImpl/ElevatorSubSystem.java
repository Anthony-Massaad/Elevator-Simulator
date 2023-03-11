package project.elevatorImpl;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.MoveToMessage;
import project.messageSystem.messages.RequestElevatorMessage;
import project.udp.UDPReceive;

public class ElevatorSubSystem extends UDPReceive{
	
	private boolean isDead; 
	private Elevator[] elevators; 
	private ConcurrentLinkedDeque<Message> elevatorResponses;
	
	/**
	 * Constructor for the ElevatorSubSystem.
	 * @param port An integer port number.
	 * @param systemName A string system name.
	 */
	public ElevatorSubSystem(int port, String systemName) {
		super(port, systemName);
		this.elevatorResponses = new ConcurrentLinkedDeque<>();
		this.isDead = false; 
		this.elevators = new Elevator[SimulationConstants.NUM_OF_ELEVATORS];
		System.out.println(this.systemName + " started");
		System.out.println("-------------------------------------------------");
	}
	
	/**
	 * Void method for implementing the UDP for the elevator subsystem.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void udpImplementation() throws IOException, InterruptedException {
		Message request = this.receive(SimulationConstants.BYTE_SIZE);
		// received, do something 
		if (request instanceof RequestElevatorMessage){
			RequestElevatorMessage requestMessage = (RequestElevatorMessage) request; 
			Log.notification("ELEVATOR SUBSYSTEM", requestMessage.toString(), new Date(), this.systemName);
			MoveToMessage moveToMessage = new MoveToMessage(requestMessage.getTimeStamp(), requestMessage.getFloorNumber(), requestMessage.getButtonsToBePressed());
			this.elevators[requestMessage.getElevatorID()].addRequest(moveToMessage);
		}else{
			throw new Error("Unknown message received");
		}
	}

	/**
	 * Void method for running the Elevator subsystem. It essentially initializes all elevators and implements the UDP communication.
	 */
	public void run() {
		// begin initialize elevator // 
		for (int i = 0; i<SimulationConstants.NUM_OF_ELEVATORS; i++) {
			// rand.nextInt(SimulationConstants.NUM_OF_FLOORS) + 1 = (0-21) + 1 = (1-22)
			this.elevators[i] = new Elevator(i, "Elevator["  + i + "]", this.elevatorResponses);
			Thread eT = new Thread(this.elevators[i]);
			eT.start();
			Log.notification("ELEVATOR SUBSYSTEM", "Elevator["  + i + "] Started and added to floor " + this.elevators[i].getElevatorStatus().getCurrentFloor(), new Date(), this.systemName);
		}
		// begin initialize elevator //
		// initialize the elevatorCommuniocation
		// this communication is a thread that will receive the responses from the elevators and send to the scheduler mid task
		ElevatorComReceiver elReceiver = new ElevatorComReceiver("Elevaor Communicator", this.elevatorResponses);
		Thread elReceiverT = new Thread(elReceiver);
		elReceiverT.start();
		Log.notification("ELEVATOR SUBSYSTEM", "Elevator Communicator Started", new Date(), this.systemName);

		try {
			while (!this.isDead) {
				this.udpImplementation();
			}
		}catch (Exception e) {
			e.printStackTrace();
    		Log.error("ELEVATOR SUBSYSTEM", "System Crashed", new Date(), this.systemName);
		}
	}
	
	/**
	 * Getter method to get the SubSystem's elevators.
	 * @return Elevator[]
	 */
	public Elevator[] getElevators() {
		return this.elevators;
	}
	
	/**
	 * Getter method for the isDead attribute.
	 * @return boolean
	 */
	public boolean getIsDead() {
		return this.isDead;		
	}

	/**
	 * Getter method to get the Elevator responses from the message queue.
	 * @return ConcurrentLinkedDeque<Message>
	 */
	public ConcurrentLinkedDeque<Message> getElevatorResponses() {
		return this.elevatorResponses;
	}

	/**
	 * Main method for running the elevator subsystem.
	 * @param args Not used in this iteration.
	 */
	public static void main(String[] args) {
		ElevatorSubSystem el = new ElevatorSubSystem(SimulationConstants.ELEVATOR_MANAGER_PORT, "Elevator Subsystem");
		el.run();
	}
	
}
