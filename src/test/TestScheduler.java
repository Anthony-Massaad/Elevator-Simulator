package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.constants.Addresses;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorStatus;
import project.messageSystem.messages.FloorRequestElevator;
import project.messageSystem.messages.UpdatePositionMessage;
import project.schedulerImpl.Scheduler;
import project.statesImpl.SchedulerStates.SchedulerIdleState;
import project.statesImpl.SchedulerStates.SchedulerProcessFloorState;

/**
 * Scheduler Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestScheduler {

	private Scheduler scheduler;
	
	@BeforeEach
	public void init(){
		scheduler = new Scheduler(SimulationConstants.SCHEDULER_PORT, "Scheduler", Addresses.DEFAULT.getAddress());
	}
	
	/**
	 * Test method to check the Elevator Statuses.
	 */
	@Test
	void testSchedulerElevatorStatuses() {
		ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses = new ConcurrentHashMap<>();
		assertEquals(scheduler.getElevatorStatuses(), elevatorStatuses);
	}
	
	/**
	 * Test method for the selectElevator() method.
	 */
	@Test
	void testSchedulerSelectElevator() {
		for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
			scheduler.addElevatorStatus(i, new ElevatorStatus());
        }
		assertEquals(scheduler.selectElevator(6, MotorDirection.UP), 0);
	}

	/**
	 * test the scheduler provided a valid message
	 */
	@Test
	void testSchedulerStateValidMessage(){
		scheduler.setReceivedMessage(new FloorRequestElevator(new Date(), 0, MotorDirection.IDLE, new ArrayList<>()));
		scheduler.setCurrentState(scheduler.getProcessFloorState());
		project.statesImpl.State state = scheduler.getCurrentState();
		
		assertTrue(state instanceof SchedulerProcessFloorState);
	}

	/**
	 * test the scheduler reset received message
	 */
	@Test
	void testSchedulerReset(){
		scheduler.reset();
		assertNull(scheduler.getReceviedMessage());
	}
	
	/**
	 * test the scheduler initial state
	 */
	@Test
	void testInitialState(){
		assertTrue(scheduler.getCurrentState() instanceof SchedulerIdleState);
	}

	/**
	 * Test the scheduler provided a valid message
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	void testSchedulerAftermathOfValidMessage() throws IOException, InterruptedException{
		for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
			scheduler.addElevatorStatus(i, new ElevatorStatus());
        }
		scheduler.setReceivedMessage(new FloorRequestElevator(new Date(), 0, MotorDirection.IDLE, new ArrayList<>()));
		scheduler.setCurrentState(scheduler.getProcessFloorState());
		assertTrue(scheduler.getCurrentState() instanceof SchedulerProcessFloorState);
		scheduler.setCurrentState(scheduler.getCurrentState().handleState());
		assertTrue(scheduler.getCurrentState() instanceof SchedulerIdleState);
	}

	// Error Cases
	/**
	 * Test scheduler provided a null message
	 * expected to continue
	 */
	@Test
	void testNullMessageCase(){
		scheduler.setCurrentState(scheduler.getProcessFloorState());
		assertTrue(scheduler.getCurrentState() instanceof SchedulerProcessFloorState);
		scheduler.setCurrentState(scheduler.getCurrentState().handleState());
		assertTrue(scheduler.getCurrentState() instanceof SchedulerIdleState);
	}

	/**
	 * Test scheduler provided a invalid message
	 * expected to continue
	 */
	@Test
	void testInvalidMessageCase(){
		scheduler.setReceivedMessage(new UpdatePositionMessage(new Date(), 0, 0, 0, 0, MotorDirection.DOWN, false, new ArrayList<>(), ""));
		scheduler.setCurrentState(scheduler.getProcessFloorState());
		assertTrue(scheduler.getCurrentState() instanceof SchedulerProcessFloorState);
		scheduler.setCurrentState(scheduler.getCurrentState().handleState());
		assertTrue(scheduler.getCurrentState() instanceof SchedulerIdleState);
	}
}
