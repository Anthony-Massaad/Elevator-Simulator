package project.constants;

/**
 * Enum for defining the Time states and their integer time values.
 * @author Anthony Massaad SYSC3303 Group 2
 */
public enum Time {
	OPEN_DOOR(1200), CLOSE_DOOR(1200), MOVE(1000), LOAD_PASSENGERS(1000), UNLOAD_PASSENGERS(1000);
	
	private final int time; 
	Time(int time){
		this.time = time; 
	}
	
	public int getTime() {
		return this.time; 
	}
}
