package project.schedulerImpl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import project.constants.MotorDirection;
import project.constants.SchedulerState;
import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorStatus;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.FloorRequestElevator;
import project.messageSystem.messages.RequestElevatorMessage;
import project.udp.UDPBoth;

/*
 * Class for communication between the floor to elevator and determining the nearest elevator 
 * Given request from floor
 */
public class Scheduler extends UDPBoth{

    private ConcurrentHashMap<Integer, ElevatorStatus> elevatorStatuses;
    private SchedulerState state;     
    private Message receivedMessage; 
    /**
     * Constructor for the Scheduler class.
     * @param port Integer port number.
     * @param systemName String system name.
     */
    public Scheduler(int port, String systemName) {
        super(port, systemName);
        this.elevatorStatuses = new ConcurrentHashMap<>();
        this.state = SchedulerState.IDLE; 
        System.out.println(this.systemName + " started");
        this.receivedMessage = null; 
		System.out.println("-------------------------------------------------");
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
            this.state = SchedulerState.PROCESSING_FLOOR;
        }
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

            if (status.getNumberOfPassengers() == SimulationConstants.MAX_PASSENGERS){
                continue;
            }

            int distance = 9999;
            int elCurrPosition = status.getCurrentFloor(); 
            System.out.println("Elevator ID: " + entry.getKey() + " Current floor: " + elCurrPosition);
            MotorDirection elDirection = status.getMotorDirection();
            int elDest = status.getNextDestination();

            int diffFloor = elCurrPosition - floorNumber;

            if (elCurrPosition == elDest) {
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
        this.checkState();
    }

    public boolean processMessage() throws IOException, InterruptedException{
        if (this.receivedMessage == null){
            return false;
        }else{
            if (this.receivedMessage instanceof FloorRequestElevator){
                FloorRequestElevator requestMessage = (FloorRequestElevator) this.receivedMessage;
                int elevatorID = this.selectElevator(requestMessage.getFloorNumber(), requestMessage.getDirection());
                Log.notification("SCHEDULER", requestMessage.toString(), new Date(), this.systemName);
                this.send(new RequestElevatorMessage(requestMessage.getTimeStamp(), requestMessage.getFloorNumber(), elevatorID, requestMessage.getButtonsToBePressed()), SimulationConstants.ELEVATOR_MANAGER_PORT);
                this.reset();
                return true; 
            }
            this.reset();
            return false; 
        }
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
                if (this.state == SchedulerState.IDLE){
                    this.receivedMessage = this.receive(SimulationConstants.BYTE_SIZE);
                    this.checkState();
                }
                
                if (this.state == SchedulerState.PROCESSING_FLOOR){
                    this.processMessage();
                }
            }
        }catch (Exception e){
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
	 * @param i
	 * @param elevatorStatus
	 */
	public void addElevatorStatus(int i, ElevatorStatus elevatorStatus) {
		this.elevatorStatuses.put(i, elevatorStatus);
	}

    /**
     * Main method that allows the Scheduler to run.
     * @param args Typical of main functions. Not used in this iteration.
     */
    public static void main(String[] args) {
        Scheduler s = new Scheduler(SimulationConstants.SCHEDULER_PORT, "Scheduler");
        s.run();
        // Testing Distance IF elevatorStauses was public and he selectElevator was also public
        // Scheduler s = new Scheduler(1000, "Random");
        // s.elevatorStatuses.put(0, new ElevatorStatus(2, 4, 5, MotorDirection.DOWN));
        // s.elevatorStatuses.put(1, new ElevatorStatus(5, 10, 8, MotorDirection.UP));
        // s.elevatorStatuses.put(2, new ElevatorStatus(0, 15, 15, MotorDirection.IDLE));
        // s.elevatorStatuses.put(3, new ElevatorStatus(6, 10, 2, MotorDirection.UP));
        // System.out.println(s.selectElevator(3, MotorDirection.UP));

    }

}
