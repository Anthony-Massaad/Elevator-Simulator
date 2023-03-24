// package test;

// import static org.junit.jupiter.api.Assertions.*;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.concurrent.ConcurrentHashMap;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import project.constants.MotorDirection;
// import project.constants.SchedulerState;
// import project.constants.SimulationConstants;
// import project.elevatorImpl.ElevatorStatus;
// import project.messageSystem.messages.FloorRequestElevator;
// import project.messageSystem.messages.MoveToMessage;
// import project.schedulerImpl.Scheduler;

// /**
//  * Scheduler Test Class.
//  * @author Elisha Catherasoo
//  */
// class TestScheduler {

// 	private Scheduler scheduler;
// 	@BeforeEach
// 	public void init(){
// 		scheduler = new Scheduler(SimulationConstants.SCHEDULER_PORT, "Scheduler");
// 	}
	
// 	/**
// 	 * Test method to check the Elevator Statuses.
// 	 */
// 	@Test
// 	void testSchedulerElevatorStatuses() {
// 		ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses = new ConcurrentHashMap<>();
// 		assertEquals(scheduler.getElevatorStatuses(), elevatorStatuses);
// 	}
	
// 	/**
// 	 * Test method for the selectElevator() method.
// 	 */
// 	@Test
// 	void testSchedulerSelectElevator() {
// 		for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
// 			scheduler.addElevatorStatus(i, new ElevatorStatus());
//         }
// 		assertEquals(scheduler.selectElevator(6, MotorDirection.UP), 0);
// 	}

// 	@Test
// 	void testSchedulerInitialState(){
// 		assertEquals(SchedulerState.IDLE, scheduler.getState());
// 	}

// 	@Test
// 	void testSchedulerStateValidMessage(){
// 		scheduler.setReceivedMessage(new FloorRequestElevator(new Date(), 0, MotorDirection.IDLE, new ArrayList<>()));
// 		scheduler.checkState();
// 		assertEquals(SchedulerState.PROCESSING_FLOOR, scheduler.getState());
// 	}

// 	@Test
// 	void testSchedulerAftermathOfValiMessage() throws IOException, InterruptedException{
// 		for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
// 			scheduler.addElevatorStatus(i, new ElevatorStatus());
//         }
// 		scheduler.setReceivedMessage(new FloorRequestElevator(new Date(), 0, MotorDirection.IDLE, new ArrayList<>()));
// 		scheduler.checkState();
// 		assertEquals(SchedulerState.PROCESSING_FLOOR, scheduler.getState());
// 		boolean result = scheduler.processMessage();
// 		assertTrue(result);
// 	}

// 	@Test
// 	void testSchedulerAftermathStateOfValiMessage() throws IOException, InterruptedException{
// 		for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
// 			scheduler.addElevatorStatus(i, new ElevatorStatus());
//         }
// 		scheduler.setReceivedMessage(new FloorRequestElevator(new Date(), 0, MotorDirection.IDLE, new ArrayList<>()));
// 		scheduler.checkState();
// 		assertEquals(SchedulerState.PROCESSING_FLOOR, scheduler.getState());
// 		boolean result = scheduler.processMessage();
// 		assertTrue(result);
// 		assertEquals(SchedulerState.IDLE, scheduler.getState());
// 		assertNull(scheduler.getReceviedMessage());
// 	}

// 	//  Error Casess / failure cases
// 	@Test 
// 	void testSchedulerNullMessage() throws IOException, InterruptedException{
// 		boolean result = scheduler.processMessage();
// 		assertFalse(result);
// 	}

// 	@Test
// 	void testSchedulerInvalidMessage() throws IOException, InterruptedException{
// 		scheduler.setReceivedMessage(new MoveToMessage(new Date(), 0, new ArrayList<>()));
// 		boolean result = scheduler.processMessage();
// 		assertFalse(result);
// 	}
	
// }
