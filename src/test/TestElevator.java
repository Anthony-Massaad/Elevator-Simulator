package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.Test;

import project.constants.ElevatorState;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.messageSystem.Message;
import project.elevatorImpl.Elevator;
import project.elevatorImpl.ElevatorStatus;

/**
 * Elevator Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestElevator {
	ConcurrentLinkedDeque<Message> responses = new ConcurrentLinkedDeque<>();
	
	// Message message = new Message(new Date(), "Elevator has arrived");
	ConcurrentLinkedDeque<Message> responsesConcurrentLinkedDeque = new ConcurrentLinkedDeque<>();
	Elevator Elevator = new Elevator(0, "Elevator[0]", responses);
		
	// ADD GETTERS TESTING
	
	@Test
	void testElevatorCheckMessage() {
		Elevator.checkMessage();
		assertEquals(Elevator.getState(), ElevatorState.IDLE);
	}
	
	@Test
	void testElevatorHandleOpenDoor() {
		try {
			Elevator.handleOpenDoor();
			assertEquals(Elevator.getState(), ElevatorState.CLOSE_DOOR);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	void testElevatorHandleCloseDoor() {
		try {
			Elevator.handleCloseDoor();
			assertEquals(Elevator.getState(), ElevatorState.IDLE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	void testElevatorHandleMoving() {
		try {
			Elevator.handleMoving();
			assertEquals(Elevator.getState(), ElevatorState.IDLE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testElevatorAddUpcomingButtons() {
		ArrayList<Integer> buttons = new ArrayList<>();
		Elevator.addUpcomingButtons(2, buttons);
		HashMap<Integer, ArrayList<Integer>> floorInputButtonsHashMap = new HashMap<>();
		floorInputButtonsHashMap.put(2, buttons);
		
		
		assertEquals(Elevator.getFloorInputButtons(), floorInputButtonsHashMap);
	}
	
	@Test
	void testElevatorGetResponses() {
		assertEquals(Elevator.getResponses(), responses);
	}
	
	@Test
	void testElevatorGetRequests() {
		assertTrue(Elevator.getRequests().isEmpty());
	}
	
	@Test
	void testElevatorGetDestinations() {
		ArrayList<Integer>destinations = new ArrayList<>();
		assertEquals(Elevator.getDestinations(), destinations);
	}
	
	@Test
	void testElevatorGetElevatorStatus() {
		assertEquals(Elevator.getElevatorStatus().getMotorDirection(), MotorDirection.IDLE);
		assertEquals(Elevator.getElevatorStatus().getNumberOfPassengers(), 0);
		assertEquals(Elevator.getElevatorStatus().getNextDestination(), 0);
	}
	
	@Test
	void testElevatorGetState() {
		assertEquals(Elevator.getState(), ElevatorState.IDLE);
	}
	
	@Test
	void testElevatorGetLamps() {
		assertEquals(Elevator.getLamps().length, 22);
	}
	
	@Test
	void testElevatorGetSystemName() {
		assertEquals(Elevator.getSystemName(), "Elevator[0]");
	}
	
	@Test
	void testElevatorGetId() {
		assertEquals(Elevator.getId(), 0);
	}
	
	@Test
	void testElevatorGetFloorInputButtons() {
		HashMap<Integer, ArrayList<Integer>> floorInputButtons = new HashMap<>();
		assertEquals(Elevator.getFloorInputButtons(), floorInputButtons);
	}
}
