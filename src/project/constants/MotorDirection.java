package project.constants;

/**
 * Enum for defining the motor directions.
 * @author Anthony Massaad SYSC3303 group 2
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

	/**
	 * convert the motor direction to a string output
	 * @param direction MotorDirection, the direction
	 * @return String, the direction as String
	 */
	public static String toString(MotorDirection direction){
		if (direction == MotorDirection.UP){
			return "UP";
		}else if (direction == MotorDirection.DOWN){
			return "DOWN";
		}else{
			return "IDLE";
		}
	}

}
