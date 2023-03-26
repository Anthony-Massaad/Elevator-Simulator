package project.schedulerImpl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorStatus;
import project.logger.Log;
import project.messageSystem.Message;
import project.statesImpl.State;
import project.statesImpl.SchedulerStates.SchedulerIdleState;
import project.statesImpl.SchedulerStates.SchedulerProcessFloorState;
import project.udp.UDPBoth;

/*
 * Class for communication between the floor to elevator and determining the nearest elevator Given request from floor
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class Scheduler extends UDPBoth{

    private ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses;
    private Message receivedMessage; 
    // declare the states
    private State currentState; 
    private State idleState; 
    private State processFloorState; 

    /**
     * Constructor for the Scheduler class.
     * @param port Integer port number.
     * @param systemName String system name.
     */
    public Scheduler(int port, String systemName) {
        super(port, systemName);
        this.elevatorStatuses = new ConcurrentHashMap<>();
        System.out.println(this.systemName + " started");
        this.receivedMessage = null; 
		System.out.println("-------------------------------------------------");
        // initialize states
        this.idleState = new SchedulerIdleState(this);
        this.processFloorState = new SchedulerProcessFloorState(this);
        this.currentState = this.idleState;
    }

    /**
     * set a message
     * @param msg Message, the message
     */
    public void setReceivedMessage(Message msg){
        this.receivedMessage = msg; 
    }

    /**
     * get the current state
     * @return State, the current State
     */
    public State getCurrentState(){
        return this.currentState;
    }

    /**
     * get the idle state
     * @return State, the idle state
     */
    public State getIdleState(){
        return this.idleState;
    }

    /**
     * get process floor state
     * @return State, the process floor state
     */
    public State getProcessFloorState(){
        return this.processFloorState;
    }

    /**
     * set the current state 
     * @param state State, the new current state
     */
    public void setCurrentState(State state){
        this.currentState = state;
    }

    /**
     * Get the received message
     * @return Message, the received message
     */
    public Message getReceviedMessage(){
        return this.receivedMessage;
    }

    public String getSystemName(){
        return this.systemName;
    }

    /**
     * Method that allows the Scheduler to select an Elevator depending on its distance from the floor.
     * @param floorNumber An integer floor number.
     * @param direction A string direction.
     * @return An elevator number.
     */
    public int selectElevator(int floorNumber, MotorDirection direction){
        int shortestDistance = 9999;
        int elNumber = -1; 

        for (Map.Entry<Integer, ElevatorStatus> entry : this.elevatorStatuses.entrySet()){
            ElevatorStatus status = entry.getValue();  

            if (status.getNumberOfPassengers() == SimulationConstants.MAX_PASSENGERS || status.getIsStuck()){
                continue;
            }

            int distance = 9999;
            int elCurrPosition = status.getCurrentFloor(); 
            System.out.println("Elevator ID: " + entry.getKey() + " Current floor: " + elCurrPosition);
            MotorDirection elDirection = status.getMotorDirection();
            int elDest = status.getNextDestination();

            int diffFloor = elCurrPosition - floorNumber;

            if (elCurrPosition == elDest || elDirection == MotorDirection.IDLE) {
                distance = Math.abs(diffFloor);
            } else if ((diffFloor > 0 && direction == MotorDirection.DOWN && elDirection == MotorDirection.DOWN) || (diffFloor < 0 && direction == MotorDirection.UP && elDirection == MotorDirection.UP)) {
                distance = Math.min(Math.abs(diffFloor), Math.abs(elDest - floorNumber));
            } else { 
                if (elDirection == MotorDirection.DOWN) { 
                    distance = elCurrPosition + floorNumber;
                } else { 
                    distance = (SimulationConstants.NUM_OF_FLOORS - elCurrPosition) + (SimulationConstants.NUM_OF_FLOORS - floorNumber);
                }
            }
            if (distance < shortestDistance){
                shortestDistance = distance;
                elNumber = entry.getKey();
            }

            System.out.println("el number: " + elNumber + " Distance: " + distance);
            
        }

        if (elNumber == -1){
            throw new Error("Elevator was not selected. sill elevator number is still -1");
        }

        Log.notification("SCHEDULER", "Shortest elevator to floor " + floorNumber + " is elevator " + elNumber, new Date(), this.systemName);

        return elNumber; 
    }

    /**
     * reset the states
     */
    public void reset(){
        this.receivedMessage = null; 
    }

    /**
     * call the receive from udp
     */
    public void receive(){
        try {
            this.receivedMessage = this.receive(SimulationConstants.BYTE_SIZE);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * call the send packet from udp
     * @param message Message, msg to send 
     * @param port Integer, port to send to 
     */
    public void sendPacket(Message message, int port){
        try {
            this.send(message, port);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Getter method to get the Elevator statuses.
	 * @return ConcurrentHashMap<Integer, ElevatorStatus>
	 */	
	public ConcurrentHashMap<Integer, ElevatorStatus> getElevatorStatuses() {
		return this.elevatorStatuses;
	}

	/**
	 * Method to add an elevator status.
	 * @param i Integer, the key value
	 * @param elevatorStatus ElevatorStatus, the status of elevator 
	 */
	public void addElevatorStatus(int i, ElevatorStatus elevatorStatus) {
		this.elevatorStatuses.put(i, elevatorStatus);
	}

    /**
     * Run method that activates the Scheduler's functionality.
     */
    public void run(){
        // initialize the hashmap of elevatorStatuses
        for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
            this.elevatorStatuses.put(i, new ElevatorStatus());
            Log.notification("SCHEDULER", "Elevator " + i + " status added", new Date(), this.systemName);
        }

        // start the communication between the scheduler and elevator 
        SchedulerMidTask schedulerMidTask = new SchedulerMidTask(SimulationConstants.SCHEDULER_MID_TASK_PORT, "Schedular Mid Task", this.elevatorStatuses);
        Thread tMidTask = new Thread(schedulerMidTask);
        tMidTask.start();
        Log.notification("SCHEDULER", "Schedular Mid Task Started", new Date(), this.systemName);

        try{
            while (true) {
                this.setCurrentState(this.currentState.handleState());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Main method that allows the Scheduler to run.
     * @param args Typical of main functions. Not used in this iteration.
     */
    public static void main(String[] args) {
        Scheduler s = new Scheduler(SimulationConstants.SCHEDULER_PORT, "Scheduler");
        s.run();
    }

}
