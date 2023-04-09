package project.gui.components.Text.ElevatorInput;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class NextDestField extends JPanel{
    
    private JLabel destination; 
    public NextDestField(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.destination = new JLabel(" ");
        this.destination.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.destination.getPreferredSize().height));
        this.destination.setHorizontalAlignment(SwingConstants.CENTER);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setPreferredSize(new Dimension(10,20));
        this.add(this.destination);
    }

    public void setDestination(String destination) {
        this.destination.setText(destination);
    }

}
