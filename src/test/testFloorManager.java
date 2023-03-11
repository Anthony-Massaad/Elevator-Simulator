/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;

import project.constants.FloorButtonState;
import project.constants.SimulationConstants;
import project.floorImpl.Floor;
import project.floorImpl.FloorManager;
import project.simulationParser.Parser;

/**
 * @author elish
 *
 */
public class testFloorManager {
	Parser p = new Parser(); 
    FloorManager manager = new FloorManager(SimulationConstants.FLOOR_MANAGER_PORT, "Floor Manager", p.getEventLines());
    
    @Test
	void testFloorManagerGetFloors() {
    	ConcurrentHashMap<Integer, Floor> floors = new ConcurrentHashMap<>();
    	assertEquals(floors, manager.getFloors());
    }
    
    @Test
	void testFloorManagerGetEvents() {
    	ArrayList<String> events = p.getEventLines();
    	
    	assertEquals(events, manager.getEvents());
    	
    }
    
    @Test
	void testFloorManagerInitializeFloors() {
    	manager.initializeFloors();
    	
    	assertEquals(1, manager.getFloors().get(0).getFloorNumber());
    	assertEquals(FloorButtonState.NOT_ACTIVE, manager.getFloors().get(0).getUpBtn());
    	assertEquals(FloorButtonState.NON_EXISTENT, manager.getFloors().get(0).getDownBtn());
    	
    	assertEquals(22, manager.getFloors().get(21).getFloorNumber());
    	assertEquals(FloorButtonState.NON_EXISTENT, manager.getFloors().get(21).getUpBtn());
    	assertEquals(FloorButtonState.NOT_ACTIVE, manager.getFloors().get(21).getDownBtn());
    	
    	assertEquals(4, manager.getFloors().get(3).getFloorNumber());
    	assertEquals(FloorButtonState.NOT_ACTIVE, manager.getFloors().get(3).getUpBtn());
    	assertEquals(FloorButtonState.NOT_ACTIVE, manager.getFloors().get(3).getDownBtn());
    	
    	assertEquals(12, manager.getFloors().get(11).getFloorNumber());
    	assertEquals(FloorButtonState.NOT_ACTIVE, manager.getFloors().get(11).getUpBtn());
    	assertEquals(FloorButtonState.NOT_ACTIVE, manager.getFloors().get(11).getDownBtn());
    	
    }
}
