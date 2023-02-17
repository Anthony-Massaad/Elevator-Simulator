package project.main;

import project.messageSystem.MessageQueue;
import project.floorSubSystem.Floor;
import project.elevatorSubSystem.Elevator;
import project.scheduler.Scheduler;
import project.simulationParser.Parser;

/**
 * 
 * Main method to run the overall system
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MessageQueue messageQueue = new MessageQueue(); 
		Parser parser = new Parser(); 
		
		Floor floor = new Floor(parser, messageQueue, "Floor");
		Elevator elevator = new Elevator(parser, messageQueue, "Elevator");
		Scheduler scheduler = new Scheduler(parser, messageQueue, "Scheduler");
		
		Thread tFloor = new Thread(floor);
		Thread tElevator = new Thread(elevator);
		Thread tScheduler = new Thread(scheduler);
		
		tFloor.start();
		tElevator.start();
		tScheduler.start();
	}

}
