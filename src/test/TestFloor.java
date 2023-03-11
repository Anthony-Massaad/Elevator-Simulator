package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project.simulationParser.*;
import project.constants.FloorButtonState;
import project.floorImpl.Floor;
import project.messageSystem.*;

/**
 * Floor Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestFloor {
	Floor floor = new Floor(0, FloorButtonState.ACTIVE, FloorButtonState.NOT_ACTIVE, "Floor [0]");
	
	@Test
	void testFloorNumber() {
		assertEquals(0, floor.getFloorNumber());
	}
	
	@Test
	void testFloorUpBtnState() {
		assertEquals(floor.getUpBtn(), FloorButtonState.ACTIVE);
		
		floor.setUpBtn(FloorButtonState.NOT_ACTIVE);
		assertEquals(floor.getUpBtn(), FloorButtonState.NOT_ACTIVE);
	}
	
	@Test
	void testFloorDownBtnState() {
		assertEquals(floor.getDownBtn(), FloorButtonState.NOT_ACTIVE);
		
		floor.setDownBtn(FloorButtonState.ACTIVE);
		assertEquals(floor.getDownBtn(), FloorButtonState.ACTIVE);
	}
	
}
