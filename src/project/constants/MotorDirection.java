package project.constants;

public enum MotorDirection {
	UP, DOWN, IDLE;

	public static MotorDirection getDirection(String direction){
		return direction.equals("UP") ? MotorDirection.UP : MotorDirection.DOWN;
	}
}
