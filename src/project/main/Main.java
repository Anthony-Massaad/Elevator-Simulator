package project.main;

import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.FloorMessageQueue;
import project.floorSubSystem.Floor;
import project.elevatorSubSystem.ElevatorSubSystem;
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
		
		Floor floor = new Floor(parser, fMQ, 1, "Floor[0]");
		ElevatorSubSystem ElevatorSubSystem = new ElevatorSubSystem(eMQ, "Elevator subsystem");
		
		Scheduler scheduler = new Scheduler(fMQ, eMQ, "Scheduler");
		
		Thread tFloor = new Thread(floor);
		Thread tElevator = new Thread(ElevatorSubSystem);
		Thread tScheduler = new Thread(scheduler);
		
		tFloor.start();
		tElevator.start();
		tScheduler.start();
	}

}
