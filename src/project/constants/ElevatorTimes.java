package project.constants;

/**
 * The Logged times from iteration 2 of the elevator 
 * Arbitrary for now
 * @author Anthony Massaad, Dorothy Tran, Max Curkovic, Elisha Catherasoo, Cassidy Pacada SYSC 3303 Winter 2023 Lab A1
 *
 */
public enum ElevatorTimes {
	MOVING(1000), CLOSING_DOOR(500), OPEN_DOOR(500);
	
	private final int time; 
	ElevatorTimes(int time){
		this.time = time;
	}
	
	public int getTime() {
		return this.time; 
	}
}
