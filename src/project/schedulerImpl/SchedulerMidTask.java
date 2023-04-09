package project.schedulerImpl;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorStatus;
import project.gui.MainGui;
import project.messageSystem.Message;
import project.statesImpl.State;
import project.statesImpl.SchedulerStates.SchedulerMidTaskIdleState;
import project.statesImpl.SchedulerStates.SchedulerProcessElevatorState;
import project.udp.UDPBoth;

/**
 * Class for communication between the elevator and the floor
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class SchedulerMidTask extends UDPBoth implements Runnable{

    private ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses;
    private Message receivedMessage; 
    // declare states
    private State currentState; 
    private State idleState; 
    private State processElevatorState; 
    private MainGui gui;

    public SchedulerMidTask(int port, String systemName, ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses, String addr, MainGui gui) {
        super(port, systemName, addr);
        this.elevatorStatuses = elevatorStatuses;
        this.receivedMessage = null;
        this.idleState = new SchedulerMidTaskIdleState(this);
        this.processElevatorState = new SchedulerProcessElevatorState(this);
        this.currentState = this.idleState;
        this.gui = gui;
    }

    public MainGui getGui(){
        return this.gui;
    }

    /**
     * set a message
     * @param msg Message, the message
     */
    public void setReceivedMessage(Message msg){
        this.receivedMessage = msg; 
    }

    public ConcurrentHashMap<Integer, ElevatorStatus> getElevatorStatus() {
        return this.elevatorStatuses;
    }

    /**
     * get the received message
     * @return Message, received message
     */
    public Message getReceviedMessage(){
        return this.receivedMessage;
    }

    /**
     * get the idle state
     * @return State, state idle
     */
    public State getIdleState(){
        return this.idleState;
    }

    /**
     * get the process elevator state
     * @return State, the process elevator state
     */
    public State getProcessElevatorState(){
        return this.processElevatorState;
    }

    /**
     * get the current state
     * @return State, the current state
     */
    public State getCurrentState(){
        return this.currentState; 
    }

    /**
     * set new current state
     * @param state State, the new current state
     */
    public void setCurrentState(State state){
        this.currentState = state;
    }

    /**
     * get the system name
     * @return String, the system name
     */
    public String getSystemName(){
        return this.systemName;
    }

    /**
     * reset the states
     */
    public void reset(){
        this.receivedMessage = null; 
    }

    /**
     * call the receive method in udp
     */
    public void receive(){
        try {
            this.receivedMessage = this.receive(SimulationConstants.BYTE_SIZE);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * call the send packet in udp 
     * @param msg Message, message to send
     * @param port Integer, port to send to
     */
    public void sendPacket(Message msg, int port){
        try {
            this.send(msg, port);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overriden run method to implement the Scheduler's mid task.
     */
    @Override
    public void run() {
        try{
            while (true){
                this.setCurrentState(this.currentState.handleState());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
