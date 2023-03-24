package project.elevatorImpl;

import java.util.ArrayList;
import java.util.Collections;
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
import project.messageSystem.messages.UpdatePositionMessage;
import project.statesImpl.State;
import project.statesImpl.elevatorStates.ElevatorCloseDoorState;
import project.statesImpl.elevatorStates.ElevatorDoorFaultState;
import project.statesImpl.elevatorStates.ElevatorIdleState;
import project.statesImpl.elevatorStates.ElevatorMovingState;
import project.statesImpl.elevatorStates.ElevatorOpenDoorState;
import project.statesImpl.elevatorStates.ElevatorRequestProcessState;

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

	// declare the states 
	private State currentState;
	private State requestProcessingState;
	private State elevatorMovingState;
	private State elevatorDoorCloseState; 
	private State elevatorIdleState; 
	private State elevatorDoorOpenState; 
	private State elevatorDoorFaultState; 
	
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
		elevatorStatus = new ElevatorStatus(this.destinations.size(), 0, new Random().nextInt(SimulationConstants.NUM_OF_FLOORS) + 1, MotorDirection.IDLE);
		this.lamps = new boolean[SimulationConstants.NUM_OF_FLOORS];
		this.systemName = systemName; 
		this.id = id; 
		this.floorInputButtons = new HashMap<>();
		this.sendUpdateStatus();
		// initialize the states
		this.requestProcessingState = new ElevatorRequestProcessState(this);
		this.elevatorDoorCloseState = new ElevatorCloseDoorState(this);
		this.elevatorDoorOpenState = new ElevatorOpenDoorState(this);
		this.elevatorIdleState = new ElevatorIdleState(this);
		this.elevatorMovingState = new ElevatorMovingState(this);
		this.elevatorDoorFaultState = new ElevatorDoorFaultState(this);
		this.currentState = this.elevatorIdleState;
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
	 * set the current state of elevator
	 * @param state State, the new current state
	 */
	public void setCurrentState(State state){
		this.currentState = state; 
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
	 * Void method for sorting the directions based on the direction the elevator is headed.
	 */
	public void sortDestinations() {
		if (this.elevatorStatus.getMotorDirection() == MotorDirection.UP) {
			Collections.sort(this.destinations);
		}else if (this.elevatorStatus.getMotorDirection() == MotorDirection.DOWN){
			Collections.reverse(this.destinations);
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
		UpdatePositionMessage updatePositionMessage = new UpdatePositionMessage(new Date(), this.id, this.elevatorStatus.getNumberOfPassengers(), this.elevatorStatus.getNextDestination(), this.elevatorStatus.getCurrentFloor(), this.elevatorStatus.getMotorDirection());
		Log.notification("ELEVATOR", updatePositionMessage.toString(), new Date(), this.systemName);
		this.responses.addFirst(updatePositionMessage);
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
		while (true) {
			try {
				if (this.requests.size() > 0) {
					this.setCurrentState(this.requestProcessingState.handleState());
				}
				this.setCurrentState(this.currentState.handleState());
			}catch (Exception e) {
				// tthrow error
			}
		}
	}

}
