package project.statesImpl;

/**
 * State abstract class
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public abstract class State {
    protected boolean isDieCalled;
    
    /**
     * State constructor
     */
    public State(){
        this.isDieCalled = false;
    }
    
    /**
     * Handle the current state
     * @return State, the new state
     */
    public abstract State handleState(); 

    /**
     * kill message sent
     */
    public void die(){
        this.isDieCalled = true; 
    }
    
}
