package project.statesImpl.SchedulerStates;

import project.schedulerImpl.SchedulerMidTask;
import project.statesImpl.State;

/**
 * Idle state for the scheduler mid task
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class SchedulerMidTaskIdleState extends State{
    private SchedulerMidTask schedulerMidTask; 
    
    /**
     * Constructor for the scheduler mid task
     * @param schedulerMidTask SchedulerMidTask, the mid task which is super state
     */
    public SchedulerMidTaskIdleState(SchedulerMidTask schedulerMidTask){
        super();
        this.schedulerMidTask = schedulerMidTask;
    }

    /**
     * Handle state
     * @return State, the new state
     */
    @Override
    public State handleState() {
        this.schedulerMidTask.receive();
        return this.schedulerMidTask.getProcessElevatorState(); 
    }
    
}
