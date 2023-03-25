package project.statesImpl;

public abstract class State {
    protected boolean isDieCalled;
    
    public State(){
        this.isDieCalled = false;
    }
    
    public abstract State handleState(); 

    public void die(){
        this.isDieCalled = true; 
    }
    
}
