package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.constants.ElevatorState;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.MoveToMessage;
import project.elevatorImpl.Elevator;
import project.elevatorImpl.ElevatorStatus;

/**
 * Elevator Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestElevator {
	
	private Elevator Elevator; 
	private ConcurrentLinkedDeque<Message> responses;
	@BeforeEach
	public void init(){
		System.out.println("Starting Up");
		responses = new ConcurrentLinkedDeque<>();
		// Message message = new Message(new Date(), "Elevator has arrived");
		Elevator = new Elevator(0, "Elevator[0]", responses);
	}
	
	/**
	 * Test method to check if the Elevator's State is IDLE when checking the elevator's message.
	 */
	@Test
	void testElevatorCheckMessage() {
		Elevator.checkMessage();
		assertEquals(Elevator.getState(), ElevatorState.IDLE);
	}
	
	/**
	 * Test method to check if the Elevator's state is CLOSE_DOOR when handleOpenDoor() is invoked.
	 */
	@Test
	void testElevatorHandleOpenDoor() {
		try {
			Elevator.handleOpenDoor();
			assertEquals(Elevator.getState(), ElevatorState.CLOSE_DOOR);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method to check if the Elevator's state if IDLE when handleCloseDoor() is invoked.
	 */
	@Test
	void testElevatorHandleCloseDoor() {
		try {
			Elevator.handleCloseDoor();
			assertEquals(Elevator.getState(), ElevatorState.IDLE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method to check if the Elevator's state is IDLE when handleMoving() is invoked.
	 */
	@Test
	void testElevatorHandleMovingWithNoMoveToMessage() {
		try {
			Elevator.handleMoving();
			assertEquals(Elevator.getState(), ElevatorState.IDLE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testElevatorHandleMovingWithMoveMessage() throws InterruptedException{
		int dest = 5;
		Elevator.addRequest(new MoveToMessage(new Date(), dest, new ArrayList<>()));
		Elevator.checkMessage();
		assertEquals(ElevatorState.MOVING, Elevator.getState());
		assertEquals(dest, Elevator.getElevatorStatus().getNextDestination());
		if (dest > Elevator.getElevatorStatus().getCurrentFloor()){
			for (int i = Elevator.getElevatorStatus().getCurrentFloor(); i < dest; i++ ){
				Elevator.handleMoving();
			}
		}else{
			for (int i = Elevator.getElevatorStatus().getCurrentFloor(); i > dest; i-- ){
				Elevator.handleMoving();
			}
		}
		assertTrue(Elevator.getElevatorStatus().getCurrentFloor() == dest);
		assertEquals(ElevatorState.OPEN_DOOR, Elevator.getState());
	}
	
	/**
	 * Test method to check if the Upcoming Buttons are added to the HashMap of buttons.
	 */
	@Test
	void testElevatorAddUpcomingButtons() {
		ArrayList<Integer> buttons = new ArrayList<>();
		Elevator.addUpcomingButtons(2, buttons);
		HashMap<Integer, ArrayList<Integer>> floorInputButtonsHashMap = new HashMap<>();
		floorInputButtonsHashMap.put(2, buttons);
		assertEquals(Elevator.getFloorInputButtons().size(), 0);
	}
	
	/**
	 * Test method to check the Elevator responses.
	 */
	@Test
	void testElevatorGetResponses() {
		assertEquals(Elevator.getResponses(), responses);
	}
	
	/**
	 * Test method to check if the Elevator requests are initially empty.
	 */
	@Test
	void testElevatorGetRequests() {
		assertTrue(Elevator.getRequests().isEmpty());
	}
	
	/**3
	 * Test method to get the Elevator destinations.
	 */
	@Test
	void testElevatorGetDestinations() {
		ArrayList<Integer>destinations = new ArrayList<>();
		assertEquals(Elevator.getDestinations(), destinations);
	}
	
	/**
	 * Test method to get the Elevator intial statuses.
	 */
	@Test
	void testElevatorGetElevatorStatus() {
		assertEquals(Elevator.getElevatorStatus().getMotorDirection(), MotorDirection.IDLE);
		assertEquals(Elevator.getElevatorStatus().getNumberOfPassengers(), 0);
		assertEquals(Elevator.getElevatorStatus().getNextDestination(), 0);
	}
	
	/**
	 * Test method to check the Elevator's intial state.
	 */
	@Test
	void testElevatorGetState() {
		assertEquals(Elevator.getState(), ElevatorState.IDLE);
	}
	
	/**
	 * Test method to check the size of the Elevator lamps.
	 */
	@Test
	void testElevatorGetLamps() {
		assertEquals(Elevator.getLamps().length, 22);
	}
	
	/**
	 * Test method to check the Elevator's system name.
	 */
	@Test
	void testElevatorGetSystemName() {
		assertEquals(Elevator.getSystemName(), "Elevator[0]");
	}
	
	/**
	 * Test method to get the the Elevator Id.
	 */
	@Test
	void testElevatorGetId() {
		assertEquals(Elevator.getId(), 0);
	}
	
	/**
	 * Test method to check if the floor input buttons are added to the HashMap.
	 */
	@Test
	void testElevatorGetFloorInputButtons() {
		HashMap<Integer, ArrayList<Integer>> floorInputButtons = new HashMap<>();
		assertEquals(Elevator.getFloorInputButtons(), floorInputButtons);
	}

	// Error cases 
	@Test 
	void testFeedElevatorRandomMessage(){
		ArrivalMessage message = new ArrivalMessage(new Date(), 0, MotorDirection.DOWN);
		Elevator.addRequest(message);
		boolean result = Elevator.checkMessage();
		assertFalse(result);
		assertEquals(ElevatorState.IDLE, Elevator.getState());
	}

	@Test
	void FeedElevatorNoMessage(){
		boolean result = Elevator.checkMessage();
		assertFalse(result);
		assertEquals(ElevatorState.IDLE, Elevator.getState());
	}
}
