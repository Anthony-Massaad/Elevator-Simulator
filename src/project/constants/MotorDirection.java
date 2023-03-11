package project.constants;

/**
 * Enum for defining the motor directions.
 */
public enum MotorDirection {
	UP, DOWN, IDLE;

	/**
	 * Method for getting the direction via a string.
	 * @param direction A string direction.
	 * @return If direction = "UP", the state is UP. DOWN otherwise.
	 */
	public static MotorDirection getDirection(String direction){
		return direction.equals("UP") ? MotorDirection.UP : MotorDirection.DOWN;
	}
}
