package project.statesImpl.SchedulerStates;

import java.util.Date;

import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.messages.FloorRequestElevator;
import project.messageSystem.messages.RequestElevatorMessage;
import project.schedulerImpl.Scheduler;
import project.statesImpl.State;

public class SchedulerProcessFloorState extends State {

    private Scheduler scheduler; 

    public SchedulerProcessFloorState(Scheduler scheduler){
        this.scheduler = scheduler; 
    }

    @Override
    public State handleState() {
        if (this.scheduler.getReceviedMessage() == null){
            return null;
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
    
}
