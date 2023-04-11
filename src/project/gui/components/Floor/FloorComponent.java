package project.gui.components.Floor;

import javax.swing.*;

import project.constants.MotorDirection;
import project.gui.components.Elevator.ElevatorComponent;

import java.awt.*;

/**
 * Responsible for the floors in the simulation
 * @author Anthony Massaad SYSC3303 Group 2
 */
public class FloorComponent extends JPanel{
    
    private FloorInfo floorInfo; 
    private JPanel elevatorContainer;
    private JPanel[] elevators; 
    /**
     * Constructor for the floor component
     * @param height Integer, the prefered height for the floor
     * @param floorNumber Integer, the floor number
     */
    public FloorComponent(int height, String floorNumber) {
        super(); 
        this.floorInfo = new FloorInfo(floorNumber, this.getHeight(), 64);
        this.elevatorContainer = new  JPanel();
        this.elevators = new JPanel[4];
        this.elevatorContainer.setLayout(new GridLayout(1, 4));
        for (int i = 0; i < 4; i++){
            this.elevators[i] = new JPanel();
            this.elevators[i].setLayout(new BoxLayout(this.elevators[i], BoxLayout.Y_AXIS));
            this.elevators[i].setPreferredSize(new Dimension(64, this.getHeight()));
            this.elevators[i].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); 
            this.elevatorContainer.add(this.elevators[i]);
        }
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLayout(new BorderLayout()); 
        this.add(this.floorInfo, BorderLayout.WEST);
        this.add(this.elevatorContainer, BorderLayout.EAST);
    }

    /**
     * Enabled the  buttons of the floor
     * @param isUp MotordDirection
     */
    public void enableButtons(MotorDirection isUp) {
        this.floorInfo.enableButton(isUp);
    }

    /**
     * Disable the buttons of the floor
     * @param isUp MotordDirection
     */
    public void disableButton(MotorDirection isUp) {
        this.floorInfo.disableButton(isUp);
    }

    /**
     * remove an elevator by elevatorid (for the index)
     * @param index Integer, the elevator id (index)
     */
    public void removeElevator(int index){
        this.elevators[index].removeAll();
        this.elevators[index].repaint();
        this.elevators[index].revalidate();
    }

    /**
     * Add an elevator at the specified index (elevatorID)
     * @param index Integer, the elevator ID
     */
    public void addElevator(int index){
        this.elevators[index].add(new ElevatorComponent("#" + (index + 1)));
        this.elevators[index].repaint();
        this.elevators[index].revalidate();
    }

    /**
     * open the door of the specified elevator by id (index)
     * @param elevatorID Integer, the elevator index
     */
    public void openDoor(int elevatorID){
        ElevatorComponent e = new ElevatorComponent("#" + (elevatorID + 1));
        e.openDoor();
        this.elevators[elevatorID].removeAll();
        this.elevators[elevatorID].add(e);
        this.elevators[elevatorID].repaint();
        this.elevators[elevatorID].revalidate();
    }

    /**
     * close the door of the specifed elevator by id (index)
     * @param elevatorID Integer, the elevator index
     */
    public void closeDoor(int elevatorID){
        ElevatorComponent e = new ElevatorComponent("#" + (elevatorID + 1));
        e.closeDoor();
        this.elevators[elevatorID].removeAll();
        this.elevators[elevatorID].add(e);
        this.elevators[elevatorID].repaint();
        this.elevators[elevatorID].revalidate();
    }

    /**
     * indicate a broken elevator for the simulation by elevator id (index)
     * @param elevatorID Integer, the elevator id
     */
    public void brokenEl(int elevatorID){
        ElevatorComponent e = new ElevatorComponent("#" + (elevatorID + 1));
        e.broken();
        this.elevators[elevatorID].removeAll();
        this.elevators[elevatorID].add(e);
        this.elevators[elevatorID].repaint();
        this.elevators[elevatorID].revalidate();
    }

}
