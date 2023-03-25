package project.statesImpl.SchedulerStates;

import java.util.Date;

import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.ElevatorLeavingMessage;
import project.messageSystem.messages.UpdatePositionMessage;
import project.schedulerImpl.SchedulerMidTask;
import project.statesImpl.State;

public class SchedulerProcessElevatorState extends State {
    private SchedulerMidTask schedulerMidTask;

    public SchedulerProcessElevatorState(SchedulerMidTask schedulerMidTask){
        super();
        this.schedulerMidTask = schedulerMidTask;
    }

    @Override
    public State handleState() {
        if (this.schedulerMidTask.getReceviedMessage() == null){
            return null;
        }else{
            if (this.schedulerMidTask.getReceviedMessage() instanceof UpdatePositionMessage) {
                UpdatePositionMessage updatePositionMessage = (UpdatePositionMessage) this.schedulerMidTask.getReceviedMessage();
                if (updatePositionMessage.getIsStuck()){
                    Log.notification("SCHEDULER MID TASK", "Elevator " + updatePositionMessage.getElevatorID() + " Stuck received", new Date(), this.schedulerMidTask.getSystemName());
                }
                this.schedulerMidTask.getElevatorStatus().get(updatePositionMessage.getElevatorID()).update(updatePositionMessage.getDirection(), updatePositionMessage.getCurrentFloor(), updatePositionMessage.getNumberOfPassengers(), updatePositionMessage.getNextDestination(), updatePositionMessage.getIsStuck());
                Log.notification("SCHEDULER MID TASK", updatePositionMessage.toString(), new Date(), this.schedulerMidTask.getSystemName());
                this.schedulerMidTask.reset();
            }else if (this.schedulerMidTask.getReceviedMessage() instanceof ArrivalMessage) {
                ArrivalMessage arrivalMessage = (ArrivalMessage) this.schedulerMidTask.getReceviedMessage();
                this.schedulerMidTask.sendPacket(arrivalMessage, SimulationConstants.FLOOR_MANAGER_PORT);
                Log.notification("SCHEDULER MID TASK", arrivalMessage.toString(), new Date(), this.schedulerMidTask.getSystemName());
                this.schedulerMidTask.reset();
            }else if (this.schedulerMidTask.getReceviedMessage() instanceof ElevatorLeavingMessage){
                ElevatorLeavingMessage elevatorMoving = (ElevatorLeavingMessage) this.schedulerMidTask.getReceviedMessage();
                this.schedulerMidTask.sendPacket(elevatorMoving, SimulationConstants.FLOOR_MANAGER_PORT);
                Log.notification("SCHEDULER MID TASK", elevatorMoving.toString(), new Date(), this.schedulerMidTask.getSystemName());
                this.schedulerMidTask.reset();
            }
            this.schedulerMidTask.reset();
        }

        return this.schedulerMidTask.getIdleState(); 
    }
    
}
