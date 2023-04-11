package project.gui.components.Elevator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * Responsible for displaying the elevator in the simulation
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorComponent extends JPanel{
    private final String DOOR_CLOSED = "||";
    private final String DOOR_OPENED = "| |";
    
    private JLabel doors; 
    private JLabel title; 
    /**
     * Constructor for the elevator component
     * @param title String, the title of the elevator
     */
    public ElevatorComponent(String title){
        super(); 
        this.setLayout(new GridLayout(1, 2));
        this.title = new JLabel(title);
        this.title.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK)));
        this.title.setHorizontalAlignment(SwingConstants.CENTER);
        this.doors = new JLabel(this.DOOR_CLOSED);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.doors.setHorizontalAlignment(SwingConstants.CENTER);
        this.doors.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(this.title);
        this.add(this.doors);
    }

    /**
     * Simulate the door open of the elevator
     */
    public void openDoor(){
        this.doors.setText(this.DOOR_OPENED);
    }

    /**
     * simulate the door closed of the elevator
     */
    public void closeDoor(){
        this.doors.setText(this.DOOR_CLOSED);
    }

    /**
     * simulate the door broken of the elevator
     */
    public void broken(){
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.title.setBorder((BorderFactory.createMatteBorder(0, 0, 0, 1, Color.RED)));
        this.title.setForeground(Color.RED);
    }
    
}
