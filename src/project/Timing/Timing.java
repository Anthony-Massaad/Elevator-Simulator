package project.Timing;

import project.constants.SimulationConstants;
import project.statesImpl.State;

public class Timing implements Runnable{
    private int duration; 
    private int increments;
    private State callingState; 

    public Timing(State state, int duration, int increments){
        this.duration = duration;
        this.increments = increments;
        this.callingState = state;
    }

    @Override
    public void run(){
        while (this.increments > 0){
            synchronized(this.callingState){
                try{
                    Thread.sleep(SimulationConstants.MAX_ELEVATOR_DURATION);
                    this.increments--;
                    this.duration -= SimulationConstants.MAX_ELEVATOR_DURATION;
                    if (this.increments == 0 && this.duration > 0){
                        System.out.println("Increment finished. Duration not hit");
                        this.callingState.die();
                        this.callingState.notifyAll();
                    }else if (this.increments == 0){
                        System.out.println("Increment finished. Duration hit");
                        this.callingState.notifyAll();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

}
