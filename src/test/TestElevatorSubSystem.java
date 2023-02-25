/**
 * 
 */
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
	void testElevatorMessageQueue() {
		assertEquals(eSubSystem.getMessageQueue(), esMQ);
	}
	
	@Test
	void testElevatorIsDead() {
		assertFalse(eSubSystem.getIsDead());
	}
	
	@Test
	void testElevatorSystemName() {
		assertEquals(eSubSystem.getSystemName(), "ElevatorSubSystem");
	}
	
	@Test
	void testElevatorResponses() {
		assertTrue(eSubSystem.getElevatorResponses().isEmpty());
	}
	
}
