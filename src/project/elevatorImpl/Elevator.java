package project.elevatorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.swing.plaf.ColorUIResource;

import project.constants.ElevatorState;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.constants.Time;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.MoveToMessage;
import project.messageSystem.messages.UpdatePositionMessage;

public class Elevator implements Runnable{
	
	private ConcurrentLinkedDeque<Message> responses; 
	private ConcurrentLinkedDeque<Message> requests;
	// <floor number, buttons to be pressed> 
	private HashMap<Integer, ArrayList<Integer>> floorInputButtons;
	private ArrayList<Integer> destinations;  // elevator buttons, technically passengers so .size is the number of passengers
	private boolean[] lamps; // respective to the destinations value - 1. It is the buttons lamps light
	private String systemName; 
	private ElevatorStatus elevatorStatus; 
	private ElevatorState state; 
	private int id; 
	
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
		this.state = ElevatorState.IDLE; 
		this.lamps = new boolean[SimulationConstants.NUM_OF_FLOORS];
		this.systemName = systemName; 
		this.id = id; 
		this.floorInputButtons = new HashMap<>();
		this.sendUpdateStatus();
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
	private void updateMotorStatus(){
		this.elevatorStatus.setMotorDirection(this.elevatorStatus.getNextDestination() > this.elevatorStatus.getCurrentFloor() ? MotorDirection.UP : MotorDirection.DOWN);
	}

	/**
	 * Void method for sending the update status.
	 */
	private void sendUpdateStatus(){
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
	private ArrayList<Integer> appendButtonsToExistingList(ArrayList<Integer> curr, ArrayList<Integer> toAdd){
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
		if (this.floorInputButtons.containsKey(floorNumber)){
			ArrayList<Integer> newList = appendButtonsToExistingList(this.floorInputButtons.get(floorNumber), buttons);
			this.floorInputButtons.get(floorNumber).clear();
			this.floorInputButtons.get(floorNumber).addAll(newList);
		}else{
			this.floorInputButtons.put(floorNumber, buttons);
		}
	}
	
	/**
     * Void method that checks if the requested message was sent from the elevator Subsystem. The Elevator will change state accordingly depending on the message
     */
	public void checkMessage() {
    	Message message = this.requests.poll();
    	
    	if (message instanceof MoveToMessage) {
    		MoveToMessage moveToMessage = (MoveToMessage) message; 
    		int destination = moveToMessage.getDestinationFloor();
        	// Log.notification("ELEVATOR", "Received move to request to floor " + destination, new Date(), this.systemName);
        	// For this iteraion, it's just moving! 
        	Log.notification("ELEVATOR", moveToMessage.toString(), new Date(), this.systemName);
			
        	if (this.elevatorStatus.getCurrentFloor() == destination) {
        		// already at the requested floor
        		this.state = ElevatorState.OPEN_DOOR;
        	}else if (this.state == ElevatorState.MOVING) {
        		// add to queue if button isn't already pressed
				if (this.destinations.contains(destination)){
					return; 
				}
        		this.destinations.add(destination);
        		this.sortDestinations();
				this.addUpcomingButtons(destination, moveToMessage.getButtonsToBePressed());
        		// sort 
        	}else {
        		// start moving the elevator and change motor direction
        		this.elevatorStatus.setNextDestination(destination);
				this.updateMotorStatus();
				this.sendUpdateStatus();
    			this.state = ElevatorState.MOVING;
        	}
    	}
    }
    
    /**
     * Void method that handles the opening of the Elevator door. Prints all related messages and sleeps depending on the Elevator state.
     * @throws InterruptedException
     */
	public void handleOpenDoor() throws InterruptedException {
		Log.notification("ELEVATOR", "Open Door", new Date(), this.systemName);
        Thread.sleep(Time.OPEN_DOOR.getTime());

		// handle unloading and loading passenger
		Log.notification("ELEVATOR", "Unloading Passenger", new Date(), this.systemName);
		Thread.sleep(Time.UNLOAD_PASSENGERS.getTime());

        // Load/unload passengers add to the buttons arraylist then close doors
		if (this.floorInputButtons.containsKey(this.elevatorStatus.getCurrentFloor())){
			Log.notification("ELEVATOR", "Loading Passenger", new Date(), this.systemName);
			Thread.sleep(Time.LOAD_PASSENGERS.getTime());
			this.destinations = this.appendButtonsToExistingList(this.destinations, this.floorInputButtons.get(this.elevatorStatus.getNextDestination()));
			System.out.println("SIZE OF DESTINATION IS " + this.destinations.size()); 
		}
		
        this.state = ElevatorState.CLOSE_DOOR;
    }
    
    /**
     * Void method that handles the closing of the Elevator door. 
     * @throws InterruptedException
     */
	public void handleCloseDoor() throws InterruptedException {
    	Log.notification("ELEVATOR", "Closing Door", new Date(), this.systemName);
        Thread.sleep(Time.CLOSE_DOOR.getTime());
        // if buttons were pressed, then start moving. Otherwise transition to idle
        if (this.destinations.size() > 0) {
			this.elevatorStatus.setNextDestination(this.destinations.get(0));
        	this.destinations.remove(0);
			this.updateMotorStatus();
        	this.state = ElevatorState.MOVING;
        }else {
			this.elevatorStatus.setMotorDirection(MotorDirection.IDLE);
        	this.state = ElevatorState.IDLE;
        }
		this.sendUpdateStatus();
    }
    
    /**
     * Void method that handles the moving of the Elevator. 
     * @throws InterruptedException
     */
	public void handleMoving() throws InterruptedException {
    	Log.notification("ELEVATOR", "Current floor " + this.elevatorStatus.getCurrentFloor(), new Date(), this.systemName);
    	
    	if (this.elevatorStatus.getCurrentFloor() < this.elevatorStatus.getNextDestination()) {
			this.elevatorStatus.setCurrentFloor(this.elevatorStatus.getCurrentFloor() + 1);
    	}else if (this.elevatorStatus.getCurrentFloor() > this.elevatorStatus.getNextDestination()) {
			this.elevatorStatus.setCurrentFloor(this.elevatorStatus.getCurrentFloor() - 1);
    	}
        Thread.sleep(Time.MOVE.getTime());
    	Log.notification("ELEVATOR", "Reached floor " + this.elevatorStatus.getCurrentFloor(), new Date(), this.systemName);
    	// update the scheduler through the ElevatorSubsystem
    	this.sendUpdateStatus();
        if (this.elevatorStatus.getNextDestination() == this.elevatorStatus.getCurrentFloor()) {
        	// when we reach the destination floor 
			this.destinations.remove(0);
			this.lamps[this.elevatorStatus.getNextDestination() - 1] = false; 
        	ArrivalMessage arrivalMessage = new ArrivalMessage(new Date(), this.elevatorStatus.getNextDestination(), this.elevatorStatus.getMotorDirection());
        	Log.notification("ELEVATOR", arrivalMessage.toString(), new Date(), this.systemName);
			Log.notification("ELEVATOR", "Lamp " + this.elevatorStatus.getNextDestination() + " off", new Date(), this.systemName);
        	this.responses.addFirst(arrivalMessage);
        	this.state = ElevatorState.OPEN_DOOR;
        }
    }
	
    /**
     * Overriden method that runs the functionality of the Elevator, all dependent on the current state.
     */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (this.state != ElevatorState.ERROR) {
			try {
				if (this.requests.size() >= 0) {
					this.checkMessage(); 
				}
				if (this.state == ElevatorState.IDLE) {
	                // checks with the scheduler using the message queue system
	                // to see if it can do something.
	                Thread.sleep(500);
	            }
	            if (this.state == ElevatorState.OPEN_DOOR){
	            	this.handleOpenDoor();
	            }
				
				if (this.state == ElevatorState.CLOSE_DOOR){
	            	this.handleCloseDoor();
	            }
	            
				if (this.state == ElevatorState.MOVING){
	            	this.handleMoving();
	            }
			}catch (InterruptedException e) {
				// tthrow error
			}
			
			
		}
	}

	/**
	 * Getter method to get the Elevator state.
	 * @return ElevatorState
	 */
	public ElevatorState getState() {
		return this.state;
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
	public Integer getId() {
		return this.id;
	}

}
