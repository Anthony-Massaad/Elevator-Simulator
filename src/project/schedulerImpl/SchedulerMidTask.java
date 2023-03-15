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

    /**
     * set a message
     * @param msg Message, the message
     */
    public void setReceivedMessage(Message msg){
        this.receivedMessage = msg; 
    }

    /**
     * get the current scheduler state
     * @return state SchedulerState, the current state of the scheduler
     */
    public SchedulerState getState(){
        return this.state; 
    }

    public Message getReceviedMessage(){
        return this.receivedMessage;
    }

    /**
     * check the state of the scheduler
     */
    public void checkState() {
        if (this.receivedMessage == null){
            this.state = SchedulerState.IDLE;
        }else{
            this.state = SchedulerState.PROCESSING_ELEVATOR;
        }
    }

    /**
     * reset the states
     */
    public void reset(){
        this.receivedMessage = null; 
        this.checkState();
    }

    private boolean processMessage() throws IOException, InterruptedException {
        if (this.receivedMessage == null){
            return false;
        }else{
            if (this.receivedMessage instanceof UpdatePositionMessage) {
                UpdatePositionMessage updatePositionMessage = (UpdatePositionMessage) this.receivedMessage;
                this.elevatorStatuses.get(updatePositionMessage.getElevatorID()).update(updatePositionMessage.getDirection(), updatePositionMessage.getCurrentFloor(), updatePositionMessage.getNumberOfPassengers(), updatePositionMessage.getNextDestination());
                Log.notification("SCHEDULER MID TASK", updatePositionMessage.toString(), new Date(), this.systemName);
                System.out.println("elevator Position update blah blah blah floor: " + this.elevatorStatuses.get(updatePositionMessage.getElevatorID()).getCurrentFloor());
                this.reset();
                return true; 
            }else if (this.receivedMessage instanceof ArrivalMessage) {
                ArrivalMessage arrivalMessage = (ArrivalMessage) this.receivedMessage;
                this.send(arrivalMessage, SimulationConstants.FLOOR_MANAGER_PORT);
                Log.notification("SCHEDULER MID TASK", arrivalMessage.toString(), new Date(), this.systemName);
                this.reset();
                return true; 
            }else if (this.receivedMessage instanceof ElevatorLeavingMessage){
                ElevatorLeavingMessage elevatorMoving = (ElevatorLeavingMessage) this.receivedMessage;
                this.send(elevatorMoving, SimulationConstants.FLOOR_MANAGER_PORT);
                Log.notification("SCHEDULER MID TASK", elevatorMoving.toString(), new Date(), this.systemName);
                this.reset();
                return true; 
            }
            this.reset();
            return false; 
        }
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
                    this.checkState();
                }
                
                if (this.state == SchedulerState.PROCESSING_ELEVATOR){
                    this.processMessage();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
