package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.junit.jupiter.api.Test;

import project.constants.SimulationConstants;
import project.elevatorImpl.Elevator;
import project.elevatorImpl.ElevatorSubSystem;
import project.messageSystem.Message;

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
		Elevator[] elevators = new Elevator[SimulationConstants.NUM_OF_ELEVATORS];
		assertEquals(eSubSystem.getElevators().length, 4);
	}
}
