package project.floorImpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import project.constants.FloorButtonState;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.Message;
import project.messageSystem.messages.ArrivalMessage;
import project.simulationParser.Parser;
import project.udp.UDPReceive;

public class FloorManager extends UDPReceive implements Runnable{

    private ConcurrentHashMap<Integer, Floor> floors;
    private ArrayList<String> events; 
    
    /**
     * Constructor for the FloorManager.
     * @param port Integer port number.
     * @param systemName String systemName.
     * @param events ArrayList of events.
     */
    public FloorManager(int port, String systemName, ArrayList<String> events) {
        super(port, systemName);
        this.floors = new ConcurrentHashMap<>();
        this.events = events; 
        System.out.println(this.systemName + " started");
		System.out.println("-------------------------------------------------");
    }

    /**
     * Void method that initializes all floors as per the defined constant.
     */
    private void initializeFloors(){
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
    public void run(){
        // initialize floors
        this.initializeFloors();

        // initialize and start real time floor scheduler 
        FloorScheduler fS = new FloorScheduler("Floor Scheduler", this.events, this.floors);
        Thread fST = new Thread(fS);
        fST.start();

        try{
            while (true){
                Message msg = this.receive(SimulationConstants.BYTE_SIZE);
                
                if (msg instanceof ArrivalMessage){
                    ArrivalMessage arrivalMessage = (ArrivalMessage) msg; 
                    int floorNumber = arrivalMessage.getFloorNumber();
                    MotorDirection direction = arrivalMessage.getDirection();

                    if (direction == MotorDirection.UP){
                        this.floors.get(floorNumber).setUpBtn(FloorButtonState.NOT_ACTIVE);
                    }else if (direction == MotorDirection.DOWN){
                        this.floors.get(floorNumber).setDownBtn(FloorButtonState.NOT_ACTIVE);
                    }else{
                        throw new Error("Pased an unknown direction for the floor events");
                    }

                }
            }
        }catch (Exception e){
            
        }
    }


   /**
    * Main method to run the FloorManager.
    * @param args Not used in this iteration.
    */
    public static void main(String[] args) {
        Parser p = new Parser(); 
        FloorManager manager = new FloorManager(SimulationConstants.FLOOR_MANAGER_PORT, "Floor Manager", p.getEventLines());
        manager.run();
    }



}
