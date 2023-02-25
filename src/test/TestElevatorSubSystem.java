package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project.elevatorSubSystem.ElevatorSubSystem;
import project.messageSystem.ElevatorSubSystemMessageQueue;

/**
 * @author elish
 *
 */
class TestElevatorSubSystem {
	ElevatorSubSystemMessageQueue esMQ = new ElevatorSubSystemMessageQueue();
	ElevatorSubSystem eSubSystem = new ElevatorSubSystem(esMQ, "ElevatorSubSystem");
	
	@Test
	void testElevatorSubSystemMessageQueue() {
		assertEquals(eSubSystem.getMessageQueue(), esMQ);
	}
	
	@Test
	void testElevatorSubSystemIsDead() {
		assertFalse(eSubSystem.getIsDead());
	}
	
	@Test
	void testElevatorSubSystemName() {
		assertEquals(eSubSystem.getSystemName(), "ElevatorSubSystem");
	}
	
	@Test
	void testElevatorSubSystemResponses() {
		assertTrue(eSubSystem.getElevatorResponses().isEmpty());
	}
	
}
