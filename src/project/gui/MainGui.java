package project.gui;

import javax.swing.*;

import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.gui.components.Floor.FloorComponent;
import project.gui.components.Text.ElevatorInput.ElevatorInfo;
import project.gui.components.Text.FloorInput.FloorInputText;
import project.messageSystem.Message;
import project.messageSystem.messages.ElevatorMoved;
import project.messageSystem.messages.FloorInputMessage;
import project.messageSystem.messages.FloorUpdateMessage;
import project.messageSystem.messages.UpdatePositionMessage;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Responsible for displaying the main frame
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class MainGui implements Runnable{
    private JPanel mainPanel, simPanel, textPanel, elevatorInfoPanel; 
    private FloorComponent[] floorComponents;
    private ElevatorInfo[] elevatorInfos; 
    private FloorInputText floorInputText; 
    private JFrame mainFrame; 
    private ConcurrentLinkedDeque<Message> updateRequests;

    /**
     * Constructor
     */
    public MainGui(){
        GridBagConstraints constraints = new GridBagConstraints();
        this.mainFrame = new JFrame("Elevator Simulation");
        this.updateRequests = new ConcurrentLinkedDeque<Message>();
        // initialize the panels
        this.mainPanel = new JPanel();
        this.simPanel = new JPanel();
        this.textPanel = new JPanel();
        this.elevatorInfoPanel = new JPanel();
        this.floorComponents = new FloorComponent[SimulationConstants.NUM_OF_FLOORS];
        this.elevatorInfos = new ElevatorInfo[SimulationConstants.NUM_OF_ELEVATORS];

        this.floorInputText = new FloorInputText();

        // setting the layout for the jpanels 
        this.mainPanel.setLayout(new GridBagLayout());
        this.simPanel.setLayout(new BoxLayout(this.simPanel, BoxLayout.Y_AXIS));
        this.textPanel.setLayout(new GridBagLayout());
        this.elevatorInfoPanel.setLayout(new GridLayout(2, 2, 5, 5));

        // add components to sim panel
        for (int i = SimulationConstants.NUM_OF_FLOORS - 1; i >= 0; i--){
            this.floorComponents[i] = new FloorComponent(800/SimulationConstants.NUM_OF_FLOORS, Integer.toString(i + 1));
            this.simPanel.add(this.floorComponents[i]);
        }

        // add elevator info to elevator info panel
        for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
            // should only be 4!
            this.elevatorInfos[i] = new ElevatorInfo(i + 1);
            this.elevatorInfoPanel.add(this.elevatorInfos[i]);
        }

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.8;
        this.textPanel.add(this.elevatorInfoPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.6;
        this.textPanel.add(this.floorInputText, constraints);

        // adding to the main panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1.0;
        this.mainPanel.add(this.simPanel, constraints);
        
        constraints.gridx = 1;
        constraints.weightx = 0.4;
        constraints.insets = new Insets(0, 5, 0, 5);
        this.mainPanel.add(this.textPanel, constraints);

        // finish setup for the frame
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.add(this.mainPanel);

        this.mainFrame.pack();
        this.mainFrame.setResizable(false);
        this.mainFrame.setVisible(true);
    }

    /**
     * Update the elevator information in the gui
     * @param elevatorID Integer, the elevator id
     * @param msg UpdatePositionMessage, the updated statuses
     */
    public void updateElevatorInfo(int elevatorID, UpdatePositionMessage msg){
        this.elevatorInfos[elevatorID].update(msg);

        if (msg.getState().equals("Broken")){
            this.floorComponents[msg.getCurrentFloor()- 1].brokenEl(msg.getElevatorID());
        }else if (msg.getState().equals("Open Door")){
            this.floorComponents[msg.getCurrentFloor()- 1].openDoor(msg.getElevatorID());
        }else if (msg.getState().equals("Close Door")){
            this.floorComponents[msg.getCurrentFloor()- 1].closeDoor(msg.getElevatorID());
        }

    }

    /**
     * Append text to the floor input
     * @param msg String, input
     */
    public void appendFloorInput(String msg){
        this.floorInputText.appendText(msg);
    }

    /**
     * Update the floor components request by enabling the respective buttons
     * @param floorNumber Integer, the floor number
     * @param direction MotorDirection, the direction
     */
    public void updateFloorComponentRequest(int floorNumber, MotorDirection direction){
        this.floorComponents[floorNumber-1].enableButtons(direction);
    }

    /**
     * Update the floor components request by disabling the respective buttons
     * @param floorNumber Integer, the floor number
     * @param direction MotorDirection, the direction
     */
    public void updateFloorComponentArrive(int floorNumber, MotorDirection direction){
        this.floorComponents[floorNumber-1].disableButton(direction);
    }

    /**
     * Move the elevator to its current floor
     * @param floorNumber Integer, the floor number
     * @param elevatorID Integer, the elevator ID
     */
    public void moveElevator(int floorNumber, int elevatorID){
        this.floorComponents[floorNumber-1].addElevator(elevatorID);
    }

    /**
     * Remove the elevator from its previous floor
     * @param floorNumber Integer, the floor number
     * @param elevatorID Integer, the elevator ID
     */
    public void removeElevator(int floorNumber, int elevatorID){
        this.floorComponents[floorNumber-1].removeElevator(elevatorID);
    }

    /**
     * Transfer the elevator from one floor to the next
     * @param msg ElevatorMoved, the positions
     */ 
    public void transferElevator(ElevatorMoved msg){
        this.removeElevator(msg.getPreviousFloor(), msg.getElevatorId());
        this.moveElevator(msg.getCurrentFloor(), msg.getElevatorId());
    }

    /**
     * Update the gui based off its queue of updating requests
     */
    public void checkMessages(){
        Message msg = this.updateRequests.poll();
        if (msg instanceof ElevatorMoved){
            // elevator is moving
            ElevatorMoved moved = (ElevatorMoved) msg; 
            this.removeElevator(moved.getPreviousFloor(), moved.getElevatorId());
            this.moveElevator(moved.getCurrentFloor(), moved.getElevatorId());
        }else if (msg instanceof UpdatePositionMessage){
            // update everything about the elevator
            UpdatePositionMessage updatepositionMessage = (UpdatePositionMessage) msg;
            this.updateElevatorInfo(updatepositionMessage.getElevatorID(), updatepositionMessage);
        }else if (msg instanceof FloorUpdateMessage){
            FloorUpdateMessage updateMessage = (FloorUpdateMessage) msg;
            this.updateFloorComponentArrive(updateMessage.getFloorNumber(), updateMessage.getDirection());
        }else if (msg instanceof FloorInputMessage){
            FloorInputMessage floorInput = (FloorInputMessage) msg;
            this.appendFloorInput(floorInput.getInput());
            this.updateFloorComponentRequest(floorInput.getFloorNumber(), floorInput.getDirection());
        }
    }

    /**
     * overriden run method from runnable
     */
    @Override
    public void run() {
        MessageCollections collect = new MessageCollections(this.updateRequests);
        Thread tC = new Thread(collect);
        tC.start();

        while(true){
            if (this.updateRequests.size() <= 0){
                continue;
            }
            this.checkMessages();
        }
    }

    /**
     * main method
     * @param args String[], arguments
     */
    public static void main(String[] args) {
        MainGui g = new MainGui();
        Thread t = new Thread(g);
        t.start();
    }

}
