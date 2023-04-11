package test;

import java.util.concurrent.ConcurrentLinkedDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.Timing.MeasureTime;
import project.messageSystem.Message;
import project.elevatorImpl.Elevator;

public class TestMeasureSystem {

    private Elevator elevator; 
	private ConcurrentLinkedDeque<Message> responses;
	MeasureTime measure = new MeasureTime();
	
    @BeforeEach
	public void init(){
		System.out.println("Starting Up");
		responses = new ConcurrentLinkedDeque<>();
		// Message message = new Message(new Date(), "Elevator has arrived");
		elevator = new Elevator(0, "Elevator[0]", responses);
	}

    /* Measurement tests */

	@Test
	void measureOpenDoorState(){
		measure.start();
		elevator.setCurrentState(elevator.getElevatorDoorOpenState());
		elevator.getCurrentState().handleState();
		measure.end("Measurement Elevator Open Door Timer Took");
	}

	@Test
	void measureCloseDoorState(){
		measure.start();
		elevator.setCurrentState(elevator.getElevatorDoorCloseState());
		elevator.getCurrentState().handleState();
		measure.end("Measurement Elevator Close Door Timer Took");
	}

	@Test
	void measureMoveOneFloor(){
		measure.start();
        elevator.setCurrentState(elevator.getElevatorMovingState());
        elevator.getElevatorStatus().setCurrentFloor(5);
        elevator.getElevatorStatus().setNextDestination(8);
        elevator.getCurrentState().handleState();
        measure.end("Measurement for moving one floor is");
	}

    @Test
	void measureMoveMultipleFloors(){
		measure.start();
        elevator.setCurrentState(elevator.getElevatorMovingState());
        elevator.getElevatorStatus().setCurrentFloor(1);
        elevator.getElevatorStatus().setNextDestination(21);
        elevator.getDestinations().add(21);

        while(elevator.getElevatorStatus().getCurrentFloor() != elevator.getElevatorStatus().getNextDestination()){
            elevator.getCurrentState().handleState();
        }

        measure.end("Measurement for moving from floor 1 to Floor 21 is");
	}

}
