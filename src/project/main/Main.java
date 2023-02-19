package project.main;

import project.messageSystem.MessageQueue;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.FloorMessageQueue;
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
		FloorMessageQueue fMQ = new FloorMessageQueue(); 
		ElevatorSubSystemMessageQueue eMQ = new ElevatorSubSystemMessageQueue(); 
		
		
		Parser parser = new Parser(); 
		
		Floor floor = new Floor(parser, fMQ, "Floor");
		Elevator elevator = new Elevator(parser, eMQ, "Elevator");
		
		
		Scheduler scheduler = new Scheduler(parser, fMQ, eMQ, "Scheduler");
		
		Thread tFloor = new Thread(floor);
		Thread tElevator = new Thread(elevator);
		Thread tScheduler = new Thread(scheduler);
		
		tFloor.start();
		tElevator.start();
		tScheduler.start();
	}

}
