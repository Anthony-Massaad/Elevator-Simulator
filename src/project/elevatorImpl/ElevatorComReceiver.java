package project.elevatorImpl;

import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.SimulationConstants;
import project.messageSystem.Message;
import project.udp.UDPSend;

public class ElevatorComReceiver extends UDPSend implements Runnable{

	private ConcurrentLinkedDeque<Message> elevatorResponses;
	public ElevatorComReceiver(String systemName, ConcurrentLinkedDeque<Message> elevatorResponses) {
		super(systemName);
		this.elevatorResponses = elevatorResponses;
	}

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
			}

		}catch(Exception e){
			// throw error 
			e.printStackTrace();
			this.closeSockets();
		}
	}
}
