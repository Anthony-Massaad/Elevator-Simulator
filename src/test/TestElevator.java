package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.Timing.MeasureTime;
import project.constants.MotorDirection;
import project.messageSystem.Message;
import project.messageSystem.messages.MoveToMessage;
import project.messageSystem.messages.UpdatePositionMessage;
import project.statesImpl.elevatorStates.ElevatorBrokenState;
import project.statesImpl.elevatorStates.ElevatorCloseDoorState;
import project.statesImpl.elevatorStates.ElevatorDoorFaultState;
import project.statesImpl.elevatorStates.ElevatorIdleState;
import project.statesImpl.elevatorStates.ElevatorMovingState;
import project.statesImpl.elevatorStates.ElevatorOpenDoorState;
import project.elevatorImpl.Elevator;

/**
 * Elevator Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestElevator {
	
	private Elevator Elevator; 
	private ConcurrentLinkedDeque<Message> responses;
	MeasureTime measure = new MeasureTime();
	@BeforeEach
	public void init(){
		System.out.println("Starting Up");
		responses = new ConcurrentLinkedDeque<>();
		// Message message = new Message(new Date(), "Elevator has arrived");
		Elevator = new Elevator(0, "Elevator[0]", responses);
	}
	
	@Test
	void testElevatorSort() {
		ArrayList<Integer> tempDest = new ArrayList<>();
		tempDest.add(10);
		tempDest.add(7);
		tempDest.add(3);
		Elevator.setDestinations(tempDest);
		Elevator.sort(false);
		
		ArrayList<Integer> destinations = new ArrayList<>();
		destinations.add(3);
		destinations.add(7);
		destinations.add(10);
		
		assertEquals(Elevator.getDestinations(), destinations);
	}
	
	/**
	 * Test method to check if the Elevator's state is CLOSE_DOOR when handleOpenDoor() is invoked.
	 */
	@Test
	void testElevatorUpdateMotorStatus() {
		Elevator.updateMotorStatus();
		
		assertEquals(Elevator.getElevatorStatus().getMotorDirection(), MotorDirection.DOWN);
		assertFalse(Elevator.getDirectionLampUp());
		assertTrue(Elevator.getDirectionLampDown());
	}
	
	/**
	 * Test method to check if the Upcoming Buttons are added to the HashMap of buttons.
	 */
	@Test
	void testElevatorAppendButtonsToExistingList() {
		ArrayList<Integer> curr = new ArrayList<>();
		curr.add(1);
		curr.add(3);
		curr.add(7);
		
		ArrayList<Integer> toAdd = new ArrayList<>();
		toAdd.add(12);
		toAdd.add(5);
		
		ArrayList<Integer> btnsList = new ArrayList<>();
		btnsList.add(1);
		btnsList.add(3);
		btnsList.add(7);
		btnsList.add(12);
		btnsList.add(5);
		
		assertEquals(Elevator.appendButtonsToExistingList(curr, toAdd), btnsList);
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
	 * Test method to check if the floor input buttons are added to the HashMap.
	 */
	@Test
	void testElevatorGetFloorInputButtons() {
		HashMap<Integer, ArrayList<Integer>> floorInputButtons = new HashMap<>();
		assertEquals(Elevator.getFloorInputButtons(), floorInputButtons);
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
	 * test the initial state of elevator
	 */
	@Test
	void testInitialState(){
		assertTrue(Elevator.getCurrentState() instanceof ElevatorIdleState);
	}

	/**
	 * Test the close door state
	 */
	@Test
	void testCloseDoorState(){
		Elevator.setCurrentState(Elevator.getElevatorDoorCloseState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorCloseDoorState);
		Elevator.setCurrentState(Elevator.getCurrentState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorIdleState);
	}

	/**
	 * test the open door state
	 */
	@Test 
	void testElevatorOpenDoorState(){
		Elevator.setCurrentState(Elevator.getElevatorDoorOpenState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorOpenDoorState);
		Elevator.setCurrentState(Elevator.getCurrentState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorCloseDoorState);
	}

	/** 
	 * Test the moving state
	 */
	@Test
	void testElevatorMovingState(){
		Elevator.setCurrentState(Elevator.getElevatorMovingState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorMovingState);
	}

	// Faults Testing 

	/**
	 * Test the transient fault of close door
	 * Expected to recover
	 */
	@Test
	void testTransientFaultCloseDoor(){
		Elevator.getDestinations().add(0);
		Elevator.setCurrentState(Elevator.getElevatorDoorCloseState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorCloseDoorState);
		Elevator.setCurrentState(Elevator.getCurrentState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorDoorFaultState);
		Elevator.setCurrentState(Elevator.getCurrentState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorCloseDoorState);
	}

	/**
	 * Test the transient fault of open door
	 * Expected to recover
	 */
	@Test
	void testTransientFaultOpenDoor(){
		Elevator.getDestinations().add(-1);
		Elevator.setCurrentState(Elevator.getElevatorDoorOpenState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorOpenDoorState);
		Elevator.setCurrentState(Elevator.getCurrentState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorDoorFaultState);
		Elevator.setCurrentState(Elevator.getCurrentState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorOpenDoorState);
	}

	/**
	 * Test the Hard fault while moving
	 * Expected to die
	 */
	@Test
	void testHardFaultElevatorCrash(){
		Elevator.getElevatorStatus().setNextDestination(100);
		Elevator.setCurrentState(Elevator.getElevatorMovingState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorMovingState);
		Elevator.setCurrentState(Elevator.getCurrentState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorBrokenState);
	}

	/**
	 * test elevator provided valid message
	 */
	@Test
	void testHandleValidMessage(){
		Message msg = new MoveToMessage(new Date(), 5, new ArrayList<Integer>(){{add(1);}}, MotorDirection.DOWN);
		Elevator.addRequest(msg);
		assertTrue(Elevator.getCurrentState() instanceof ElevatorIdleState);
		Elevator.setCurrentState(Elevator.getProcessingState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorMovingState);
	}

	// Handling Error Cases
	/**
	 * test elevator provided an invalid input 
	 * expected to not break
	 */
	@Test
	void testHandleInvalidMessageInput(){
		Message msg = new UpdatePositionMessage(new Date(), 0, 0, 0, 0, MotorDirection.UP, false, new ArrayList<>(), "");
		Elevator.addRequest(msg);
		assertTrue(Elevator.getCurrentState() instanceof ElevatorIdleState);
		Elevator.setCurrentState(Elevator.getProcessingState().handleState());
		assertTrue(Elevator.getCurrentState() instanceof ElevatorIdleState);
	}
}
