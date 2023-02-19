package project.constants;

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
