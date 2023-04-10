package project.elevatorImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ElevatorMoved;
import project.messageSystem.messages.UpdatePositionMessage;
import project.statesImpl.State;
import project.statesImpl.elevatorStates.ElevatorBrokenState;
import project.statesImpl.elevatorStates.ElevatorCloseDoorState;
import project.statesImpl.elevatorStates.ElevatorDoorFaultState;
import project.statesImpl.elevatorStates.ElevatorIdleState;
import project.statesImpl.elevatorStates.ElevatorMovingState;
import project.statesImpl.elevatorStates.ElevatorOpenDoorState;
import project.statesImpl.elevatorStates.ElevatorRequestProcessState;

/**
 * Elevator instance which is a thread 
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class Elevator implements Runnable{
	
	private ConcurrentLinkedDeque<Message> responses; 
	private ConcurrentLinkedDeque<Message> requests;
	// <floor number, buttons to be pressed> 
	private HashMap<Integer, ArrayList<Integer>> floorInputButtons;
	private ArrayList<Integer> destinations;  // elevator buttons, technically passengers so .size is the number of passengers
	private boolean[] lamps; // respective to the destinations value - 1. It is the buttons lamps light
	private String systemName; 
	private ElevatorStatus elevatorStatus; 
	private int id; 
	private boolean directionLampUp;
	private boolean directionLampDown;
	private MotorDirection upcomingDirection; // needed for changing the direction on the initial motordirection of the elvator 
	private int previousFloor; 

	// declare the states 
	private State currentState;
	private State requestProcessingState;
	private State elevatorMovingState;
	private State elevatorDoorCloseState; 
	private State elevatorIdleState; 
	private State elevatorDoorOpenState; 
	private State elevatorDoorFaultState;
	private State elevatorBrokenState; 
	
	/**
	 * Constructor for the Elevator class.
	 * @param id An integer ID.
	 * @param systemName A string systemName.
	 * @param responses A linked deque of responses.
	 */
	public Elevator(int id, String systemName, ConcurrentLinkedDeque<Message> responses) {
		this.responses = responses; 
		this.requests = new ConcurrentLinkedDeque<>();
		// responsible for the basic statuses shared with the scheduler 
		// new Random().nextInt(SimulationConstants.NUM_OF_FLOORS) + 1 = (0-21) + 1 = (1-22)
		this.destinations = new ArrayList<>(); 
		this.elevatorStatus = new ElevatorStatus(this.destinations.size(), 0, new Random().nextInt(SimulationConstants.NUM_OF_FLOORS) + 1, MotorDirection.IDLE);
		this.lamps = new boolean[SimulationConstants.NUM_OF_FLOORS];
		this.systemName = systemName; 
		this.id = id; 
		this.floorInputButtons = new HashMap<>();
		this.upcomingDirection = null; 
		this.previousFloor = this.elevatorStatus.getCurrentFloor();
		// initialize the states
		this.requestProcessingState = new ElevatorRequestProcessState(this);
		this.elevatorDoorCloseState = new ElevatorCloseDoorState(this);
		this.elevatorDoorOpenState = new ElevatorOpenDoorState(this);
		this.elevatorIdleState = new ElevatorIdleState(this);
		this.elevatorMovingState = new ElevatorMovingState(this);
		this.elevatorDoorFaultState = new ElevatorDoorFaultState(this);
		this.elevatorBrokenState = new ElevatorBrokenState(this);
		this.currentState = this.elevatorIdleState;
		this.sendUpdateStatus();
		this.sendUpdateMoveStatus();
	}

	/**
	 * have the thread sleep
	 * @param time Integer, the duration
	 */
	public void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the upcoming direction which the elevator will change to
	 * @return MotorDirection, the direction
	 */
	public MotorDirection getUpcomingDirection(){
		return this.upcomingDirection;
	}

	/**
	 * set the upcoming direction the elevator will change to
	 * @param dir MotorDirection, the direction
	 */
	public void setUpcomingDirection(MotorDirection dir){
		this.upcomingDirection = dir; 
	}

	/**
	 * set the current state of elevator
	 * @param state State, the new current state
	 */
	public void setCurrentState(State state){
		State tpm = this.currentState;
		this.currentState = state; 
		if (state instanceof ElevatorIdleState && !(tpm instanceof ElevatorIdleState)){
			this.sendUpdateStatus();
		}
	}

	/**
	 * get the current State
	 * @return State, the current state
	 */
	public State getCurrentState(){
		return this.currentState; 
	}

	/**
	 * get the elevator moving state
	 * @return State, the moving state
	 */
	public State getElevatorMovingState(){
		return this.elevatorMovingState;
	}

	/**
	 * get the processing state
	 * @return State, the processing state
	 */
	public State getProcessingState(){
		return this.requestProcessingState;
	}

	public void setPreviousFloor(int floor){
		this.previousFloor = floor; 
	}

	public int getPreviousFloor(){
		return this.previousFloor;
	}


	/**
	 * get the door fault state
	 * @return State, door fault state
	 */
	public State getElevatorDoorFaultState(){
		return this.elevatorDoorFaultState;
	}

	/**
	 * get the elevator Idle state
	 * @return State, the idle state
	 */
	public State getElevatorIdleState(){
		return this.elevatorIdleState;
	}

	/**
	 * get the elevator Close door state 
	 * @return State, the close door state
	 */
	public State getElevatorDoorCloseState(){
		return this.elevatorDoorCloseState;
	}

	/**
	 * get the elevator door open state 
	 * @return State, the door open state
	 */
	public State getElevatorDoorOpenState(){
		return this.elevatorDoorOpenState;
	}

	/**
	 * get the elevator Broken State 
	 * @return State, elevator broken state
	 */
	public State getElevatorBrokenState(){
		return this.elevatorBrokenState;
	}

	/**
	 * Getter method for the elevator status.
	 */
	public ElevatorStatus getElevatorStatus(){
		return this.elevatorStatus;
	}

	/**
	 * Void method for adding a request.
	 * @param msg A Message object msg.
	 */
	public void addRequest(Message msg){
		this.requests.add(msg);
	}

	/**
	 * Set the destination
	 * @param dest ArrayList<Integer>, new destinations
	 */
	public void setDestinations(ArrayList<Integer> dest){
		this.destinations = dest; 
	}

	/**
	 * Sorting algorithm for the destinations of the elevator
	 * Faults inputs stays in the index provided from the input file
	 * @param isReverse boolean, True to sort in reverse, otherwise false
	 */
	public void sort(boolean isReverse){
		int pos; 
        for (int i = 0; i < this.destinations.size(); i++) { 
            pos = i; 

			// the value at the index is a fault
			if (this.destinations.get(i) <= 0 || this.destinations.get(i) == SimulationConstants.ELEVATOR_BROKEN_FAULT_INPUT){
				continue;
			}

			for (int j = i + 1; j < this.destinations.size(); j++){
				
				// the value at the index is a fault
				if (this.destinations.get(j) <= 0 || this.destinations.get(j) == SimulationConstants.ELEVATOR_BROKEN_FAULT_INPUT){
					continue;
				}

				if (!isReverse){
					// sort in accending
					if (this.destinations.get(j) < this.destinations.get(pos)){
						pos = j; 
					}
				}else{
					// sort in deccending
					if (this.destinations.get(j) > this.destinations.get(pos)){
						pos = j; 
					}
				}	
			}

			//swap
			int temp = this.destinations.get(i);
			this.destinations.set(i, this.destinations.get(pos));
			this.destinations.set(pos, temp);
        } 
		Log.notification("ELEVATOR", "Sorted Destinations: " + Arrays.toString(this.destinations.toArray()), new Date(), this.systemName);
	}
	
	/**
	 * Void method for sorting the directions based on the direction the elevator is headed.
	 */
	public void sortDestinations() {
		if (this.elevatorStatus.getMotorDirection() == MotorDirection.UP) {
			this.sort(false);
		}else if (this.elevatorStatus.getMotorDirection() == MotorDirection.DOWN){
			this.sort(true);
		}else {
			// error lol 
        	Log.notification("ELEVATOR","Sorting Error", new Date(), this.systemName);
		}
	}

	/**
	 * Void method for updating the Elevator's motor direction.
	 */
	public void updateMotorStatus(){
		if (this.elevatorStatus.getNextDestination() > this.elevatorStatus.getCurrentFloor()){
			this.elevatorStatus.setMotorDirection(MotorDirection.UP);
			this.directionLampUp = true; 
			this.directionLampDown = false; 
		}else{
			this.elevatorStatus.setMotorDirection(MotorDirection.DOWN);
			this.directionLampDown = true;
			this.directionLampUp = false; 
		}
		Log.notification("ELEVATOR", "Motor Direction is " + MotorDirection.toString(this.elevatorStatus.getMotorDirection()), new Date(), this.systemName);
		Log.notification("ELEVATOR", "up direction lamp set to " + this.directionLampUp, new Date(), this.systemName);
		Log.notification("ELEVATOR", "down direction lamp set to " + this.directionLampDown, new Date(), this.systemName);
	}

	/**
	 * Void method for sending the update status.
	 */
	public void sendUpdateStatus(){
		UpdatePositionMessage updatePositionMessage = new UpdatePositionMessage(new Date(), this.id, this.elevatorStatus.getNumberOfPassengers(), this.elevatorStatus.getNextDestination(), this.elevatorStatus.getCurrentFloor(), this.elevatorStatus.getMotorDirection(), this.elevatorStatus.getIsStuck(), this.destinations, this.currentState.toString());
		Log.notification("ELEVATOR", updatePositionMessage.toString(), new Date(), this.systemName);
		this.responses.addLast(updatePositionMessage);
	}

	public void sendUpdateMoveStatus(){
		ElevatorMoved moved = new ElevatorMoved(new Date(), this.previousFloor, this.elevatorStatus.getCurrentFloor(), this.id);
		this.responses.addLast(moved);
	}

	/**
	 * Void method for ading all buttons to the current existing list of elevator buttons.
	 * @param curr The arraylist of current buttons for that elevator.
	 * @param toAdd The arraylist of buttons to add to the list.
	 * @return The combined arraylist.
	 */
	public ArrayList<Integer> appendButtonsToExistingList(ArrayList<Integer> curr, ArrayList<Integer> toAdd){
		curr.addAll(toAdd);
		Set<Integer> set = new LinkedHashSet<>();
		set.addAll(curr);
		return new ArrayList<>(set);
	}

	/**
	 * Void method to add all upcoming buttons to the current list of buttons.
	 * @param floorNumber An integer floor number.
	 * @param buttons An arraylist of current buttons.
	 */
	public void addUpcomingButtons(int floorNumber, ArrayList<Integer> buttons){
		if (buttons.size() == 0){
			return; 
		}
		
		if (this.floorInputButtons.containsKey(floorNumber)){
			ArrayList<Integer> newList = appendButtonsToExistingList(this.floorInputButtons.get(floorNumber), buttons);
			this.floorInputButtons.get(floorNumber).clear();
			this.floorInputButtons.get(floorNumber).addAll(newList);
		}else{
			this.floorInputButtons.put(floorNumber, buttons);
		}
		System.out.println("Size for upcoming buttons for floor " + floorNumber + "= " + this.floorInputButtons.get(floorNumber).size());
	}

	/**
	 * Getter method to get the floor input buttons from the HashMap.
	 * @return HashMap <Integer, ArrayList<Integer>>
	 */
	public HashMap<Integer, ArrayList<Integer>> getFloorInputButtons() {
		return this.floorInputButtons;
	}

	/**
	 * Getter method to get the Elevator requests from the message queue.
	 * @return ConcurrentLinkedDeque<Message>
	 */
	public ConcurrentLinkedDeque<Message> getRequests() {
		return this.requests;
	}

	/**
	 * Getter method to get the Elevator responses from the message queue.
	 * @return ConcurrentLinkedDeque<Message>
	 */
	public ConcurrentLinkedDeque<Message> getResponses() {
		return this.responses;
	}

	/**
	 * Getter method to get the Elevator destination.
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getDestinations() {
		return this.destinations;
	}

	/**
	 * Getter method to get the Elevator lamps.
	 * @return boolean[]
	 */
	public boolean[] getLamps() {
		return this.lamps;
	}

	/**
	 * Getter method to get the Elevator system name.
	 * @return String
	 */
	public String getSystemName() {
		return this.systemName;
	}

	/**
	 * Getter method to get the Elevator Id.
	 * @return Integer
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the direction lamp for up
	 * @param cond boolean, true if on otherwise false
	 */
	public void setDirectionLampUp(boolean cond){
		this.directionLampUp = cond; 
	}

	/**
	 * set the direction lamp for down
	 * @param cond boolean, true if on otherwise false 
	 */
	public void setDirectionLampDown(boolean cond){
		this.directionLampDown = cond;
	}

	/**
	 * get the up direction lamp 
	 * @return boolean, true if on otherwise false 
	 */
	public boolean getDirectionLampUp(){
		return this.directionLampUp;
	}

	/**
	 * get the down direction lamp
	 * @return boolean, true if on otherwise false 
	 */
	public boolean getDirectionLampDown(){
		return this.directionLampDown; 
	}

	/**
     * Overriden method that runs the functionality of the Elevator, all dependent on the current state.
     */
	@Override
	public void run() {
		while (!(this.currentState instanceof ElevatorBrokenState)) {
			try {
				if (this.requests.size() > 0) {
					this.setCurrentState(this.requestProcessingState.handleState());
				}

				this.setCurrentState(this.currentState.handleState());
			}catch (Exception e) {
				// tthrow error
			}
		}
		this.elevatorStatus.setIsStuck(true);
		this.sendUpdateStatus();
		Log.error("ELEVATOR", "Elevator Off", new Date(), this.systemName);
	}

}
