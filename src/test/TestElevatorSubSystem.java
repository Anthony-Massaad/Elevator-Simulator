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
	
	@Test
	void testElevatorSubSystemGetElevatorResponses() {
		assertTrue(eSubSystem.getElevatorResponses().isEmpty());
	}
	
	@Test
	void testElevatorSubSystemGetIsDead() {
		assertEquals(eSubSystem.getIsDead(), false);
	}
	
	@Test
	void testElevatorSubSystemGetElevators() {
		assertEquals(eSubSystem.getElevators().length, 4);
	}
}
