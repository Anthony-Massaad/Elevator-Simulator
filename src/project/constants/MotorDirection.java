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

	/**
	 * get the opposite of the direction given
	 * @param direction MotorDirection, the direction
	 * @return MotorDirection, the opposite direaction
	 */
	public static MotorDirection oppositeDirection(MotorDirection direction){
		return direction == MotorDirection.DOWN ? MotorDirection.UP : MotorDirection.DOWN;
	}

	public static String toString(MotorDirection direction){
		return direction == MotorDirection.DOWN ? "DOWN" : "UP";
	}

}
