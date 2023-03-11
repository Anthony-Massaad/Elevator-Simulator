package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;

import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorStatus;
import project.schedulerImpl.Scheduler;

/**
 * Scheduler Test Class.
 * @author Elisha Catherasoo
 */
class TestScheduler {
	Scheduler scheduler = new Scheduler(SimulationConstants.SCHEDULER_PORT, "Scheduler");
	
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
}
