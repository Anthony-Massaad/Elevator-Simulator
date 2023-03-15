package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.constants.FloorButtonState;
import project.floorImpl.Floor;

/**
 * Floor Test Class.
 * @author Elisha Catherasoo, Dorothy Tran
 */
class TestFloor {
	private Floor floor; 
	@BeforeEach
	public void init(){
		floor = new Floor(0, FloorButtonState.ACTIVE, FloorButtonState.NOT_ACTIVE, "Floor [0]");
	}
	
	/**
	 * Test method to check if the initial floor number is 0.
	 */
	@Test
	void testFloorNumber() {
		assertEquals(0, floor.getFloorNumber());
	}
	
	/**
	 * Test method to check the Floor Button states when active and inactive when the floor goes up.
	 */
	@Test
	void testFloorUpBtnState() {
		assertEquals(floor.getUpBtn(), FloorButtonState.ACTIVE);
		floor.setUpBtn(FloorButtonState.NOT_ACTIVE);
		assertEquals(floor.getUpBtn(), FloorButtonState.NOT_ACTIVE);
	}
	
	/**
	 * Test method to check the Floor Button states when active and inactive when the floor goes down.
	 */
	@Test
	void testFloorDownBtnState() {
		assertEquals(floor.getDownBtn(), FloorButtonState.NOT_ACTIVE);
		floor.setDownBtn(FloorButtonState.ACTIVE);
		assertEquals(floor.getDownBtn(), FloorButtonState.ACTIVE);
	}

	@Test
	void testFloorDownLamp(){
		assertFalse(floor.getDownLamp());
		floor.setDownLamp(true);
		assertTrue(floor.getDownLamp());
	}

	@Test
	void testFloorUpLamp(){
		assertFalse(floor.getUpLamp());
		floor.setUpLamp(true);
		assertTrue(floor.getUpLamp());
	}
	
}
