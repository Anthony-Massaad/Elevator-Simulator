package project.statesImpl.elevatorStates;

import project.statesImpl.State;

public class ElevatorOpenDoorState extends State{
    protected static State instance; 

    public ElevatorOpenDoorState(){
        instance = new ElevatorOpenDoorState();
    }

    protected static State getInstance(){
        return instance;
    }


    @Override
    public State handleState() {
        return null; 
    }
    
}
