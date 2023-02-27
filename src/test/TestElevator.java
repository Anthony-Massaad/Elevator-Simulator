package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Date;

import org.junit.jupiter.api.Test;

import project.constants.ElevatorState;
import project.elevatorSubSystem.Elevator;
import project.messageSystem.Message;

/**
 * Elevator Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestElevator {
	private ConcurrentLinkedDeque<Message> responses;
	
	Message message = new Message(new Date(), "Elevator has arrived");
	Elevator Elevator = new Elevator("Elevator[0]", responses, 0);
	
	/**
	 * Test to check if the elevator is initially dead.
	 */
	@Test
	void testElevatorIsDead() {
		assertFalse(Elevator.getIsDead());
	}
	
	/**
	 * Test to check the name of elevator system.
	 */
	@Test
	void testElevatorSystemName() {
		assertEquals(Elevator.getSystemName(), "Elevator[0]");
	}
	
	/**
	 * Test to check if the ConcurrentLinkedDeque of responses is initially empty.
	 */
	@Test
	void testElevatorResponses() {
		assertEquals(Elevator.getResponses(), null);
	}
	
	/**
	 * Test to check if the initial current floor the elevator is on is floor 0.
	 */
	@Test
	void testElevatorCurrentFloor() {
		assertEquals(Elevator.getCurrentFloor(), 0);
	}
	
	/**
	 * Test to check if the destination floor is initally floor 0.
	 */
	@Test
	void testElevatorDestinationFloor() {
		assertEquals(Elevator.getDestinationFloor(), 0);
	}
	
	/**
	 * Test to check if the initial state of the Elevator is IDLE.
	 */
	@Test
	void testElevatorState() {
		assertEquals(Elevator.getElevatorState(), ElevatorState.IDLE);
	}
	
	/**
	 * Test to check if the ConcurrentLinkedDeque of requests is initially empty.
	 */
	@Test
	void testElevatorRequests() {
		assertTrue(Elevator.getRequests().isEmpty());
	}
	
	/**
	 * Test to check if requests are added into the ConcurrentLinkedDeque of requests.
	 */
	@Test
	void testElevatorAddRequests() {
		assertEquals(Elevator.getRequests().size(), 0); //check if initially empty
		Elevator.addRequest(message);
		assertEquals(Elevator.getRequests().size(), 1); // check if the request was added
	}

}
