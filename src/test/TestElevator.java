/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ConcurrentLinkedDeque;

import java.util.Date;

import org.junit.jupiter.api.Test;

import project.elevatorSubSystem.ElevatorSubSystem;
import project.constants.ElevatorState;
import project.elevatorSubSystem.Elevator;
import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.Message;
import project.messageSystem.MessageQueue;

/**
 * @author Elisha Catherasoo, Dorothy Tran
 *
 */
class TestElevator {
	private ConcurrentLinkedDeque<Message> responses;
	
	ElevatorSubSystemMessageQueue eMQ = new ElevatorSubSystemMessageQueue(); 
	Message message = new Message(new Date(), "Elevator has arrived");
	
	ElevatorSubSystem elevatorSubSystem = new ElevatorSubSystem(eMQ, "Elevator subsystem");
	Elevator Elevator = new Elevator("Elevator", responses, 0);
	
	
	@Test
	void testElevatorIsDead() {
		assertFalse(Elevator.getIsDead());
	}
	
	@Test
	void testElevatorSystemName() {
		assertEquals(Elevator.getSystemName(), "Elevator");
	}
	
	@Test
	void testElevatorResponses() {
		assertEquals(Elevator.getResponses(), null);
	}
	
	@Test
	void testElevatorCurrentFloor() {
		assertEquals(Elevator.getCurrentFloor(), 0);
	}
	
	@Test
	void testElevatorDestinationFloor() {
		assertEquals(Elevator.getDestinationFloor(), 0);
	}
	
	@Test
	void testElevatorState() {
		assertEquals(Elevator.getElevatorState(), ElevatorState.IDLE);
	}
	
	@Test
	void testElevatorRequests() {
		assertTrue(Elevator.getRequests().isEmpty());
	}
	
	
	@Test
	void testElevatorAddRequests() {
		assertEquals(Elevator.getRequests().size(), 0); //check if initially empty
		Elevator.addRequest(message);
		assertEquals(Elevator.getRequests().size(), 1); // check if the request was added
	}

}
