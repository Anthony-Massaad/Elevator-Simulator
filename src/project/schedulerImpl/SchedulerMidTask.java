package project.schedulerImpl;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import project.constants.SchedulerState;
import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorStatus;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.ElevatorLeavingMessage;
import project.messageSystem.messages.UpdatePositionMessage;
import project.udp.UDPBoth;

/**
 * Class for communication between the elevator and the floor
 */
public class SchedulerMidTask extends UDPBoth implements Runnable{

    private ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses;
    private SchedulerState state; 
    private Message receivedMessage; 
    public SchedulerMidTask(int port, String systemName, ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses) {
        super(port, systemName);
        this.elevatorStatuses = elevatorStatuses;
        this.state = SchedulerState.IDLE;
        this.receivedMessage = null;
    }

    private void processMessage() throws IOException, InterruptedException {
        if (this.receivedMessage instanceof UpdatePositionMessage) {
            UpdatePositionMessage updatePositionMessage = (UpdatePositionMessage) this.receivedMessage;
            this.elevatorStatuses.get(updatePositionMessage.getElevatorID()).update(updatePositionMessage.getDirection(), updatePositionMessage.getCurrentFloor(), updatePositionMessage.getNumberOfPassengers(), updatePositionMessage.getNextDestination());
            Log.notification("SCHEDULER MID TASK", updatePositionMessage.toString(), new Date(), this.systemName);
            System.out.println("elevator Position update blah blah blah floor: " + this.elevatorStatuses.get(updatePositionMessage.getElevatorID()).getCurrentFloor());
        }else if (this.receivedMessage instanceof ArrivalMessage) {
            ArrivalMessage arrivalMessage = (ArrivalMessage) this.receivedMessage;
            this.send(arrivalMessage, SimulationConstants.FLOOR_MANAGER_PORT);
            Log.notification("SCHEDULER MID TASK", arrivalMessage.toString(), new Date(), this.systemName);
        }else if (this.receivedMessage instanceof ElevatorLeavingMessage){
            ElevatorLeavingMessage elevatorMoving = (ElevatorLeavingMessage) this.receivedMessage;
            this.send(elevatorMoving, SimulationConstants.FLOOR_MANAGER_PORT);
            Log.notification("SCHEDULER MID TASK", elevatorMoving.toString(), new Date(), this.systemName);
        }else{
            throw new Error("Unknown Message Received or null"); 
        }
        this.state = SchedulerState.IDLE;
        this.receivedMessage = null; 
    }

    /**
     * Overriden run method to implement the Scheduler's mid task.
     */
    @Override
    public void run() {
        try{
            while (true){
                if (this.state == SchedulerState.IDLE){
                    this.receivedMessage = this.receive(SimulationConstants.BYTE_SIZE);
                    this.state = SchedulerState.PROCESSING;
                }
                
                if (this.state == SchedulerState.PROCESSING){
                    this.processMessage();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
