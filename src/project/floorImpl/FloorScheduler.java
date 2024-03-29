package project.floorImpl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import project.clockImpl.Clock;
import project.constants.FloorButtonState;
import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.logger.Log;
import project.messageSystem.messages.FloorInputMessage;
import project.messageSystem.messages.FloorRequestElevator;
import project.udp.UDPSend;

/**
 * Scheduler class that is a thread and handles the sending to the scheduler
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class FloorScheduler extends UDPSend implements Runnable{
    private final int TIME_INDEX = 0;
    private final int FLOOR_INDEX = 1; 
    private final int DIRECTION_INDEX = 2; 
    private final int BUTTON_START_INDEX = 3; 

    private ArrayList<String> events; 
    private Clock clock; 
    private ConcurrentHashMap<Integer, Floor> floors; 
    
    /**
     * Constructor for the FloorScheduler.
     * @param systemName A string systemName.
     * @param events An arrayList of events.
     * @param floors A hashmap of floors.
     */
    public FloorScheduler(String systemName, ArrayList<String> events, ConcurrentHashMap<Integer, Floor> floors, String addr) {
        super(systemName, addr);
        this.events = events;
        this.clock = new Clock();
        this.floors = floors; 
    }

    /**
     * Void method to check and process each event and increment the clock.
     * @throws ParseException
     * @throws IOException
     * @throws InterruptedException
     */
    private void checkEvents() throws ParseException, IOException, InterruptedException{
        for (String event : this.events){
            String[] currEvent = event.split(" "); 
            Date date = this.clock.convertToDate(currEvent[this.TIME_INDEX]);
            if (this.clock.isTime(date.getTime())){
                this.processEvent(event, currEvent, date);
            }
        }
        this.clock.increment();
    }

    /**
     * Void method that processes each event received, and set buttons as necessary per the events received.
     * @param input the input
     * @param event A string array event.
     * @param date A Date object date.
     * @throws ParseException
     * @throws IOException
     * @throws InterruptedException
     */
    private void processEvent(String input, String[] event, Date date) throws ParseException, IOException, InterruptedException{
        Log.notification("FLOOR SCHEDULER", "Proccessing Event", new Date(), this.systemName);
        int floorNumber = Integer.parseInt(event[this.FLOOR_INDEX]);
        MotorDirection direction = MotorDirection.getDirection(event[this.DIRECTION_INDEX]);
        
        ArrayList<Integer> buttonsToBePressed = new ArrayList<>();
        for (int i = this.BUTTON_START_INDEX; i<event.length; i++){
            buttonsToBePressed.add(Integer.parseInt(event[i]));
        }   
        System.out.println("SIZE OF BUTTONS TO BE PRESSED: " + buttonsToBePressed.size());

        if (direction == MotorDirection.UP){
            this.floors.get(floorNumber-1).setUpBtn(FloorButtonState.ACTIVE);
        }else if (direction == MotorDirection.DOWN){
            this.floors.get(floorNumber-1).setDownBtn(FloorButtonState.ACTIVE);
        }else{
            throw new Error("Pased an unknown direction for the floor events");
        }
        
        this.send(new FloorRequestElevator(date, floorNumber, direction, buttonsToBePressed), SimulationConstants.SCHEDULER_PORT);
        this.send(new FloorInputMessage(date, input, direction, floorNumber), SimulationConstants.GUI_PORT);
    }

    /**
     * Void method to run the FloorScheduler thread.
     */
    @Override
    public void run() {
        try{
            while (true){
                this.checkEvents();
                Thread.sleep(1000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
