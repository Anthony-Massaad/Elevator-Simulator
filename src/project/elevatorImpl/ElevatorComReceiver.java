package project.elevatorImpl;

import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.SimulationConstants;
import project.messageSystem.Message;
import project.udp.UDPSend;

/**
 * Elevator Communicator class from Elevator to Floor
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorComReceiver extends UDPSend implements Runnable{

	private ConcurrentLinkedDeque<Message> elevatorResponses;
	
	/**
	 * Constructor for the ElevatorComReceiver.
	 * @param systemName String systemName.
	 * @param elevatorResponses ArrayList of elevator responses.
	 */
	public ElevatorComReceiver(String systemName, ConcurrentLinkedDeque<Message> elevatorResponses, String addr) {
		super(systemName, addr);
		this.elevatorResponses = elevatorResponses;
	}

	/**
	 * Void method for running the ElevatorComReceiver. Consistently polls for elevator responses and sends them to the SchedulerMidTask.
	 */
	@Override
	public void run() {
		try{
			while (true) {
				if (this.elevatorResponses.size() <= 0){
					Thread.sleep(1000);
					continue; 
				}
				Message msg = this.elevatorResponses.poll();
				this.send(msg, SimulationConstants.SCHEDULER_MID_TASK_PORT);
				this.send(msg, SimulationConstants.GUI_PORT);
			}

		}catch(Exception e){
			// throw error 
			e.printStackTrace();
			this.closeSockets();
		}
	}
}
