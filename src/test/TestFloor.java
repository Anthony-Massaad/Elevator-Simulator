package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project.floorSubSystem.Floor;
import project.simulationParser.*;
import project.messageSystem.*;

/**
 * @author elish
 *
 */
class TestFloor {
	Parser parser = new Parser();
	FloorMessageQueue fMQ = new FloorMessageQueue();
	
	Floor floor = new Floor(parser, fMQ, 0, "Floor[0]");
	
	
	@Test
	void testFloorIsDead() {
		assertFalse(floor.getIsDead());
	}
	
	@Test
	void testFloorParser() {
		assertEquals(floor.getParser(), parser);
	}
	
	@Test
	void testFloorMessageQueue() {
		assertEquals(floor.getMessageQueue(), fMQ);
	}
	
	@Test
	void testFloorSystemName() {
		assertEquals(floor.getSystemName(), "Floor[0]");
	}
	
	@Test
	void testFloorFloorNumber() {
		assertEquals(floor.getFloorNumber(), 0);
	}
	
	@Test
	void testFloorRequestSent() {
		assertFalse(floor.getRequestSent());
	}
}
