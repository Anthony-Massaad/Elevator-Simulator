package project.floorImpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import project.constants.Addresses;
import project.constants.FloorButtonState;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.messageSystem.messages.ElevatorLeavingMessage;
import project.messageSystem.messages.FloorUpdateMessage;
import project.simulationParser.Parser;
import project.udp.UDPBoth;

/**
 * class that handles the Floors 
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class FloorManager extends UDPBoth implements Runnable{

    private ConcurrentHashMap<Integer, Floor> floors;
    private ArrayList<String> events; 
    
    /**
     * Constructor for the FloorManager.
     * @param port Integer port number.
     * @param systemName String systemName.
     * @param events ArrayList of events.
     */
    public FloorManager(int port, String systemName, ArrayList<String> events) {
        super(port, systemName, Addresses.FLOOR.getAddress());
        this.floors = new ConcurrentHashMap<>();
        this.events = events; 
        System.out.println(this.systemName + " started");
		System.out.println("-------------------------------------------------");
    }

    /**
     * Void method that initializes all floors as per the defined constant.
     */
    public void initializeFloors(){
        // initialize the floors 
        for (int i = 0; i < SimulationConstants.NUM_OF_FLOORS; i++){
            String name = "Floor[" + i + "]";
            if (i == 0){
                // very bottom
                this.floors.put(i, new Floor(i + 1, FloorButtonState.NOT_ACTIVE, FloorButtonState.NON_EXISTENT, name));
            }else if (i == SimulationConstants.NUM_OF_FLOORS - 1){
                // very top 
                this.floors.put(i, new Floor(i + 1, FloorButtonState.NON_EXISTENT, FloorButtonState.NOT_ACTIVE, name));
            }else{
                // inbetween
                this.floors.put(i, new Floor(i + 1, FloorButtonState.NOT_ACTIVE, FloorButtonState.NOT_ACTIVE, name));
            }
            Log.notification("FLOOR MANAGER", "Initialized Floor " + (i + 1), new Date(), this.systemName);
        }
    }

    /**
     * Void method to run the FloorManager's functionality. Initializes all floors and sets buttons as per its states.
     */
    @Override
    public void run(){
        // initialize floors
        this.initializeFloors();

        // initialize and start real time floor scheduler 
        String addressToSend = Addresses.SCHEDULER.getAddress();
        FloorScheduler fS = new FloorScheduler("Floor Scheduler", this.events, this.floors, addressToSend);
        Thread fST = new Thread(fS);
        fST.start();

        try{
            while (true){
                Message msg = this.receive(SimulationConstants.BYTE_SIZE);
                
                if (msg instanceof ArrivalMessage){
                    // elevator arrived
                    ArrivalMessage arrivalMessage = (ArrivalMessage) msg; 
                    int floorNumber = arrivalMessage.getFloorNumber();
                    MotorDirection direction = arrivalMessage.getDirection();
                    if (direction == MotorDirection.UP){
                        this.floors.get(floorNumber-1).setUpBtn(FloorButtonState.NOT_ACTIVE);
                        this.floors.get(floorNumber-1).setUpLamp(true);
                    }else if (direction == MotorDirection.DOWN){
                        this.floors.get(floorNumber-1).setDownBtn(FloorButtonState.NOT_ACTIVE);
                        this.floors.get(floorNumber-1).setDownLamp(true);
                    }else{
                        throw new Error("Pased an unknown direction for the floor events");
                    }
                    this.send(new FloorUpdateMessage(new Date(), direction, floorNumber), SimulationConstants.GUI_PORT);
                }else if (msg instanceof ElevatorLeavingMessage){
                    // elevator is leaving the floor
                    ElevatorLeavingMessage leavingMessage = (ElevatorLeavingMessage) msg; 
                    if (leavingMessage.getDirection() == MotorDirection.UP){
                        this.floors.get(leavingMessage.getFloorNumber() - 1).setUpLamp(false);
                    }else if (leavingMessage.getDirection() == MotorDirection.DOWN){
                        this.floors.get(leavingMessage.getFloorNumber() - 1).setDownLamp(false);
                    }else{
                        this.floors.get(leavingMessage.getFloorNumber() - 1).setDownLamp(false);
                        this.floors.get(leavingMessage.getFloorNumber() - 1).setUpLamp(false);
                    }
                }
            }
        }catch (Exception e){
            Log.error("FLOOR MANAGER", "System broke", new Date(), this.systemName);
        }
    }
	
   /**
    * Getter method to get the floor.
    * @return ConcurrentHashMap<Integer, Floor>
    */
    public ConcurrentHashMap<Integer, Floor> getFloors() {
    	return this.floors;
    }
	
   /**
    * Getter method to get the FloorManager events.
    * @return ArrayList<String>
    */
    public ArrayList<String> getEvents() {
    	return this.events;
    }

   /**
    * Main method to run the FloorManager.
    * @param args Not used in this iteration.
    */
    public static void main(String[] args) {
        Parser p = new Parser(); 
        FloorManager manager = new FloorManager(SimulationConstants.FLOOR_MANAGER_PORT, "Floor Manager", p.getEventLines());
        Thread tF = new Thread(manager);
        tF.start();
    }

}
