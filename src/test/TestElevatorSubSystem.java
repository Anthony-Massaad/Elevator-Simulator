package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorSubSystem;

/**
 * Elevator Subsystem Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestElevatorSubSystem {
	ElevatorSubSystem eSubSystem = new ElevatorSubSystem(SimulationConstants.ELEVATOR_MANAGER_PORT, "ElevatorSubSystem");
	
	/**
	 * Test method to check if the Elevator SubSystem's intital responses are empty.
	 */
	@Test
	void testElevatorSubSystemGetElevatorResponses() {
		assertTrue(eSubSystem.getElevatorResponses().isEmpty());
	}
	
	/**
	 * Test method for the getIsDead() getter method.
	 */
	@Test
	void testElevatorSubSystemGetIsDead() {
		assertEquals(eSubSystem.getIsDead(), false);
	}
	
	/**
	 * Test method to check the number of elevators of the Elevator SubSystem.
	 */
	@Test
	void testElevatorSubSystemGetElevators() {
		assertEquals(eSubSystem.getElevators().length, SimulationConstants.NUM_OF_ELEVATORS);
	}
}
