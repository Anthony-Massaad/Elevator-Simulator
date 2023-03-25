package project.Timing;

import project.constants.SimulationConstants;
import project.statesImpl.State;

/**
 * Timing class 
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class Timing implements Runnable{
    private int duration; 
    private int increments;
    private State callingState; 

    /**
     * Constructor for the timing class
     * @param state State, the state that started the time
     * @param duration Integer, the actual duration  
     * @param increments Integer, the number of periods
     */
    public Timing(State state, int duration, int increments){
        this.duration = duration;
        this.increments = increments;
        this.callingState = state;
    }

    /**
     * orverriden run method
     */
    @Override
    public void run(){
        while (this.increments > 0){
            synchronized(this.callingState){
                try{
                    // sleep for the expected durection
                    Thread.sleep(SimulationConstants.MAX_ELEVATOR_DURATION);
                    this.increments--; // decrease the increments/peruid
                    this.duration -= SimulationConstants.MAX_ELEVATOR_DURATION; // subtract actual with expected durection
                    if (this.increments == 0 && this.duration > 0){
                        System.out.println("Increment finished. Duration not hit");
                        // increment finished but the duration was no accurate
                        // hard fault, kill elevator
                        this.callingState.die();
                        this.callingState.notifyAll();
                    }else if (this.increments == 0){
                        System.out.println("Increment finished. Duration hit");
                        // increment finished and duration was hit. keep running
                        this.callingState.notifyAll();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

}
