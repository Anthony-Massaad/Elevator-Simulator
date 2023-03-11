package project.schedulerImpl;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorStatus;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.UpdatePositionMessage;
import project.udp.UDPBoth;

public class SchedulerMidTask extends UDPBoth implements Runnable{

    private ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses;
    public SchedulerMidTask(int port, String systemName, ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses) {
        super(port, systemName);
        this.elevatorStatuses = elevatorStatuses;
    }

    /**
     * Overriden run method to implement the Scheduler's mid task.
     */
    @Override
    public void run() {
        try{
            while (true){
                Message message = this.receive(SimulationConstants.BYTE_SIZE);
                if (message instanceof UpdatePositionMessage) {
                    UpdatePositionMessage updatePositionMessage = (UpdatePositionMessage) message;
                    this.elevatorStatuses.get(updatePositionMessage.getElevatorID()).update(updatePositionMessage.getDirection(), updatePositionMessage.getCurrentFloor(), updatePositionMessage.getNumberOfPassengers(), updatePositionMessage.getNextDestination());
                    Log.notification("SCHEDULER MID TASK", updatePositionMessage.toString(), new Date(), this.systemName);
                }else if (message instanceof ArrivalMessage) {
                    ArrivalMessage arrivalMessage = (ArrivalMessage) message;
                    this.send(arrivalMessage, SimulationConstants.FLOOR_MANAGER_PORT);
                    Log.notification("SCHEDULER MID TASK", arrivalMessage.toString(), new Date(), this.systemName);
                }else{
                    throw new Error("Unknown Message Received"); 
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
