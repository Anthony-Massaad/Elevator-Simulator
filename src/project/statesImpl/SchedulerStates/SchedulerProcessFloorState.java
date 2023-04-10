package project.statesImpl.SchedulerStates;

import java.util.Date;

import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.messages.FloorRequestElevator;
import project.messageSystem.messages.RequestElevatorMessage;
import project.schedulerImpl.Scheduler;
import project.statesImpl.State;

/**
 * Scheduler process Floor state
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class SchedulerProcessFloorState extends State {

    private Scheduler scheduler; 

    /**
     * constructor for process floor state
     * @param scheduler Scheduler, the scheduler super state
     */
    public SchedulerProcessFloorState(Scheduler scheduler){
        super();
        this.scheduler = scheduler; 
    }

    /**
     * Handle sate 
     * @return State, the new state
     */
    @Override
    public State handleState() {
        if (this.scheduler.getReceviedMessage() == null){
            return this.scheduler.getIdleState();
        }else {
            if (this.scheduler.getReceviedMessage() instanceof FloorRequestElevator){
                FloorRequestElevator requestMessage = (FloorRequestElevator) this.scheduler.getReceviedMessage();
                int elevatorID = this.scheduler.selectElevator(requestMessage.getFloorNumber(), requestMessage.getDirection());
                Log.notification("SCHEDULER", requestMessage.toString(), new Date(), this.scheduler.getSystemName());
                RequestElevatorMessage msg = new RequestElevatorMessage(requestMessage.getTimeStamp(), requestMessage.getFloorNumber(), elevatorID, requestMessage.getButtonsToBePressed(), requestMessage.getDirection());
                this.scheduler.sendPacket(msg, SimulationConstants.ELEVATOR_MANAGER_PORT);
            }
            this.scheduler.reset();
        }
        return this.scheduler.getIdleState();
    }

    @Override
    public String toString(){
        return "Processing Floor";
    }
    
}
