package project.constants;

/**
 * Enum that defines the Floor button states.
 */
public enum FloorButtonState {
    ACTIVE, NOT_ACTIVE, NON_EXISTENT;

	/**
	 * Method that parses a state to a certain String active or not active.
	 * @param state A FloorButtonState state.
	 * @return True if active, False otherwise.
	 */
    public static String parseState(FloorButtonState state){
        return state == FloorButtonState.ACTIVE ? "Active" : "Not Active";
    }
}
