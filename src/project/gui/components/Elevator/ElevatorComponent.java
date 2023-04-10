package project.gui.components.Elevator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

public class ElevatorComponent extends JPanel{
    private final String DOOR_CLOSED = "||";
    private final String DOOR_OPENED = "| |";
    
    private JLabel doors; 
    private JLabel title; 
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

    public void openDoor(){
        this.doors.setText(this.DOOR_OPENED);
    }

    public void closeDoor(){
        this.doors.setText(this.DOOR_CLOSED);
    }

}
