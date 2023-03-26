package project.statesImpl.SchedulerStates;

import project.schedulerImpl.Scheduler;
import project.statesImpl.State;

/**
 * Scheduler idle state
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class SchedulerIdleState extends State{

    private Scheduler scheduler; 
    
    /**
     * constructor for the elevator idle state
     * @param scheduler Scheduler, the scheduler which is the super state
     */
    public SchedulerIdleState(Scheduler scheduler){
        super();
        this.scheduler = scheduler; 
    }

    /**
     * Handle the current state
     * @return State, the current state
     */
    @Override
    public State handleState() {
        this.scheduler.receive();
        return this.scheduler.getProcessFloorState(); 
    }
    
}
