package project.statesImpl.SchedulerStates;

import project.schedulerImpl.SchedulerMidTask;
import project.statesImpl.State;

public class SchedulerMidTaskIdleState extends State{
    private SchedulerMidTask schedulerMidTask; 
    
    public SchedulerMidTaskIdleState(SchedulerMidTask schedulerMidTask){
        super();
        this.schedulerMidTask = schedulerMidTask;
    }

    @Override
    public State handleState() {
        this.schedulerMidTask.receive();
        return this.schedulerMidTask.getProcessElevatorState(); 
    }
    
}
