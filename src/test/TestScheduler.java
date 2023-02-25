package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project.messageSystem.ElevatorSubSystemMessageQueue;
import project.messageSystem.FloorMessageQueue;
import project.scheduler.Scheduler;

/**
 * @author elish
 *
 */
class TestScheduler {
	FloorMessageQueue fMQ = new FloorMessageQueue();
	ElevatorSubSystemMessageQueue eMQ = new ElevatorSubSystemMessageQueue();
	
	Scheduler scheduler = new Scheduler(fMQ, eMQ, "Scheduler");
	
	
	@Test
	void testSchedulerIsDead() {
		assertFalse(scheduler.getIsDead());
	}	
	
	@Test
	void testSchedulerElevatorSubSystemMessageQueue() {
		assertEquals(scheduler.getElevatorSubSystemMessageQueue(), eMQ);
	}	
	
	@Test
	void testSchedulerFloorMessageQueue() {
		assertEquals(scheduler.getFloorMessageQueue(), fMQ);
	}	
	
	@Test
	void testSchedulerSystemName() {
		assertEquals(scheduler.getSystemName(), "Scheduler");
	}

}
