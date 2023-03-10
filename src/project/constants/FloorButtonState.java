package project.constants;

public enum FloorButtonState {
    ACTIVE, NOT_ACTIVE, NON_EXISTENT;

    public static String parseState(FloorButtonState state){
        return state == FloorButtonState.ACTIVE ? "Active" : "Not Active";
    }
}
