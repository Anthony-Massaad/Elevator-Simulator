package project.constants;

/**
 * All necessary constants, including our number of elevators, floors, passengers, and ports.
 * @author Anthony Massaad SYSC3303 Group 2
 */
public class SimulationConstants {
	// values 
	public static final int NUM_OF_ELEVATORS = 4; 
	public static final int NUM_OF_FLOORS = 22; 
	public static final int BYTE_SIZE = 2084; 
	public static final int MAX_PASSENGERS = 8; 

	// ports 
	public static final int SCHEDULER_PORT = 2500; 
	public static final int ELEVATOR_MANAGER_PORT = 1500; 
	public static final int FLOOR_MANAGER_PORT = 3500; 
	public static final int SCHEDULER_MID_TASK_PORT = 3000;
	public static final int GUI_PORT = 4000; 

	// indexes to check within elevator destinations arraylist for stuck inputs
	public static int DOOR_CLOSE_STUCK_INPUT = 0;
	public static int DOOR_CLOSE_STUCK_INDEX = 0;
	public static int DOOR_OPEN_STUCK_INDEX = 0;
	public static int ELEVATOR_BROKEN_FAULT_INPUT = 100; 
	public static int MAX_ELEVATOR_DURATION = Time.MOVE.getTime();

}
