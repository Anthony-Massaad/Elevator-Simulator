package project.gui.components.Elevator;

import javax.swing.*;

public class ElevatorComponent extends JPanel{
    private final String DOOR_CLOSED = "||";
    private final String DOOR_OPENED = "| |";
    
    private JLabel doors; 
    public ElevatorComponent(){
        super(); 
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.doors = new JLabel(this.DOOR_CLOSED);
        this.add(this.doors);
    }

    public void open_doors(){
        this.doors.setText(this.DOOR_OPENED);
    }

    public void close_doors(){
        this.doors.setText(this.DOOR_CLOSED);
    }

}
