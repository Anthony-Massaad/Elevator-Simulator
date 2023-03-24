package project.statesImpl.SchedulerStates;

import project.schedulerImpl.Scheduler;
import project.statesImpl.State;

public class SchedulerIdleState extends State{

    private Scheduler scheduler; 
    
    public SchedulerIdleState(Scheduler scheduler){
        this.scheduler = scheduler; 
    }

    @Override
    public State handleState() {
        this.scheduler.receive();
        return this.scheduler.getProcessFloorState(); 
    }
    
}
